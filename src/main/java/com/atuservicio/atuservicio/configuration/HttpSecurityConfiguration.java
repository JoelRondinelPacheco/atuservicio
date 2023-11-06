package com.atuservicio.atuservicio.configuration;

import com.atuservicio.atuservicio.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers("/css/*", "/js/*", "/img/*").permitAll()
                        .mvcMatchers("/", "/search", "/contact", "/login", "/error", "/perform_login" ,
                                    "/client/register", "/supplier/register", "/supplier/services", "/image/**").permitAll()
                        .mvcMatchers("/profile", "/editUser", "/client/edit/**").authenticated()
                        .mvcMatchers("/supplier/modify/**").hasRole(Role.SUPPLIER.name())
                        .mvcMatchers("/admin/dashboard", "/admin/clients/**",
                                    "/admin/suppliers/**", "/admin/categories", "admin/category/**").hasRole(Role.ADMIN.name())
                        .anyRequest().denyAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error"))
                        // TODO agregar mensaje de error en la pagina
                .logout(logout -> logout
                                .logoutUrl("/perform_logout")
                                .logoutSuccessUrl("/")
                                .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    /*
    @Bean
AccessDecisionVoter hierarchyVoter() {
    RoleHierarchy hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF\n" +
            "ROLE_STAFF > ROLE_USER\n" +
            "ROLE_USER > ROLE_GUEST");
    return new RoleHierarchyVoter(hierarchy);
}
     */
}
