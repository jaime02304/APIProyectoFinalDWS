package edu.proyectoFinalAPI.Daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio donde se encuentra las cabecera de los metodos con la relacion de
 * las subcategorias
 * 
 * @author jpribio - 01/04/2025
 */
@Repository
public interface SubcategoriaRepositorio  extends JpaRepository<SubcategoriaEntidad, Long>{

	SubcategoriaEntidad  findByNombreSubcategoria(String nombreSubcategoria);

}
