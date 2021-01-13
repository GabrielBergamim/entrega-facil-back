package br.edu.ifsp.entregafacil.service;

import br.edu.ifsp.entregafacil.entidade.Entrega;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Usuario obj);

    void sendEmail(SimpleMailMessage msg);

    void sendNewPasswordEmail(Usuario cliente, String newPass);

    void sendAtualizacaoStatus(Entrega entrega, Usuario usuario, Usuario entregador, String statusDe);

}
