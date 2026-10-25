
package br.com.bolosystem.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private int clienteId;
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(int clienteId) {
        this.clienteId = clienteId;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0.0;

        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }

        return total;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}
