package br.edu.ifsp.entregafacil.controller;

import br.edu.ifsp.entregafacil.dto.EntregaDTO;
import br.edu.ifsp.entregafacil.entidade.Entrega;
import br.edu.ifsp.entregafacil.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @GetMapping("{codigo}")
    public Entrega findPage(@PathVariable Integer codigo) {

        return entregaService.findById(codigo);
    }

    @GetMapping("{usuario}/solicitacoesabertas")
    public Page<Entrega> findPage(@PathVariable Integer usuario,
                                  @RequestParam(value="page", defaultValue = "0")Integer page,
                                  @RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPage) {

        return entregaService.findEntregasAbertas(usuario, page, linesPerPage);
    }

    @GetMapping("{usuario}/minhasolicitacoes")
    public Page<Entrega> findMinhasSolicitacoes(@PathVariable Integer usuario,
                                  @RequestParam(value="page", defaultValue = "0")Integer page,
                                  @RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPage) {

        return entregaService.findMinhasSolicitacoes(usuario, page, linesPerPage);
    }

    @GetMapping("{usuario}/minhasolicitacoesentrega")
    public Page<Entrega> findMinhasSolicitacoesEntrega(@PathVariable Integer usuario,
                                                @RequestParam(value="page", defaultValue = "0")Integer page,
                                                @RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPage) {

        return entregaService.findMinhasSolicitacoesEntrega(usuario, page, linesPerPage);
    }

    @PostMapping()
    public Integer insert(@RequestBody EntregaDTO objDTO){
        return entregaService.insert(objDTO);
    }

    @PutMapping("{entregador}/{solicitacao}")
    public Entrega alterarStatus(@PathVariable Integer entregador, @PathVariable Integer solicitacao) {
        return entregaService.alterarStatus(entregador, solicitacao);
    }


}
