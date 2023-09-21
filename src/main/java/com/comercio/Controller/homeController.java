package com.comercio.Controller;

import com.comercio.model.DetalleDeOrden;
import com.comercio.model.Orden;
import com.comercio.model.Producto;
import com.comercio.model.Usuario;
import com.comercio.services.DetalleServices;
import com.comercio.services.OrdenServices;
import com.comercio.services.ProductoServices;
import com.comercio.services.UsuarioServices;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class homeController {
    private final Logger log= LoggerFactory.getLogger(homeController.class);

    //almacenar la orden
    List<DetalleDeOrden> detalleOrden= new ArrayList<DetalleDeOrden>();

    //datosd e la orden
    Orden orden= new Orden();

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private ProductoServices productoService;
    @Autowired
    private DetalleServices detalleServices;

    @Autowired
    private OrdenServices ordenServices;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        log.info("la id del ususario es: {}",session.getAttribute("idUsuario"));
        model.addAttribute("productos", productoService.mostrarProductos());

        model.addAttribute("sesion", session.getAttribute("idUsuario"));

        return "Usuario/home";

    }

    @GetMapping("productohome/{id}")
    public  String productoHome(@PathVariable Integer id, Model model){
        log.info("el producto a ver es: {}",id);
        Producto producto= new Producto();
        Optional<Producto> ListPro=productoService.obtener(id);
        producto=ListPro.get();
        model.addAttribute("producto", producto);
        return "Usuario/productohome";
    }


    @PostMapping("/carrito")
    public String carrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleDeOrden detalle= new DetalleDeOrden();
        Producto producto= new Producto();
        double sumaTotal=0;
        Optional<Producto> detalleCar=productoService.obtener(id);
        log.info("el producto es: {}", productoService.obtener(id));
        log.info("cantidad: {}", cantidad);
        producto=detalleCar.get();

        detalle.setCantidad(cantidad);
        detalle.setNombre(producto.getNombre());
        detalle.setPrecio(producto.getPrecio());
        detalle.setTotal(producto.getPrecio()*cantidad);
        detalle.setProducto(producto);

        //validar que el carrito no se agregue mas de una vez
        Integer productoId=producto.getId();
        boolean ingresado=detalleOrden.stream().anyMatch(p ->p.getProducto().getId()==productoId);
        if (!ingresado){
            detalleOrden.add(detalle);
        }


        sumaTotal = detalleOrden.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("carrito", detalleOrden);
        model.addAttribute("orden",orden);
        return "Usuario/carrito";
    }


    // eliminar producto de carrito

    @GetMapping("delete/carrito/{id}")
    public String borrarIdCarrito(@PathVariable Integer id, Model model){
        //lista nueva de producrtos
        List<DetalleDeOrden> nuevaDetalleOrden= new ArrayList<DetalleDeOrden>();
        for (DetalleDeOrden detalleDeOrden: detalleOrden){
            if (detalleDeOrden.getProducto().getId()!=(id)){
                nuevaDetalleOrden.add(detalleDeOrden);
            }
        }
        //poner la lista de orden actualizada
        detalleOrden=nuevaDetalleOrden;

        double sumaTotal=0;
        sumaTotal = detalleOrden.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("carrito", detalleOrden);
        model.addAttribute("orden",orden);

        return "Usuario/carrito";
    }

    @GetMapping("/car")
    public String car(Model model,HttpSession session) {
        model.addAttribute("carrito", detalleOrden);
        model.addAttribute("orden", orden);


        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "Usuario/carrito";
    }

    @GetMapping("/resumenOrden")
    public String resumenOrden( Model model, HttpSession session){
        Usuario usuario= usuarioServices.obtenerId(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();

        model.addAttribute("carrito", detalleOrden);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario", usuario );
        return "Usuario/resumenorden";
    }

    @GetMapping("/saveOrden")
    public String guardarOrden( HttpSession session){
        Date fecha= new Date();
        orden.setFechaCreacion(fecha);
        orden.setNumero(ordenServices.generarNumOrden());

        orden.setUsuario(usuarioServices.obtenerId(Integer.parseInt(session.getAttribute("idUsuario").toString())).get());
        ordenServices.crear(orden);


        //guardar detalles

        for (DetalleDeOrden dt:detalleOrden) {
            dt.setOrden(orden);
            detalleServices.crear(dt);
        }

        //limpiar
        orden= new Orden();
        detalleOrden.clear();

        return  "redirect:/";
    }

    @PostMapping("/buscar")
    public String buscarProduct(@RequestParam String nombre, Model model){
        log.info("el nombre del producto: {} ",nombre);
        List<Producto> producto= productoService.mostrarProductos().stream().filter(fl-> fl.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute( "productos", producto);
        return  "Usuario/home";
    }

}