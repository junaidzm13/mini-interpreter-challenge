package com.csg.codeit.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import kotlin.random.Random
import kotlin.random.nextInt

@Configuration
@EnableWebSecurity
@Profile("production")
class ProdWebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("notAdmin").password("{noop}${password()}").roles("ADMIN")
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/README.md", "/favicon.ico", "/evaluate", "/example").permitAll()
            .anyRequest().authenticated()
            .and().httpBasic()
    }

    private fun password() = List(20) { Random.nextInt(33..126) }.joinToString("")
}