/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        double total = 0;
        for (ItemPedido i : itens) {
            total += i.getSubtotal();
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

