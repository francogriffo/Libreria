package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public void registrar(String nombre) throws ErrorServicio {

        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre) throws ErrorServicio {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);

        } else {
            throw new ErrorServicio("No se encontro la editorial solicitada.");
        }

    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorialRepositorio.delete(editorial);
        } else {
            throw new ErrorServicio("No se encontro la Editorial solicitada.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public List<Editorial> consultarPorNombre(String nombre) throws ErrorServicio {

        validar(nombre);

        try {
            return editorialRepositorio.buscarPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Editorial> consultarTodas() {

        try {
            return editorialRepositorio.buscarTodas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
  

    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);

        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }

    }

    public void habilitar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);

        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }

    }

    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo.");
        }

    }

}
