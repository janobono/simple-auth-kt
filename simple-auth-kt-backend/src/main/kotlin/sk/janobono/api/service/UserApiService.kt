package sk.janobono.api.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.server.ResponseStatusException
import sk.janobono.api.service.so.UserCreateSO
import sk.janobono.api.service.so.UserSO
import sk.janobono.api.service.so.UserUpdateSO
import sk.janobono.component.UserComponent
import sk.janobono.dal.domain.Authority
import sk.janobono.dal.domain.User
import sk.janobono.dal.repository.UserRepository
import sk.janobono.dal.specification.UserSpecification
import java.util.function.Function

@Service
class UserApiService {

    private var passwordEncoder: PasswordEncoder? = null

    private var userComponent: UserComponent? = null

    private var userRepository: UserRepository? = null

    @Autowired
    fun setPasswordEncoder(passwordEncoder: PasswordEncoder?) {
        this.passwordEncoder = passwordEncoder
    }

    @Autowired
    fun setUserComponent(userComponent: UserComponent?) {
        this.userComponent = userComponent
    }

    @Autowired
    fun setUserRepository(userRepository: UserRepository?) {
        this.userRepository = userRepository
    }

    fun getUsers(pageable: Pageable): Page<UserSO> {
        LOGGER.debug("getUsers({})", pageable)
        val result = userRepository!!.findAll(pageable).map<UserSO>(Function { user: User? ->
            userComponent!!.toUserSO(
                user!!
            )
        })
        LOGGER.debug("getUsers({})={}", pageable, result)
        return result
    }

    fun getUsers(searchField: String, pageable: Pageable): Page<UserSO> {
        LOGGER.debug("getUsers({},{})", searchField, pageable)
        val result = userRepository!!.findAll(UserSpecification(searchField), pageable)
            .map<UserSO> { userComponent!!.toUserSO(it) }
        LOGGER.debug("getUsers({},{})={}", searchField, pageable, result)
        return result
    }

    fun getUser(id: Long): UserSO {
        LOGGER.debug("getUser({})", id)
        val user = userRepository!!.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "User not found."
            )
        }!!
        val result = userComponent!!.toUserSO(user)
        LOGGER.debug("getUser({})={}", id, result)
        return result
    }

    @Transactional
    fun addUser(userCreateSO: UserCreateSO): UserSO {
        LOGGER.debug("addUser({})", userCreateSO)
        if (userRepository!!.existsByUsername(userCreateSO.username.lowercase())) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken.")
        }
        var user = User()
        user.username = userCreateSO.username
        user.password = passwordEncoder!!.encode(userCreateSO.password)
        user.enabled = userCreateSO.enabled
        user.authorities = userCreateSO.authorities.map {
            Authority(it.id, it.name)
        }
        user.attributes = userCreateSO.attributes
        user = userRepository!!.save(user)
        val result = userComponent!!.toUserSO(user)
        LOGGER.debug("addUser({})={}", userCreateSO, result)
        return result
    }

    @Transactional
    fun setUser(id: Long, userUpdateSO: UserUpdateSO): UserSO {
        LOGGER.debug("setUser({},{})", id, userUpdateSO)
        if (userRepository!!.existsByUsernameAndIdNot(userUpdateSO.username.lowercase(), id)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken.")
        }
        var user = userRepository!!.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User not found."
                )
            }!!
        user.username = userUpdateSO.username
        if (StringUtils.hasLength(userUpdateSO.password)) {
            user.password = passwordEncoder!!.encode(userUpdateSO.password)
        }
        user.enabled = userUpdateSO.enabled
        user.authorities = userUpdateSO.authorities.map {
            Authority(it.id, it.name)
        }
        user.attributes = userUpdateSO.attributes
        user = userRepository!!.save(user)
        val result = userComponent!!.toUserSO(user)
        LOGGER.debug("setUser({},{})={}", id, userUpdateSO, result)
        return result
    }

    @Transactional
    fun deleteUser(id: Long) {
        LOGGER.debug("deleteUser({})", id)
        if (!userRepository!!.existsById(id)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.")
        }
        userRepository!!.deleteById(id)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserApiService::class.java)
    }
}
