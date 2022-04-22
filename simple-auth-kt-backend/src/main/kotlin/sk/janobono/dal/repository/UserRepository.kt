package sk.janobono.dal.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import sk.janobono.dal.domain.User
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findAll(specification: Specification<User>, pageable: Pageable): Page<User>

    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean

    fun existsByUsernameAndIdNot(username: String, id: Long): Boolean
}
