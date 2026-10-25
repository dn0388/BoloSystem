
package br.com.bolosystemweb.web;

import br.com.bolosystem.model.Cliente;
import br.com.bolosystem.service.ClienteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteService service = new ClienteService();

    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente) {
        try {
            service.cadastrar(cliente);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cliente cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable int id,
            @RequestBody Cliente cliente) {
        try {
            boolean atualizado = service.atualizar(id, cliente);

            if (!atualizado) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Cliente atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id) {
        try {
            boolean excluido = service.excluir(id);

            if (!excluido) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
