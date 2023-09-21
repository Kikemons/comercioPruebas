package com.comercio.Controller;

import com.comercio.model.Producto;
import com.comercio.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoServices productoServices;

    @GetMapping("")
    public String saludar(Model model){
        List<Producto> productos=productoServices.mostrarProductos();
         model.addAttribute("productos", productos);
        return "administrador/inicio";
    }



}
