package br.com.food.pagamento.dto;

import br.com.food.pagamento.type.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDto {

    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Long pedidoId;
    private Long formaPagamentoId;
    private Status status;

}
