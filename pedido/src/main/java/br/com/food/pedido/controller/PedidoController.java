package br.com.food.pedido.controller;

import br.com.food.pedido.dto.PedidoDto;
import br.com.food.pedido.dto.StatusDto;
import br.com.food.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDto> obterTodos() {
        return pedidoService.obterTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> listarPorId(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(pedidoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDto> criarPedido(@RequestBody @Valid PedidoDto pedidoDto, UriComponentsBuilder uriBuilder) {
        PedidoDto pedido = pedidoService.criarPedido(pedidoDto);
        return ResponseEntity
                .created(uriBuilder.path("/pedidos/{id}").buildAndExpand(pedido.getId()).toUri())
                .body(pedido);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDto> atualizarStatus(@PathVariable Long id, @RequestBody StatusDto statusDto){
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, statusDto));
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovarPagamento(@PathVariable @NotNull Long id) {
        pedidoService.aprovarPagamento(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/config/porta")
    public String obterPorta(@Value("${local.server.port}") String porta) {
        return String.format("ACESSADO ATRAVÉS DA PORTA:%s", porta);
    }

}
