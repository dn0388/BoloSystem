package br.com.bolosystem.service;

import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {
    private final PedidoService service = new PedidoService();
    @Test void deveAceitarPedidoValido() { Pedido p=new Pedido(1); ItemPedido i=new ItemPedido(); i.setProdutoId(1); i.setQuantidade(2); i.setSubtotal(30); p.adicionarItem(i); assertDoesNotThrow(() -> service.validar(p)); }
    @Test void deveRejeitarPedidoSemCliente() { Pedido p=new Pedido(0); IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->service.validar(p)); assertEquals("Selecione um cliente válido.",e.getMessage()); }
    @Test void deveRejeitarPedidoSemItens() { assertThrows(IllegalArgumentException.class,()->service.validar(new Pedido(1))); }
    
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

    assertEquals(55.50, total, 0.001);
}

}
