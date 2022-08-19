package br.com.food.pedido.repository;

import br.com.food.pedido.model.Pedido;
import br.com.food.pedido.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Pedido p set p.status = :status where p = :pedido")
    void atualizarStatus(Status status, Pedido pedido);

    @Query(value = "select p from Pedido p left join fetch p.itens where p.id = :id")
    Pedido porIdComItens(Long id);

}
