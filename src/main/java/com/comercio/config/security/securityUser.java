package com.comercio.config.security;


import com.comercio.model.Usuario;
import com.comercio.services.UsuarioServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class securityUser implements UserDetailsService {

    @Autowired
    private UsuarioServices usuarioServices;


    private Logger log= LoggerFactory.getLogger(securityUser.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioServices.findByEmail(username);
        if (optionalUsuario.isPresent()) {
            log.info("el usuario es : ", optionalUsuario.get().getEmail());
            Usuario usuario=optionalUsuario.get();
            return User.withUsername(usuario.getEmail()).password(usuario.getPassword()).roles(usuario.getTipo()).build();
        }
        throw new UsernameNotFoundException("usuario no registrado");
    }
}
