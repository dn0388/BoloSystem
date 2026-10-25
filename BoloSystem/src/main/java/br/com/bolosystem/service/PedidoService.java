package br.com.bolosystem.service;

import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;

public class PedidoService {

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

            if (item.getSubtotal() < 0) {
                throw new IllegalArgumentException(
                    "O subtotal não pode ser negativo."
                );
            }
        }
    }
}
