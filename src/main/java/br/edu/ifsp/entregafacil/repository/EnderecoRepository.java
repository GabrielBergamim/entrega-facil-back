package br.edu.ifsp.entregafacil.repository;

import br.edu.ifsp.entregafacil.entidade.Endereco;
import br.edu.ifsp.entregafacil.entidade.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
