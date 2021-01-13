package br.edu.ifsp.entregafacil.dto;

public class EntregaDTO {

    private Integer idSolicitante;
    private EnderecoDTO enderecoRetirada;
    private EnderecoDTO enderecoEntrega;

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public EnderecoDTO getEnderecoRetirada() {
        return enderecoRetirada;
    }

    public void setEnderecoRetirada(EnderecoDTO enderecoRetirada) {
        this.enderecoRetirada = enderecoRetirada;
    }

    public EnderecoDTO getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoDTO enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}
