package br.edu.ifsp.entregafacil.service;
;
import br.edu.ifsp.entregafacil.dto.AvaliacaoCadastroDTO;
import br.edu.ifsp.entregafacil.dto.AvaliacaoDTO;
import br.edu.ifsp.entregafacil.dto.AvaliacoesDTO;
import br.edu.ifsp.entregafacil.entidade.Avaliacao;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.repository.AvaliacaoRepository;
import br.edu.ifsp.entregafacil.repository.UsuarioRepository;
import br.edu.ifsp.entregafacil.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AvaliacoesDTO findAllAvaliacoes(Integer usuario, Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        BigDecimal media = repository.findMediaNotaAvaliado(usuario);
        Usuario usuarioEntity = usuarioRepository.findById(usuario).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + usuario + ", Tipo: " + Usuario.class.getName()));
        AvaliacoesDTO avaliacoesDTO = new AvaliacoesDTO();
        avaliacoesDTO.setMedia(media);


        Page<Avaliacao> page1 = repository.findAllAvalicoesByAvaliado(usuario, pageRequest);
        avaliacoesDTO.setLastPage(page1.isLast());
        List<AvaliacaoDTO> content = new ArrayList<>();

        if(page1.getContent() != null) {
            page1.getContent().forEach(avaliacao -> {
                AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
                avaliacaoDTO.setAvaliacao(avaliacao.getComentario());
                avaliacaoDTO.setNota(avaliacao.getNota());
                avaliacaoDTO.setAvaliador(avaliacao.getAvalidor().getNome());

                content.add(avaliacaoDTO);
            });
            avaliacoesDTO.setNome(usuarioEntity.getNome());
            avaliacoesDTO.setContent(content);
        }

        return avaliacoesDTO;
    }

    public void insert(Integer idAvaliador, Integer idAvaliado, AvaliacaoCadastroDTO avaliacao) {
        Avaliacao entity = new Avaliacao();
        Usuario avaliador = usuarioRepository.findById(idAvaliador).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + idAvaliador + ", Tipo: " + Usuario.class.getName()));
        Usuario avaliado = usuarioRepository.findById(idAvaliado).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + idAvaliado + ", Tipo: " + Usuario.class.getName()));

        entity.setAvalidor(avaliador);
        entity.setAvaliado(avaliado);
        entity.setComentario(avaliacao.getComentario());
        entity.setNota(avaliacao.getNota());

        repository.save(entity);
    }
}
