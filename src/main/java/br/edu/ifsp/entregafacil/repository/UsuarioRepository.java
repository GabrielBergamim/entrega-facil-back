package br.edu.ifsp.entregafacil.repository;

import br.edu.ifsp.entregafacil.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
    @Transactional(readOnly = true)
    Usuario findByEmail(String email);
}
