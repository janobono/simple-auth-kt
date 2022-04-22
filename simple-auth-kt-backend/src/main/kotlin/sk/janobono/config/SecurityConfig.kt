package sk.janobono.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import sk.janobono.component.JwtAuthenticationEntryPoint
import sk.janobono.component.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    private var jwtAuthenticationFilter: JwtAuthenticationFilter? = null

    private var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint? = null

    @Autowired
    fun setJwtAuthenticationFilter(jwtAuthenticationFilter: JwtAuthenticationFilter?) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter
    }

    @Autowired
    fun setJwtAuthenticationEntryPoint(jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint?) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/authenticate").permitAll()
            .antMatchers("/health").permitAll()
            .anyRequest().authenticated().and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
