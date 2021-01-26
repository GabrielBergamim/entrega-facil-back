package br.edu.ifsp.entregafacil.repository;

import br.edu.ifsp.entregafacil.entidade.Entrega;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EntregaRepository extends JpaRepository<Entrega, Integer> {

    @Transactional(readOnly=true)
    @Query(value="SELECT obj FROM Entrega obj WHERE obj.solicitante.id <> :solicitante_id AND obj.status = 'ABERTO'")
    Page<Entrega> findEntregasAbertas(@Param("solicitante_id") Integer solicitante_id, Pageable pageRequest);

    @Transactional(readOnly=true)
    @Query(value="SELECT obj FROM Entrega obj WHERE obj.solicitante.id = :solicitante_id")
    Page<Entrega> findMinhasSolicitacoes(@Param("solicitante_id") Integer solicitante_id, Pageable pageRequest);

    @Transactional(readOnly=true)
    @Query(value="SELECT obj FROM Entrega obj WHERE obj.entregador.id = :entregador_id")
    Page<Entrega> findMinhasSolicitacoesEntrega(@Param("entregador_id") Integer entregador, Pageable pageRequest);
}
