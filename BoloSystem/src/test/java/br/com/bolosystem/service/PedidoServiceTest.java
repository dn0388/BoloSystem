
package br.com.bolosystem.service;

import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private final PedidoService service = new PedidoService();

    @Test
    void deveAceitarPedidoValido() {
        Pedido pedido = new Pedido(1);

        ItemPedido item = new ItemPedido();
        item.setProdutoId(1);
        item.setQuantidade(2);
        item.setSubtotal(30.00);

        pedido.adicionarItem(item);

        assertDoesNotThrow(() -> service.validar(pedido));
    }

    @Test
    void deveRejeitarPedidoSemCliente() {
        Pedido pedido = new Pedido(0);

        IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> service.validar(pedido)
        );

        assertEquals(
            "Selecione um cliente válido.",
            erro.getMessage()
        );
    }

    @Test
    void deveRejeitarPedidoSemItens() {
        Pedido pedido = new Pedido(1);

        assertThrows(
            IllegalArgumentException.class,
            () -> service.validar(pedido)
        );
    }

    @Test
    void deveCalcularTotalDoPedido() {
        Pedido pedido = new Pedido(1);

        ItemPedido item1 = new ItemPedido();
        item1.setProdutoId(1);
        item1.setQuantidade(2);
        item1.setSubtotal(20.00);

        ItemPedido item2 = new ItemPedido();
        item2.setProdutoId(2);
        item2.setQuantidade(1);
        item2.setSubtotal(15.50);

        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        double total = service.calcularTotal(pedido);

        assertEquals(35.50, total, 0.001);
    }
}
