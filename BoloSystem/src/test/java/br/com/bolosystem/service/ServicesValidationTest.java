
package br.com.bolosystem.service;

import br.com.bolosystem.model.Cliente;
import br.com.bolosystem.model.Pedido;
import br.com.bolosystem.model.Produto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicesValidationTest {

    @Test
    public void produtoNuloDeveSerRejeitado() {
        ProdutoService service = new ProdutoService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(null)
        );
    }

    @Test
    public void produtoSemNomeDeveSerRejeitado() {
        ProdutoService service = new ProdutoService();

        Produto produto = new Produto();
        produto.setNome(" ");
        produto.setPreco(10.0);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(produto)
        );
    }

    @Test
    public void produtoComPrecoNegativoDeveSerRejeitado() {
        ProdutoService service = new ProdutoService();

        Produto produto = new Produto();
        produto.setNome("Bolo de chocolate");
        produto.setPreco(-1.0);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(produto)
        );
    }

    @Test
    public void clienteNuloDeveSerRejeitado() {
        ClienteService service = new ClienteService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(null)
        );
    }

    @Test
    public void clienteSemNomeDeveSerRejeitado() {
        ClienteService service = new ClienteService();

        Cliente cliente = new Cliente();
        cliente.setNome(" ");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(cliente)
        );
    }

    @Test
    public void pedidoNuloDeveSerRejeitado() {
        PedidoService service = new PedidoService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(null)
        );
    }

    @Test
    public void pedidoSemClienteValidoDeveSerRejeitado() {
        PedidoService service = new PedidoService();

        Pedido pedido = new Pedido();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.validar(pedido)
        );
    }
}
