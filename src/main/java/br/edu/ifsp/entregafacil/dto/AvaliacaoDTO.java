package br.edu.ifsp.entregafacil.dto;

import java.math.BigDecimal;

public class AvaliacaoDTO {
    private BigDecimal nota;
    private String avaliador;
    private String avaliacao;

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(String avaliador) {
        this.avaliador = avaliador;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }
}
