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
    @Test void deveCalcularTotalDoPedido() { Pedido p=new Pedido(1); ItemPedido a=new ItemPedido();a.setSubtotal(20);ItemPedido b=new ItemPedido();b.setSubtotal(15.5);p.adicionarItem(a);p.adicionarItem(b);assertEquals(35.5,p.calcularTotal(),0.001); }
}
