package br.edu.ifsp.entregafacil.dto;

import javax.validation.constraints.NotEmpty;

public class SenhaDTO {

    @NotEmpty(message="Preenchimento obrigatório")
    private String oldPass;
    @NotEmpty(message="Preenchimento obrigatório")
    private String newPass;

    public SenhaDTO() {}

    public SenhaDTO(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

}
