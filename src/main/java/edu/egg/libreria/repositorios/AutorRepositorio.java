package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("SELECT c FROM Autor c WHERE c.id = :id")
    public Autor buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Autor c WHERE c.nombre LIKE %:nombre%")
    public List<Autor> buscarPorNombre(@Param("nombre") String nombre);    
    
    @Query("SELECT c FROM Autor c")
    public List<Autor> buscarTodos();

}
