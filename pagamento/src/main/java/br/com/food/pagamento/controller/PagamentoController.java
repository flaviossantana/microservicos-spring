package br.com.food.pagamento.controller;

import br.com.food.pagamento.dto.PagamentoDto;
import br.com.food.pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDto> obterTodos(@PageableDefault(size = 11) Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> obterPorId(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(pagamentoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> criar(
            @RequestBody @Valid PagamentoDto dto,
            UriComponentsBuilder uriBuilder) {

        PagamentoDto pagamentoDto = pagamentoService.criar(dto);

        URI uri = uriBuilder.path("/pagamento/{id}")
                .buildAndExpand(pagamentoDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pagamentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> alterar(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid PagamentoDto dto) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> excluir(@PathVariable @NotNull Long id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public void confirmarPagamento(@PathVariable @NotNull Long id) {
        pagamentoService.confirmarPagamento(id);
    }

}
