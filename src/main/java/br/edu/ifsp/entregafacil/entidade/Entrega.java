package br.edu.ifsp.entregafacil.entidade;

import br.edu.ifsp.entregafacil.dto.StatusEntrega;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Entrega {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    private LocalDate dataSolicitada;
    private LocalDate dataEntrega;

    @ManyToOne()
    @JoinColumn(name="solicitante_id")
    private Usuario solicitante;

    @ManyToOne()
    @JoinColumn(name="entregador_id")
    private Usuario entregador;

    @ManyToOne()
    @JoinColumn(name="endereco_retirada_id")
    private Endereco enderecoRetirada;

    @ManyToOne()
    @JoinColumn(name="endereco_entrega_id")
    private Endereco enderecoEntrega;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public void setStatus(StatusEntrega status) {
        this.status = status;
    }

    public LocalDate getDataSolicitada() {
        return dataSolicitada;
    }

    public void setDataSolicitada(LocalDate dataSolicitada) {
        this.dataSolicitada = dataSolicitada;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getEntregador() {
        return entregador;
    }

    public void setEntregador(Usuario entregador) {
        this.entregador = entregador;
    }

    public Endereco getEnderecoRetirada() {
        return enderecoRetirada;
    }

    public void setEnderecoRetirada(Endereco enderecoRetirada) {
        this.enderecoRetirada = enderecoRetirada;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}
