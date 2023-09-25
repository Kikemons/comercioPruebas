package com.comercio.config;

import com.comercio.config.security.securityUser;
import com.comercio.model.Usuario;
import com.comercio.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class webSecurityConfig {

//    @Autowired
//    public  securityUser securityUser;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return  new securityUser();
//    }

    @Autowired
    private UsuarioServices usuarioServices;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("kike")
                .password("1233")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//       http.csrf().disable()
//               .authorizeRequests()
//               .anyRequest().authenticated()
//               .and()
//               .httpBasic();
//
//       return http.build();
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
