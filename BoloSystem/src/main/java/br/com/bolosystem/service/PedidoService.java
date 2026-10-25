package br.com.bolosystem.service;

import br.com.bolosystem.dao.ClienteDAO;
import br.com.bolosystem.dao.PedidoDAO;
import br.com.bolosystem.dao.ProdutoDAO;
import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;
import br.com.bolosystem.model.Produto;

public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    public void validar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException(
                "O pedido não pode ser nulo."
            );
        }

        if (pedido.getClienteId() <= 0) {
            throw new IllegalArgumentException(
                "Selecione um cliente válido."
            );
        }

        if (pedido.getItens() == null
                || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                "O pedido deve possuir pelo menos um produto."
            );
        }

        for (ItemPedido item : pedido.getItens()) {
            if (item == null) {
                throw new IllegalArgumentException(
                    "O item do pedido é inválido."
                );
            }

            if (item.getProdutoId() <= 0) {
                throw new IllegalArgumentException(
                    "Selecione um produto válido."
                );
            }

            if (item.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
                );
            }

            if (item.getSubtotal() < 0
                    || !Double.isFinite(item.getSubtotal())) {
                throw new IllegalArgumentException(
                    "O subtotal deve ser válido e não negativo."
                );
            }
        }
    }

    public double calcularTotal(Pedido pedido) {
        validar(pedido);
        return pedido.calcularTotal();
    }

    public void cadastrar(Pedido pedido) {
        validar(pedido);

        if (!clienteDAO.existeId(pedido.getClienteId())) {
            throw new IllegalArgumentException(
                "Cliente não encontrado."
            );
        }

        for (ItemPedido item : pedido.getItens()) {
            Produto produto =
                produtoDAO.buscarPorId(item.getProdutoId());

            if (produto == null) {
                throw new IllegalArgumentException(
                    "Produto não encontrado: "
                    + item.getProdutoId()
                );
            }

            item.setSubtotal(
                produto.getPreco() * item.getQuantidade()
            );
        }

        pedidoDAO.salvar(pedido);
    }
}