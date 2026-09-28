package com.proyectointegrador.msusuarios.repository;

import com.proyectointegrador.msusuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
