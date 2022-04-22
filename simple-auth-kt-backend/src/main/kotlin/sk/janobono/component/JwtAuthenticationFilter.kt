package sk.janobono.component

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    private var jwtToken: JwtToken? = null

    @Autowired
    fun setJwtToken(jwtToken: JwtToken?) {
        this.jwtToken = jwtToken
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        if (StringUtils.hasLength(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substringAfter("Bearer ")
            val user = jwtToken!!.parseToken(token)
            LOGGER.debug("user: {}", user)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                user,
                null,
                user.authorities!!.map {
                    SimpleGrantedAuthority(
                        it.name
                    )
                }
            )
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }
}
