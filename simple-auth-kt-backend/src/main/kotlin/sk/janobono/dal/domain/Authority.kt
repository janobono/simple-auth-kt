package sk.janobono.dal.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "simple_auth_authority")
@SequenceGenerator(name = "authority_generator", allocationSize = 1, sequenceName = "sq_simple_auth_authority")
class Authority {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "authority_generator")
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    constructor() {}

    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }

    @PrePersist
    @PreUpdate
    fun updateName() {
        name = name!!.lowercase(Locale.getDefault())
    }

    override fun equals(other: Any?): Boolean {
        return (other is Authority) && (other.id == id)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Authority(id=$id, name=$name)"
    }
}
