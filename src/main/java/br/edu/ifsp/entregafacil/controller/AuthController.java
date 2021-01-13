package br.edu.ifsp.entregafacil.controller;

import br.edu.ifsp.entregafacil.dto.EmailDTO;
import br.edu.ifsp.entregafacil.dto.LoginDTO;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.security.JWTUtil;
import br.edu.ifsp.entregafacil.security.UserSS;
import br.edu.ifsp.entregafacil.service.AuthService;
import br.edu.ifsp.entregafacil.service.UserDetailsServiceImpl;
import br.edu.ifsp.entregafacil.service.UserService;
import br.edu.ifsp.entregafacil.service.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder pe;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public void refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public void forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
    }

    @PostMapping("/login")
    public Usuario login(@Valid @RequestBody LoginDTO login, HttpServletResponse response){
        UserSS user =  userService.loadUserByUsername(login.getEmail());

        if(!user.isEnabled()) {
            throw new AuthorizationException("Email está inativo!");
        }

        if(!pe.matches(login.getSenha(), user.getPassword())){
            throw new AuthorizationException("Email ou senha inválidos!");
        }

        Usuario saida = new Usuario();
        saida.setId(user.getId());
        saida.setEmail(user.getEmail());

        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);

        return saida;
    }
}
