package sk.janobono.common

enum class DefaultAuthority constructor(val authorityName: String) {
    VIEW_USERS("view-users"),
    MANAGE_USERS("manage-users")
}
