package sk.janobono.api.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import sk.janobono.api.service.so.AuthoritySO
import sk.janobono.dal.domain.Authority
import sk.janobono.dal.repository.AuthorityRepository

@Service
class AuthorityApiService {

    private var authorityRepository: AuthorityRepository? = null

    @Autowired
    fun setAuthorityRepository(authorityRepository: AuthorityRepository?) {
        this.authorityRepository = authorityRepository
    }

    fun getAuthorities(pageable: Pageable?): Page<AuthoritySO> {
        LOGGER.debug("getAuthorities({})", pageable)
        return authorityRepository!!.findAll(pageable!!).map { authority: Authority ->
            toAuthoritySO(
                authority
            )
        }
    }

    fun getAuthority(id: Long): AuthoritySO {
        LOGGER.debug("getAuthority({})", id)
        return authorityRepository!!.findById(id)
            .map { authority: Authority ->
                toAuthoritySO(
                    authority
                )
            }
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Authority not found."
                )
            }
    }

    @Transactional
    fun addAuthority(authorityName: String?): AuthoritySO {
        LOGGER.debug("addAuthority({})", authorityName)
        if (authorityRepository!!.existsByName(authorityName!!)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Authority exists.")
        }
        var authority = Authority()
        authority.name = authorityName
        authority = authorityRepository!!.save(authority)
        val result = toAuthoritySO(authority)
        LOGGER.debug("addAuthority({})={}", authorityName, result)
        return result
    }

    @Transactional
    fun setAuthority(id: Long, authorityName: String?): AuthoritySO {
        LOGGER.debug("setAuthority({},{})", id, authorityName)
        var authority = authorityRepository!!.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Authority not found."
                )
            }
        authority.name = authorityName
        authority = authorityRepository!!.save(authority)
        val result = toAuthoritySO(authority)
        LOGGER.debug("setAuthority({})={}", authorityName, result)
        return result
    }

    @Transactional
    fun deleteAuthority(id: Long) {
        LOGGER.debug("deleteAuthority({})", id)
        if (!authorityRepository!!.existsById(id)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Authority not found.")
        }
        authorityRepository!!.deleteById(id)
    }

    private fun toAuthoritySO(authority: Authority): AuthoritySO {
        return AuthoritySO(authority.id!!, authority.name!!)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthorityApiService::class.java)
    }
}
