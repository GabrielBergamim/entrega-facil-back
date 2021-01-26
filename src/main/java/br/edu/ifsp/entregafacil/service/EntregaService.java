package br.edu.ifsp.entregafacil.service;

import br.edu.ifsp.entregafacil.dto.EntregaDTO;
import br.edu.ifsp.entregafacil.dto.StatusEntrega;
import br.edu.ifsp.entregafacil.entidade.Endereco;
import br.edu.ifsp.entregafacil.entidade.Entrega;
import br.edu.ifsp.entregafacil.entidade.Usuario;
import br.edu.ifsp.entregafacil.repository.EnderecoRepository;
import br.edu.ifsp.entregafacil.repository.EntregaRepository;
import br.edu.ifsp.entregafacil.repository.UsuarioRepository;
import br.edu.ifsp.entregafacil.service.exceptions.DataIntegrityException;
import br.edu.ifsp.entregafacil.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    public Page<Entrega> findEntregasAbertas(Integer usuario, Integer page, Integer linesPerPage) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);

        return repository.findEntregasAbertas(usuario, pageRequest);
    }

    public Page<Entrega> findMinhasSolicitacoes(Integer usuario, Integer page, Integer linesPerPage) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);

        return repository.findMinhasSolicitacoes(usuario, pageRequest);
    }

    public Page<Entrega> findMinhasSolicitacoesEntrega(Integer usuario, Integer page, Integer linesPerPage) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);

        return repository.findMinhasSolicitacoesEntrega(usuario, pageRequest);
    }

    public Integer insert(EntregaDTO entrega) {
        Entrega entity = new Entrega();
        Usuario solicitante = new Usuario();
        Endereco enderecoRetirada = new Endereco();
        Endereco enderecoEntrega = new Endereco();

        enderecoRetirada.setBairro(entrega.getEnderecoRetirada().getBairro());
        enderecoRetirada.setCep(entrega.getEnderecoRetirada().getCep());
        enderecoRetirada.setCidade(entrega.getEnderecoRetirada().getCidade());
        enderecoRetirada.setEstado(entrega.getEnderecoRetirada().getEstado());
        enderecoRetirada.setLogradouro(entrega.getEnderecoRetirada().getLogradouro());
        enderecoRetirada.setNumero(entrega.getEnderecoRetirada().getNumero());

        enderecoEntrega.setBairro(entrega.getEnderecoEntrega().getBairro());
        enderecoEntrega.setCep(entrega.getEnderecoEntrega().getCep());
        enderecoEntrega.setCidade(entrega.getEnderecoEntrega().getCidade());
        enderecoEntrega.setEstado(entrega.getEnderecoEntrega().getEstado());
        enderecoEntrega.setLogradouro(entrega.getEnderecoEntrega().getLogradouro());
        enderecoEntrega.setNumero(entrega.getEnderecoEntrega().getNumero());

        enderecoRetirada = enderecoRepository.save(enderecoRetirada);
        enderecoEntrega = enderecoRepository.save(enderecoEntrega);

        solicitante.setId(entrega.getIdSolicitante());
        entity.setStatus(StatusEntrega.ABERTO);
        entity.setDataSolicitada(LocalDate.now());
        entity.setEnderecoRetirada(enderecoRetirada);
        entity.setEnderecoEntrega(enderecoEntrega);
        entity.setSolicitante(solicitante);

        entity = repository.save(entity);

        return entity.getId();
    }

    public void alterarStatus(Integer codigoEntregador, Integer codigoEntrega) {
        Entrega entrega = repository.findById(codigoEntrega).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + codigoEntrega + ", Tipo: " + Entrega.class.getName()));

        Usuario entregador = usuarioRepository.findById(codigoEntregador).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + codigoEntregador + ", Tipo: " + Usuario.class.getName()));

        Usuario usuario = usuarioRepository.findById(entrega.getId()).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + entrega.getId() + ", Tipo: " + Usuario.class.getName()));

        String statusDe = entrega.getStatus().toString();

        switch (entrega.getStatus()) {
            case ABERTO: entrega.setStatus( StatusEntrega.ACEITO); entrega.setEntregador(entregador); repository.save(entrega);break;
            case ACEITO: entrega.setStatus(StatusEntrega.ENTREGA_ANDAMENTO); repository.save(entrega);break;
            case ENTREGA_ANDAMENTO: entrega.setStatus(StatusEntrega.ENTREGUE); entrega.setDataEntrega(LocalDate.now()); repository.save(entrega);break;
            default:
                throw new DataIntegrityException("Status da entrega não pode ser atualizado!");
        }

       emailService.sendAtualizacaoStatus(entrega, usuario, entregador, statusDe);
    }
}
