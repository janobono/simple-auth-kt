package sk.janobono.dal.specification

import org.slf4j.LoggerFactory
import org.springframework.data.jpa.domain.Specification
import sk.janobono.dal.domain.User
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class UserSpecification(val searchField: String? = null) : Specification<User> {

    override fun toPredicate(root: Root<User>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        query.distinct(true)
        if (searchField == null || searchField.isBlank()) {
            LOGGER.debug("Empty criteria.")
            return query.restriction
        }
        val predicates: MutableList<Predicate> = ArrayList()
        predicates.add(searchFieldToPredicate(searchField, root, criteriaBuilder))
        return query.where(criteriaBuilder.and(*predicates.toTypedArray())).restriction
    }

    private fun searchFieldToPredicate(
        searchField: String,
        root: Root<User>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val predicates: Array<Predicate> = searchField.split(" ".toRegex())
            .filter(String::isNotBlank).map {
                criteriaBuilder.like(
                    root.get("username"),
                    "%${it.lowercase(Locale.getDefault())}%"
                )
            }.toTypedArray()
        return criteriaBuilder.and(*predicates)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserSpecification::class.java)
    }
}
