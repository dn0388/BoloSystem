
package br.com.bolosystemweb.web;

import br.com.bolosystem.model.Pedido;
import br.com.bolosystem.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {

    private final PedidoService service = new PedidoService();

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Pedido pedido) {
        try {
            service.cadastrar(pedido);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Pedido salvo com sucesso. Total: "
                            + pedido.calcularTotal());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
