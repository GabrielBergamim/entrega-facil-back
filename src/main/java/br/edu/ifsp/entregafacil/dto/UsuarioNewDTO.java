package br.edu.ifsp.entregafacil.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class UsuarioNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caractéres")
    private String nome;
    @NotEmpty(message = "Preenchimento obrigatório!")
    @Email(message = "Email inválido")
    private String email;
    @NotEmpty(message = "Preenchimento obrigatório!")
    private String cpf;
    @NotEmpty(message = "Preenchimento obrigatório!")
    private String senha;
    @NotEmpty(message = "Preenchimento obrigatório!")
    private String telefone;

    public UsuarioNewDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
