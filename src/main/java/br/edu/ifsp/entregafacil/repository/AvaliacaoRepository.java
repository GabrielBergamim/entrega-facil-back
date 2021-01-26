package br.edu.ifsp.entregafacil.repository;

import br.edu.ifsp.entregafacil.entidade.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    @Transactional(readOnly=true)
    @Query(value="SELECT obj FROM Avaliacao obj WHERE obj.avaliado.id = :avaliado_id ORDER BY obj.nota ASC")
    Page<Avaliacao> findAllAvalicoesByAvaliado(@Param("avaliado_id") Integer avaliado, Pageable pageRequest);

    @Query(value="SELECT AVG(obj.nota) FROM Avaliacao obj WHERE obj.avaliado.id = :avaliado_id")
    BigDecimal findMediaNotaAvaliado(@Param("avaliado_id") Integer avaliado);
}
