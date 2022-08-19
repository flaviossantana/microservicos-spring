package br.com.food.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDto {

    private Long id;

    @NotNull
    @Positive
    private Integer quantidade;

    private String descricao;

}
