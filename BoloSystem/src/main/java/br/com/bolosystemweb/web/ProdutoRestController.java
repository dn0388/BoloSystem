package br.com.bolosystemweb.web;

import br.com.bolosystem.dao.ProdutoDAO;
import br.com.bolosystem.model.Produto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {
    private final ProdutoDAO dao = new ProdutoDAO();
    @GetMapping public List<Produto> listar() { return dao.listar(); }
    @GetMapping("/{id}") public ResponseEntity<Produto> buscar(@PathVariable int id) {
        Produto p = id > 0 ? dao.buscarPorId(id) : null; return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }
    @PostMapping public ResponseEntity<?> cadastrar(@RequestBody Produto p) {
        if (!valido(p)) return ResponseEntity.badRequest().body("Informe nome e preço não negativo.");
        dao.inserir(p); return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso.");
    }
    @PutMapping("/{id}") public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody Produto p) {
        if (id <= 0 || !valido(p)) return ResponseEntity.badRequest().body("Dados inválidos.");
        return dao.atualizar(id, p) ? ResponseEntity.ok("Produto atualizado.") : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> excluir(@PathVariable int id) {
        if (id <= 0) return ResponseEntity.badRequest().build();
        return dao.excluir(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    private boolean valido(Produto p) { return p != null && p.getNome() != null && !p.getNome().isBlank() && p.getPreco() >= 0 && Double.isFinite(p.getPreco()); }
}
