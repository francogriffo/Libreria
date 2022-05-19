package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import edu.egg.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public void registrar(Long isbn, String titulo, Integer anio, Autor autor, Editorial editorial) throws ErrorServicio {

        validar(isbn, titulo, anio, autor, editorial);

        Libro libro = new Libro();
        
         List<Libro> respuesta = libroRepositorio.buscarPorIsbn(isbn);
        if (respuesta==null) {
         libro.setIsbn(isbn);
         libro.setEjemplares(1);
        } else {
            Integer ejemplaresCargados = 0;
            for (Libro libro1 : respuesta) {
               ejemplaresCargados += libro1.getEjemplares();              
            }
            libro.setEjemplares(ejemplaresCargados+1);
        }
        libro.setTitulo(titulo);
        libro.setAnio(anio);       
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libroRepositorio.delete(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial) throws ErrorServicio {

        validar(isbn, titulo, anio, autor, editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws ErrorServicio {

        try {
            return libroRepositorio.buscarPorId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarPorTitulo(String titulo) throws ErrorServicio {

        try {
            return libroRepositorio.buscarPorTitulo(titulo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
     @Transactional(readOnly = true)
    public List<Libro> buscarPorAutor(String nombre) throws ErrorServicio {

        try {
            return libroRepositorio.buscarPorAutor(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarTodos() throws ErrorServicio {

        try {
            return libroRepositorio.buscarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }

    }

    public void validar(Long isbn, String titulo, Integer anio, Autor autor, Editorial editorial) throws ErrorServicio {

        if (isbn == null || isbn.toString().trim().isEmpty()) {
            throw new ErrorServicio("El ISBN del libro no puede ser nulo o estar vacio.");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo del libro no puede ser nulo o estar vacio.");
        }

        if (anio == null || anio.toString().trim().isEmpty()) {
            throw new ErrorServicio("El año del libro no puede ser nulo o estar vacio.");
        }

//        if (ejemplares == null || ejemplares.toString().trim().isEmpty()) {
//            throw new ErrorServicio("La cantidad de ejemplares del libro no puede ser nula o estar vacia.");
//        }
//
//        if (ejemplaresPrestados == null || ejemplaresPrestados.toString().trim().isEmpty()) {
//            throw new ErrorServicio("La cantidad de ejemplares prestados del libro no puede ser nula o estar vacia.");
//        }
//
//        if (ejemplaresRestantes == null || ejemplaresRestantes.toString().trim().isEmpty()) {
//            throw new ErrorServicio("La cantidad de ejemplares restantes del libro no puede ser nula o estar vacia.");
//        }
        if (autor == null || autor.toString().trim().isEmpty()) {
            throw new ErrorServicio("El autor del libro no puede ser nulo o estar vacio.");
        }

        if (editorial == null || editorial.toString().trim().isEmpty()) {
            throw new ErrorServicio("La editorial del libro no puede ser nula o estar vacia.");
        }
    }

}
