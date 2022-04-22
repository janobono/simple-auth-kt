package sk.janobono.component

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import sk.janobono.api.service.so.AuthoritySO
import sk.janobono.api.service.so.UserSO
import sk.janobono.dal.domain.Authority
import sk.janobono.dal.domain.User

@Component
class UserComponent {

    fun toUserSO(user: User): UserSO {
        LOGGER.debug("toUserSO({})", user)
        val result = UserSO(
            user.id,
            user.username!!,
            user.enabled!!,
            user.authorities!!
                .sortedBy(Authority::id)
                .map {
                    AuthoritySO(it.id!!, it.name!!)
                }.toList(),
            attributes = user.attributes!!
        )
        LOGGER.debug("toUserSO({})={}", user, result)
        return result
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserComponent::class.java)
    }
}
