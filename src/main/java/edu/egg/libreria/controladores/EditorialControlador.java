
package edu.egg.libreria.controladores;

import edu.egg.libreria.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    EditorialServicio editorialServicio;
    
      @GetMapping("/registro")
    public String formulario() {
        return "editorial_registro";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {

        try {
            editorialServicio.registrar(nombre);
            modelo.put("exito", "Editorial registrada con exito!");
        } catch (Exception e) {
            modelo.put("error", "Error al registrar la editorial");
        }

        return "editorial_registro";
    }

    @GetMapping("/mostrarEditoriales")
    public String mostrarEditoriales(ModelMap modelo) {
        
        modelo.put("mensajeid", "ID");
        modelo.put("mensajenombre", "Nombre");
        modelo.put("mensajeeditar", "Editar");
        modelo.put("mensajeeliminar", "Eliminar");
        modelo.addAttribute("editoriales", editorialServicio.consultarTodas());

        return "editorial_registro";
//        List<Autor> listaAutores = autorServicio.consultarTodos();
//        modelo.addAttribute("autores", listaAutores);
//        
//        return "autor_registro";
    }

}
    

