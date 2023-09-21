package com.comercio.Controller;

import com.comercio.model.Producto;
import com.comercio.model.Usuario;
import com.comercio.services.ProductoServices;
import com.comercio.services.UploadFileServices;
import com.comercio.services.UsuarioServices;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private UploadFileServices uploadFileServices;
    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private UsuarioServices usuarioServices;


    private final Logger LOGGER= LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoServices.mostrarProductos());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/guardar")
    public String guardar(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("este es el objeto producto {}",producto);
        Usuario u= usuarioServices.obtenerId(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        LOGGER.info("el usuario es: {}",u.getId());
        producto.setUsuario(u);

        // imagen
        if (producto.getId()==null){//validacion cuando se crea un producto
        String nombreImagen= uploadFileServices.saveImage(file);
        producto.setImagen(nombreImagen);
        }else{

        }

       productoServices.guardar(producto);
        return "redirect:/productos";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model){
        Producto producto= new Producto();
        Optional<Producto> OpProducto=productoServices.obtener(id);
        producto=OpProducto.get();
        LOGGER.info("el producto es: {}",producto);
        model.addAttribute("productoId", producto);
        return "productos/edit";
    }

    @PostMapping ("/actualizar")
    public String actualizar(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {

        if (file.isEmpty()){ //es cuando editamos producto pero no cambiamos la imagen
            Producto p= new Producto();
            p=productoServices.obtener(producto.getId()).get();
            producto.setImagen(p.getImagen());
        }
        else{//cuando se edita tambien la imagen
            String nombreImagen= uploadFileServices.saveImage(file);
            producto.setImagen(nombreImagen);

        }
        productoServices.actualizar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id){
        Producto producto = new Producto();
        producto=productoServices.obtener(id).get();

        //eliminar ccuando no sea la imagen por defecto
        if (!producto.getImagen().equals("defaul.jpg")){
        uploadFileServices.deleteImage(producto.getImagen());
        }
        productoServices.borrar(id);
        return "redirect:/productos";
    }



}

