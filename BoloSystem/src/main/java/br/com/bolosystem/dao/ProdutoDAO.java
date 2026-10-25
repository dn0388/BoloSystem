package br.com.bolosystem.dao;

import Conexao.ConnectionFactory;
import br.com.bolosystem.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** JDBC DAO shared by the Swing desktop and Spring web interface. */
public class ProdutoDAO {
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome().trim());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar produto.", e);
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT id, nome, preco FROM produto ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar produtos.", e);
        }
        return lista;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT id, nome, preco FROM produto WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getDouble("preco"));
                    return p;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar produto.", e);
        }
    }

    public boolean atualizar(int id, Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome().trim());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar produto.", e);
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir produto. Se houver pedidos vinculados, o banco pode impedir a exclusão.", e);
        }
    }
}
