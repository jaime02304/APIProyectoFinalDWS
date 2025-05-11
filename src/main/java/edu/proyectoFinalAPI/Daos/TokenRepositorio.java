package edu.proyectoFinalAPI.Daos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

/**
 * clase repositorio de los tokens
 * 
 * @author jpribio - 22/01/25
 */
@Repository
public interface TokenRepositorio extends JpaRepository<TokenEntidad, Long> {
	TokenEntidad findByToken(String token);

	List<TokenEntidad> findByUsuario(UsuarioEntidad usuario);

	void deleteByFechaExpiracionBefore(LocalDateTime fecha);

	@Transactional
	@Modifying
	@Query("DELETE FROM TokenEntidad t WHERE t.usuario = :usuario")
	void deleteByUsuario(@Param("usuario") UsuarioEntidad usuario);

	List<TokenEntidad> findByIsVerificacionTrueAndUsadoFalseAndFechaExpiracionBefore(LocalDateTime fecha);

}
