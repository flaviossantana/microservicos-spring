package br.com.food.pagamento.service;

import br.com.food.pagamento.client.PedidoClient;
import br.com.food.pagamento.dto.PagamentoDto;
import br.com.food.pagamento.model.Pagamento;
import br.com.food.pagamento.repository.PagamentoRepository;
import br.com.food.pagamento.type.StatusPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedidoClient;

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDto.class));
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criar(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(StatusPagamento.CRIADO);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizar(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluir(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public void confirmarPagamento(Long id) {

        Pagamento pagamento = pagamentoRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);


        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        pagamentoRepository.save(pagamento);

        pedidoClient.atualizarPagamento(pagamento.getPedidoId());

    }

}



