package br.edu.ifsp.entregafacil.service;

import br.edu.ifsp.entregafacil.dto.SenhaDTO;
import br.edu.ifsp.entregafacil.dto.UsuarioNewDTO;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.repository.UsuarioRepository;
import br.edu.ifsp.entregafacil.service.exceptions.DataIntegrityException;
import br.edu.ifsp.entregafacil.service.exceptions.InvalidOldPassowordException;
import br.edu.ifsp.entregafacil.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository repository;

    @Autowired
    public BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public Usuario find(Integer id) throws ObjectNotFoundException {
        Optional<Usuario> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public Usuario confirm(String codAuth) throws DataIntegrityException {
        Usuario obj = find(retornaId(codAuth));

        if(obj.getAtivo()) {
            throw new DataIntegrityException("Usuário " + obj.getNome() + " já está ativo");
        }else {
            if (obj.getCodAuth().equals(codAuth)) {
                obj.setAtivo(true);
                repository.save(obj);
            } else {
                throw new DataIntegrityException("Código inválido: " + codAuth);
            }
        }

        return obj;
    }

    @Transactional
    public Usuario insert(Usuario obj) {
        obj.setId(null);

        if(repository.findByEmail(obj.getEmail()) != null) {
            throw new DataIntegrityException("E-mail já cadastrado");
        }

        obj = repository.save(obj);
        obj.setCodAuth(obj.getId().toString()+"0"+newCodigoAuth());
       // emailService.sendOrderConfirmationEmail(obj);

        return obj;
    }

    @Transactional
    public Usuario update(Usuario obj) {
        Optional<Usuario> newObj = repository.findById(obj.getId());

        newObj.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + obj.getId() + ", Tipo: " + Usuario.class.getName()));

        updateData(newObj.get(), obj);

        return repository.save(newObj.get());
    }

    public void updateSenha(Integer id, SenhaDTO senhaDTO) {
        Usuario obj = find(id);

        String oldPass = senhaDTO.getOldPass();
        String newPass = senhaDTO.getNewPass();

        if(!pe.matches(oldPass, obj.getSenha())){
            throw new InvalidOldPassowordException("Senha antiga diferente.");
        }

        obj.setSenha(pe.encode(newPass));
        repository.save(obj);
    }

    public Usuario fromDTO(UsuarioNewDTO usuarioNewDTO){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioNewDTO.getEmail());
        usuario.setNome(usuarioNewDTO.getNome());
        usuario.setCpf(usuarioNewDTO.getCpf());
        usuario.setTelefone(usuarioNewDTO.getTelefone());
        usuario.setSenha(pe.encode(usuarioNewDTO.getSenha()));

        return usuario;
    }

    private void updateData(Usuario newObj, Usuario obj){
        newObj.setEmail(obj.getEmail());
        newObj.setNome(obj.getNome());
        newObj.setTelefone(obj.getTelefone());
    }

    private String newCodigoAuth() {
        char[] vet = new char[6];
        for (int i=0; i<6; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (rand.nextInt(10) + 48);
        }
        else if (opt == 1) { // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }
        else { // gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }

    private Integer retornaId(String codAuth) {
        String id = "";
        for(int i=0; i<codAuth.length(); i++){
            char divider = codAuth.charAt(i);

            if(divider == '0'){
                id = codAuth.substring(0, i);
                break;
            }
        }

        return Integer.parseInt(id);
    }
}
