package br.com.bolosystem.dao;

import Conexao.ConnectionFactory;
import br.com.bolosystem.model.ItemPedido;
import br.com.bolosystem.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoDAO {
    public void salvar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (cliente_id, data, total) VALUES (?, NOW(), ?)";
        String sqlItem = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int pedidoId;
                try (PreparedStatement stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, pedido.getClienteId()); stmt.setDouble(2, pedido.calcularTotal()); stmt.executeUpdate();
                    try (ResultSet keys = stmt.getGeneratedKeys()) { if (!keys.next()) throw new SQLException("O banco não retornou o ID do pedido."); pedidoId = keys.getInt(1); }
                }
                try (PreparedStatement itemStmt = conn.prepareStatement(sqlItem)) {
                    for (ItemPedido item : pedido.getItens()) { itemStmt.setInt(1, pedidoId); itemStmt.setInt(2, item.getProdutoId()); itemStmt.setInt(3, item.getQuantidade()); itemStmt.setDouble(4, item.getSubtotal()); itemStmt.addBatch(); }
                    itemStmt.executeBatch();
                }
                conn.commit();
            } catch (SQLException | RuntimeException e) {
                try { conn.rollback(); } catch (SQLException rollbackError) { e.addSuppressed(rollbackError); }
                throw e;
            } finally {
                try { conn.setAutoCommit(true); } catch (SQLException ignored) { /* conexão será fechada */ }
            }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao salvar pedido.", e); }
    }
}
