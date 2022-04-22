package sk.janobono.dal.repository

import org.springframework.data.jpa.repository.JpaRepository
import sk.janobono.dal.domain.Authority
import java.util.*

interface AuthorityRepository : JpaRepository<Authority, Long?> {

    fun findByName(name: String): Optional<Authority>

    fun existsByName(name: String): Boolean
}
