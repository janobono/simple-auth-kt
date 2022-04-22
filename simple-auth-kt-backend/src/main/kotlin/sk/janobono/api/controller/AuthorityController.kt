package sk.janobono.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import sk.janobono.api.service.AuthorityApiService
import sk.janobono.api.service.so.AuthoritySO

@Tag(name = "authorities", description = "authorities management endpoint")
@RestController
@RequestMapping(path = ["/authorities"])
class AuthorityController(private val authorityApiService: AuthorityApiService) {

    @Operation(
        parameters = [Parameter(
            `in` = ParameterIn.QUERY,
            name = "page",
            content = [Content(schema = Schema(type = "integer"))]
        ), Parameter(
            `in` = ParameterIn.QUERY,
            name = "size",
            content = [Content(schema = Schema(type = "integer"))]
        ), Parameter(
            `in` = ParameterIn.QUERY,
            name = "sort",
            content = [Content(array = ArraySchema(schema = Schema(type = "string")))]
        )]
    )
    @GetMapping
    @PreAuthorize("hasAnyAuthority('view-users', 'manage-users')")
    fun getAuthorities(
        @Parameter(hidden = true) pageable: Pageable?
    ): ResponseEntity<Page<AuthoritySO>> {
        LOGGER.debug("getAuthorities({})", pageable)
        return ResponseEntity(authorityApiService.getAuthorities(pageable), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('view-users', 'manage-users')")
    fun getAuthority(@PathVariable("id") id: Long?): ResponseEntity<AuthoritySO> {
        LOGGER.debug("getAuthority({})", id)
        return ResponseEntity(authorityApiService.getAuthority(id!!), HttpStatus.OK)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('manage-users')")
    fun addAuthority(@RequestBody authority: String?): ResponseEntity<AuthoritySO> {
        LOGGER.debug("addAuthority({})", authority)
        return ResponseEntity(authorityApiService.addAuthority(authority), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('manage-users')")
    fun setAuthority(@PathVariable("id") id: Long?, @RequestBody authority: String?): ResponseEntity<AuthoritySO> {
        LOGGER.debug("addAuthority({})", authority)
        return ResponseEntity(authorityApiService.setAuthority(id!!, authority), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('manage-users')")
    fun deleteAuthority(@PathVariable("id") id: Long?) {
        LOGGER.debug("deleteAuthority({})", id)
        authorityApiService.deleteAuthority(id!!)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthorityController::class.java)
    }
}
