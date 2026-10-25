package br.com.bolosystemweb.web;

import br.com.bolosystem.dao.ClienteDAO;
import br.com.bolosystem.dao.PedidoDAO;
import br.com.bolosystem.dao.ProdutoDAO;
import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;
import br.com.bolosystem.model.Produto;
import br.com.bolosystem.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final PedidoService service = new PedidoService();

    @PostMapping public ResponseEntity<?> cadastrar(@RequestBody Pedido pedido) {
        try {
            service.validar(pedido);
            if (!clienteDAO.existeId(pedido.getClienteId())) return ResponseEntity.badRequest().body("Cliente não encontrado.");
            for (ItemPedido item : pedido.getItens()) {
                Produto produto = produtoDAO.buscarPorId(item.getProdutoId());
                if (produto == null) return ResponseEntity.badRequest().body("Produto não encontrado: " + item.getProdutoId());
                item.setSubtotal(produto.getPreco() * item.getQuantidade());
            }
            pedidoDAO.salvar(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido salvo com sucesso. Total: " + pedido.calcularTotal());
        } catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
