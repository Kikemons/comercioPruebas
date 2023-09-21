package com.comercio.Controller;

import com.comercio.model.Orden;
import com.comercio.model.Usuario;
import com.comercio.services.OrdenServices;
import com.comercio.services.UsuarioServices;

import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private OrdenServices ordenServices;

    //se crea el mapping del aparatdo registro
    @GetMapping("/registro")
    public String crear(){
    return "Usuario/registro";
    }

    @PostMapping("/crear")
    public String subirUsusario(Usuario usuario, Model model){
        logger.info("usuario registro es: {}",usuario);
        usuario.setTipo("USER");
        usuarioServices.crear(usuario);
        return"redirect:/";
    }

    @GetMapping ("/login")
    public String login(){
        return "usuario/login";
    }

    @PostMapping("/Acceder")
    public String acceder(Usuario usuario, HttpSession session){
        Optional<Usuario> user=usuarioServices.findByEmail(usuario.getEmail());
        logger.info("usuario de bd:: {}", user.get());

        if(user.isPresent()){
          session.setAttribute("idUsuario", user.get().getId());
           if(user.get().getTipo().equals("ADMIN")){
            return "redirect:/administrador";
           }else {
               return"redirect:/";
           }
        }else{
            logger.info("usuario no registrado");
        }
        return"redirect:/";
    }

    @GetMapping("/compras")
    public String compras(HttpSession session, Model model) {
    model.addAttribute("sesion", session.getAttribute("idUsuario"));
    Usuario usuario= usuarioServices.obtenerId(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
    List<Orden> orden=ordenServices.findByUsuario(usuario);
    model.addAttribute("ordenes", orden);
        return "Usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model){
        logger.info("el id de la orden es {} ",id);
        Optional<Orden> orden= ordenServices.ordenId(id);
        model.addAttribute("detalles", orden.get().getDetalle());
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "Usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrar(HttpSession session){
        session.removeAttribute("idUsuario");
        return "redirect:/";
    }

    @GetMapping ("/user")
        public String usuarios(Usuario usuario,Model model){
        model.addAttribute("Usuarios", usuarioServices.mostrarUsuarios());
        return "administrador/usuarios";
        }

        @GetMapping("/ordenes")
        public String ordenes(Orden orden, Model model){
        model.addAttribute("ordenes", ordenServices.mostrar());
        return "administrador/compras";
        }

        @GetMapping("/detalleOrden/{id}")
        public String dettaleorden (@PathVariable Integer id, Model model){
        Optional<Orden> orden=ordenServices.ordenId(id);
        model.addAttribute("detalleorden", orden.get().getDetalle());
        return "administrador/detallecompra";
        }
}
