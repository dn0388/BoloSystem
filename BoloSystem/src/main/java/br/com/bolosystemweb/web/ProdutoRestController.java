package br.com.bolosystemweb.web;
import br.com.bolosystem.model.Produto;
import br.com.bolosystem.service.ProdutoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping
    public List<Produto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable int id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Produto produto = service.buscarPorId(id);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        try {
            service.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Produto cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable int id,
            @RequestBody Produto produto) {
        try {
            boolean atualizado = service.atualizar(id, produto);

            if (!atualizado) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Produto atualizado com sucesso.");
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

