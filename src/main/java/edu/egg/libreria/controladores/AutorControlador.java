package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.servicios.AutorServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    AutorServicio autorServicio;

    @GetMapping("/registro")
    public String formulario() {
        return "autor_registro";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {

        try {
            autorServicio.registrar(nombre);
            modelo.put("exito", "Autor registrado con exito!");
        } catch (Exception e) {
            modelo.put("error", "Error al registrar al autor");
        }

        return "autor_registro";
    }

    @GetMapping("/mostrarAutores")
    public String mostrarAutores(ModelMap modelo) {
        
        modelo.put("mensajeid", "ID");
        modelo.put("mensajenombre", "Nombre");
        modelo.put("mensajeeditar", "Editar");
        modelo.put("mensajeeliminar", "Eliminar");
        modelo.addAttribute("autores", autorServicio.consultarTodos());

        return "autor_registro";
//        List<Autor> listaAutores = autorServicio.consultarTodos();
//        modelo.addAttribute("autores", listaAutores);
//        
//        return "autor_registro";
    }

}
