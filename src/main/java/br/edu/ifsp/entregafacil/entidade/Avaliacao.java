package br.edu.ifsp.entregafacil.entidade;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal nota;

    private String comentario;

    @ManyToOne()
    @JoinColumn(name="avaliador_id")
    private Usuario avalidor;

    @ManyToOne()
    @JoinColumn(name="avaliado_id")
    private Usuario avaliado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAvalidor() {
        return avalidor;
    }

    public void setAvalidor(Usuario avalidor) {
        this.avalidor = avalidor;
    }

    public Usuario getAvaliado() {
        return avaliado;
    }

    public void setAvaliado(Usuario avaliado) {
        this.avaliado = avaliado;
    }
}
