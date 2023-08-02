package com.example.SpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**")
                .hasRole(ApplicationUserRole.STUDENT.toString())
                .antMatchers(HttpMethod.GET,"/management/api/**")
                .hasAnyRole(ApplicationUserRole.ADMIN.toString(), ApplicationUserRole.ADMINTRAINEE.toString())
                .antMatchers(HttpMethod.DELETE, "/management/api/**")
                .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/**")
                .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**")
                .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("anna")
                .password(this.passwordEncoder.encode("1234"))
//                .roles(ApplicationUserRole.STUDENT.toString()) // ROLE_USER
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build();

        UserDetails user2 = User.builder()
                .username("linda")
                .password(this.passwordEncoder.encode("1234"))
//                .roles(ApplicationUserRole.ADMIN.toString()) // ROLE_ADMIN
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails user3 = User.builder()
                .username("tom")
                .password(this.passwordEncoder.encode("1234"))
//                .roles(ApplicationUserRole.ADMINTRAINEE.toString()) // ROLE_ADMINTRAINEE
                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
}
