
package br.com.bolosystem.service;

import br.com.bolosystem.model.Pedido;

public class PedidoServiceMainCheck {

    public static void main(String[] args) {
        PedidoService service = new PedidoService();

        try {
            service.validar(null);
            throw new AssertionError(
                    "Um pedido nulo deveria ser rejeitado."
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "OK: pedido nulo rejeitado."
            );
        }

        try {
            service.validar(new Pedido());
            throw new AssertionError(
                    "Um pedido sem cliente deveria ser rejeitado."
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "OK: pedido sem cliente rejeitado."
            );
        }

        System.out.println("Verificação concluída.");
    }
}
