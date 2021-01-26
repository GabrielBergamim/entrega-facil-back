package br.edu.ifsp.entregafacil.controller;

import br.edu.ifsp.entregafacil.dto.AvaliacaoCadastroDTO;
import br.edu.ifsp.entregafacil.dto.AvaliacoesDTO;
import br.edu.ifsp.entregafacil.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @GetMapping("{usuario}")
    public AvaliacoesDTO findMinhasSolicitacoes(@PathVariable Integer usuario,
                                                @RequestParam(value="page", defaultValue = "0")Integer page,
                                                @RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPage,
                                                @RequestParam(value="orderBy", defaultValue = "nota") String orderBy,
                                                @RequestParam(value="direction", defaultValue = "DESC") String direction) {

        return service.findAllAvaliacoes(usuario, page, linesPerPage, direction, orderBy);
    }

    @PostMapping("{avaliador}/{avaliado}")
    public void insert(@PathVariable Integer avaliador, @PathVariable Integer avaliado, @RequestBody AvaliacaoCadastroDTO avaliacao) {
        service.insert(avaliador, avaliado, avaliacao);
    }
}
