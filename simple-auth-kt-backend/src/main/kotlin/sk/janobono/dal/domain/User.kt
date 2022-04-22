package sk.janobono.dal.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "simple_auth_user")
@SequenceGenerator(name = "user_generator", allocationSize = 1, sequenceName = "sq_simple_auth_user")
class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_generator")
    var id: Long? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "enabled")
    var enabled: Boolean? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "simple_auth_user_authority",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    var authorities: List<Authority>? = null
        get() {
            if (field == null) {
                field = ArrayList()
            }
            return field
        }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "simple_auth_user_attribute", joinColumns = [JoinColumn(name = "user_id")])
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    var attributes: Map<String, String>? = null
        get() {
            if (field == null) {
                field = HashMap()
            }
            return field
        }

    @PrePersist
    @PreUpdate
    fun updateUsername() {
        username = username!!.lowercase(Locale.getDefault())
    }

    override fun equals(other: Any?): Boolean {
        return (other is User) && (other.id == id)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "User(id=$id, username=$username, enabled=$enabled)"
    }
}
