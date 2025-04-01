package edu.proyectoFinalAPI.Daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio donde se encuentra las cabecera de los metodos con la relacion de
 * las categorias
 * 
 * @author jpribio - 01/04/2025
 */
@Repository
public interface CategoriaRepositorio extends JpaRepository<CategoriaEntidad, Long> {

	CategoriaEntidad findByNombreCategoria(String nombreCategoria);
}
