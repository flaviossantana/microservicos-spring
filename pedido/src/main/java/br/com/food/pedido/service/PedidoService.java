package br.com.food.pedido.service;

import br.com.food.pedido.dto.PedidoDto;
import br.com.food.pedido.dto.StatusDto;
import br.com.food.pedido.model.Pedido;
import br.com.food.pedido.model.Status;
import br.com.food.pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PedidoDto> obterTodos() {
        return repository
                .findAll()
                .stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = repository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pedido, PedidoDto.class);
    }


    public PedidoDto criarPedido(PedidoDto pedidoDto){

        Pedido pedido = modelMapper.map(pedidoDto, Pedido.class);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(i -> i.setPedido(pedido));

        Pedido save = repository.save(pedido);
        return modelMapper.map(save, PedidoDto.class);
    }

    public PedidoDto atualizarStatus(Long id, StatusDto status){
        Pedido pedido = repository.porIdComItens(id);

        if(pedido == null){
            throw new EntityNotFoundException();
        }

        pedido.setStatus(status.getStatus());
        repository.atualizarStatus(status.getStatus(), pedido);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovarPagamentoPedido(Long id){
        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null){
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizarStatus(Status.PAGO, pedido);
    }



}
