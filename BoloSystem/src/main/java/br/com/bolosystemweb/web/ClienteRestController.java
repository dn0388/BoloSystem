package br.com.bolosystemweb.web;

import br.com.bolosystem.dao.ClienteDAO;
import br.com.bolosystem.model.Cliente;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
    private final ClienteDAO dao = new ClienteDAO();
    @GetMapping public List<Cliente> listar() { return dao.listar(); }
    @PostMapping public ResponseEntity<?> cadastrar(@RequestBody Cliente c) {
        if (!valido(c)) return ResponseEntity.badRequest().body("Nome do cliente é obrigatório.");
        dao.cadastrar(c); return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado.");
    }
    @PutMapping("/{id}") public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody Cliente c) {
        if (id <= 0 || !valido(c)) return ResponseEntity.badRequest().body("Dados inválidos.");
        return dao.atualizar(id, c) ? ResponseEntity.ok("Cliente atualizado.") : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> excluir(@PathVariable int id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        return dao.excluir(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    private boolean valido(Cliente c) { return c != null && c.getNome() != null && !c.getNome().isBlank(); }
}
