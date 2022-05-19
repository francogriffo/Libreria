package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT c FROM Libro c WHERE c.id = :id")
    public Libro buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Libro c WHERE c.titulo LIKE %:titulo%")
    public List<Libro> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT c FROM Libro c")
    public List<Libro> buscarTodos();
    
    @Query("SELECT c FROM Libro c WHERE c.isbn = :isbn")
    public List<Libro> buscarPorIsbn(@Param("isbn") Long isbn);
    
    @Query("SELECT c FROM Libro c WHERE c.autor.nombre LIKE %:nombre%")
    public List<Libro> buscarPorAutor(@Param("nombre") String nombre);
    

}
