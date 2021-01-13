package br.edu.ifsp.entregafacil.service;

import br.edu.ifsp.entregafacil.entidade.Entrega;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    private Random rand = new Random();

    @Override
    public void sendOrderConfirmationEmail(Usuario obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromCadastro(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromCadastro(Usuario obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Confirmação email.");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Código de ativação: "+ obj.getCodAuth());

        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Usuario usuario, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Usuario cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha.");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    @Override
    public void sendAtualizacaoStatus(Entrega entrega, Usuario usuario, Usuario entregador, String statusDe) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(usuario.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Atualização do status de entrega.");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Olá senhor(a): "+usuario.getNome() +  "\nAtualização do status do seu pedido de: " + statusDe + " para: "+ entrega.getStatus().toString()
        + "\nAceito por: " + entregador.getNome() + "\nEmail: " + entregador.getEmail() + "\nTelefone: " + entregador.getTelefone());

        sendEmail(sm);
    }
}
