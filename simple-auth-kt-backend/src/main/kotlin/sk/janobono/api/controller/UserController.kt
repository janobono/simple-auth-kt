package sk.janobono.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import sk.janobono.api.service.UserApiService
import sk.janobono.api.service.so.UserCreateSO
import sk.janobono.api.service.so.UserSO
import sk.janobono.api.service.so.UserUpdateSO
import javax.validation.Valid

@Tag(name = "users", description = "users management endpoint")
@RestController
@RequestMapping(path = ["/users"])
class UserController(private val userApiService: UserApiService) {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('view-users', 'manage-users')")
    fun getUsers(pageable: Pageable?): ResponseEntity<Page<UserSO>> {
        LOGGER.debug("getUsers({})", pageable)
        return ResponseEntity(userApiService.getUsers(pageable!!), HttpStatus.OK)
    }

    @GetMapping("/by-search-criteria")
    @PreAuthorize("hasAnyAuthority('view-users', 'manage-users')")
    fun getUsersBySearchCriteria(
        @RequestParam(value = "search-field", required = false) searchField: String?, pageable: Pageable?
    ): ResponseEntity<Page<UserSO>> {
        LOGGER.debug("getUsersBySearchCriteria({},{})", searchField, pageable)
        return ResponseEntity(
            userApiService.getUsers(
                searchField!!,
                pageable!!
            ), HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('view-users', 'manage-users')")
    fun getUser(@PathVariable("id") id: Long?): ResponseEntity<UserSO> {
        LOGGER.debug("getUser({})", id)
        return ResponseEntity(userApiService.getUser(id!!), HttpStatus.OK)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('manage-users')")
    fun addUser(@RequestBody userCreateSO: @Valid UserCreateSO?): ResponseEntity<UserSO> {
        LOGGER.debug("addUser({})", userCreateSO)
        return ResponseEntity(userApiService.addUser(userCreateSO!!), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('manage-users')")
    fun setUser(
        @PathVariable("id") id: Long?,
        @RequestBody userUpdateSO: @Valid UserUpdateSO?
    ): ResponseEntity<UserSO> {
        LOGGER.debug("setUser({})", userUpdateSO)
        return ResponseEntity(userApiService.setUser(id!!, userUpdateSO!!), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('manage-users')")
    fun deleteUser(@PathVariable("id") id: Long?) {
        LOGGER.debug("deleteUser({})", id)
        userApiService.deleteUser(id!!)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserController::class.java)
    }
}
