package br.edu.ifsp.entregafacil.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AvaliacoesDTO {
    private String nome;
    private BigDecimal media;
    private Boolean lastPage;
    private List<AvaliacaoDTO> content;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getMedia() {
        return media;
    }

    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<AvaliacaoDTO> getContent() {
        return content;
    }

    public void setContent(List<AvaliacaoDTO> content) {
        this.content = content;
    }
}
