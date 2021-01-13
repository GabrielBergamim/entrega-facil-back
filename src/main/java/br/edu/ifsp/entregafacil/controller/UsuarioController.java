package br.edu.ifsp.entregafacil.controller;

import br.edu.ifsp.entregafacil.dto.SenhaDTO;
import br.edu.ifsp.entregafacil.dto.UsuarioNewDTO;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/{id}")
    public Usuario find(@PathVariable Integer id) {
        Usuario obj  = service.find(id);

        return obj;
    }

    @PostMapping()
    public Integer insert(@Valid @RequestBody UsuarioNewDTO objDTO){
        Usuario obj = service.fromDTO(objDTO);
        obj.setAtivo(true);
        obj = service.insert(obj);

        return obj.getId();
    }

    @PutMapping("/confirm")
    public Usuario confirm(@RequestParam("codAuth") String codAuth){
        Usuario obj = service.confirm(codAuth);

        return obj;
    }

    @PutMapping("senha/{id}")
    public void update(@Valid @RequestBody SenhaDTO senhaDTO, @PathVariable Integer id){
        service.updateSenha(id, senhaDTO);
    }
}
