package com.hame.festa.repository;
import org.springframework.data.repository.CrudRepository;
import com.hame.festa.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Usuario findByLogin(String login);
}
