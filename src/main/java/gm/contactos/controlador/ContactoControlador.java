package gm.contactos.controlador;

import gm.contactos.modelo.Contacto;
import gm.contactos.servicio.ContactoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(ContactoControlador.class);

    @Autowired
    ContactoServicio contactoServicio;

    @GetMapping("/")
    public String iniciar(ModelMap modelo){
        List<Contacto> contactos = contactoServicio.listarContactos();
        contactos.forEach((contacto) -> logger.info(contacto.toString()));
        modelo.put("contactos", contactos);
        return "index"; //index.html
    }

    @GetMapping("/agregar")
    public String mostrarAgregar(){
        return "agregar";//agregar.html
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("contactoForma") Contacto contacto){
        logger.info("Contacto a agregar: " + contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";// redirigimos al controlador el path "/"
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable(value = "id") int idContacto,
                                ModelMap modelo){
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        logger.info("Contacto a editar (mostrar): " + contacto);
        modelo.put("contacto", contacto);
        return "editar"; //editar.html
    }

}
