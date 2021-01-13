package br.edu.ifsp.entregafacil.service;

import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.repository.UsuarioRepository;
import br.edu.ifsp.entregafacil.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserSS loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario cli = repo.findByEmail(email);

        if (cli == null) {
            throw new UsernameNotFoundException("E-mail ou senha inválios!");
        }

        return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getAtivo());
    }
}
