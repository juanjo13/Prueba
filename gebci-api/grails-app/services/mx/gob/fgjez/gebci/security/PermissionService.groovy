package mx.gob.fgjez.gebci.security

import grails.gorm.transactions.Transactional
import mx.gob.fgjez.gebci.abac.Permiso

@Transactional
class PermissionService {

    def springSecurityService

    boolean canRead(long key) {
        def read = find(key) != null
        log.debug("can read @${key} ?> ${read}")
        return read
    }

    boolean canWrite(long key) {
        def p = find(key)
        def write = p != null && (p.access in ['write', 'delegate', 'owner'])
        log.debug("can write @${key} ?> ${write}")
        return write
    }

    boolean isOwner(long key) {
        def p = find(key)
        def owner = p != null && p.access == 'owner'
        log.debug("is owner @${key} ?> ${owner}")
        return owner
    }

    void setOwner(long key, String user) {
        log.debug("setting owner ${key}:${user}")
        def ps = Permiso.findAll { authKey == key && (username == user || access == 'owner') }
        ps*.delete(flush:true)
        create(key, user, 'owner')
    }

    void addWriter(long key, String user) {
        log.debug("adding writer ${key}:${user}")
        def p = find(key, user)
        if (p == null) {
            create(key, user, 'write')
        } else if (p.access != 'owner') {
            p.access = 'write'
        }
    }

    void addReader(long key, String user) {
        log.debug("adding reader ${key}:${user}")
        def p = find(key, user)
        if (p == null)
            create(key, user, 'read')
    }

    private Permiso find(long key) {
        find(key, currentUser())
    }

    private String currentUser() {
        springSecurityService.principal?.username
    }

    private static Permiso find(long key, String user) {
        Permiso.find { authKey == key && username == user }
    }

    private Permiso create(long key, String user, String access) {
        new Permiso(authKey: key, username: user, access: access).save()
    }

    def createPermissions(carpeta, permisos) {
        def username = springSecurityService.principal.username
        def isMP = springSecurityService.principal.authorities.find { it.role == 'ROLE_MINISTERIO_PUBLICO' } != null
        def isSec = springSecurityService.principal.authorities.find { it.role == 'ROLE_SECRETARIO' } != null

        if (!isMP && !isSec) {
            throw new SecurityException()
        }

        permisos = permisos ?: []
        def self = permisos.find { it.username == username }
        def owner = permisos.find { it.access == 'owner' }

        log.debug("user: ${username}; self: ${self}; owner:${owner}")

        if (!owner && !isMP) { // no se especifica owner y el usuario no es MP
            throw new IllegalArgumentException('no se ha definido dueño para la carpeta')
        }

        if (owner && isMP && owner != self) { // usuario MP no puede crear carpetas para otro MP
            throw new SecurityException('no esta permitido a MP asigna carpeta a otro MP')
        }

        if (owner) { // verifica que el owner sea MP
            def u = User.find { username == owner.username }
            def r = Role.find { authority == 'ROLE_MINISTERIO_PUBLICO' }
            def ownerIsMP = u && UserRole.exists(u.id, r.id)
            if(!ownerIsMP) {
                throw new SecurityException('solo MP puede ser dueño de carpeta')
            }
        }

        def delegates = permisos.findAll { it.access == 'delegate' }
        if (delegates && !isMP) { //solo el MP puede asignar delegados
            throw new SecurityException('solo MP puede definir delegados de carpeta')
        }

        if (delegates) {
            def r = Role.find { authority == 'ROLE_SECRETARIO' }
            delegates.each { d ->
                def u = User.find { username == d.username }
                if (!UserRole.exists(u.id, r.id)) {
                    throw new SecurityException('solo secretarios pueden ser delegados de carpeta')
                }
            }
        }

        permisos << new Permiso(
                carpeta: carpeta,
                authKey: carpeta.authKey,
                username: username,
                access: isMP ? 'owner' : 'delegate')

        log.debug("created perms: ${permisos}")
        permisos
    }

    def updatePermissions(oldPerms, newPerms) {
        log.debug("oldPerms:${oldPerms}")
        log.debug("newPerms:${newPerms}")

        def username = springSecurityService.principal.username
        def authorities = springSecurityService.principal.authorities
        def isMP = authorities.find { it.role == 'ROLE_MINISTERIO_PUBLICO' } != null
        def isSec = authorities.find { it.role == 'ROLE_SECRETARIO' } != null
        def isTitular = authorities.find { it.role == 'ROLE_TITULAR' } != null

        if (!isMP && !isSec && isTitular) {
            throw new SecurityException()
        }

        def oldSelf = oldPerms.find { it.username == username }
        def oldOwner = oldPerms.find { it.access == 'owner' }

        def newSelf = newPerms.find { it.username == username }
        def newOwner = newPerms.find { it.access == 'owner' }

        log.debug("user: ${username}; self: ${oldSelf}|${newSelf}; owner: ${oldOwner}|${newOwner}")

        if (newOwner && oldOwner != newOwner && !isTitular) {
            throw new SecurityException('solo el titular puede cambiar una carpeta de dueño')
        }

        if (newSelf && oldSelf != newSelf) {
            throw new SecurityException('el usuario no puede cambiar sus propios permisos')
        }

        def newPermsByUser = newPerms.collectEntries { [(it.username) : it] }
        newPermsByUser << [(oldOwner.username) : oldOwner]
        newPermsByUser << [(oldSelf.username) : oldSelf]

        oldPerms.removeAll { p -> !newPermsByUser.containsKey(p.username) }
        oldPerms.each { p ->
            p.access = newPermsByUser.get(p.username).access
        }
        def oldUsers = oldPerms.collect { p -> p.username }
        def newP = newPermsByUser.findAll { !oldUsers.contains(it.key) }*.value
        oldPerms.addAll(newP)
        oldPerms
    }

}
