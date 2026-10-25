package br.com.bolosystem.dao;

import Conexao.ConnectionFactory;
import br.com.bolosystem.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, contato, cpf, endereco, ponto_referencia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencher(stmt, cliente); stmt.executeUpdate();
        } catch (SQLException e) { throw new IllegalStateException("Erro ao cadastrar cliente.", e); }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, contato, cpf, endereco, ponto_referencia FROM cliente ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) { Cliente c = new Cliente(); c.setId(rs.getInt("id")); c.setNome(rs.getString("nome")); c.setContato(rs.getString("contato")); c.setCpf(rs.getString("cpf")); c.setEndereco(rs.getString("endereco")); c.setPontoReferencia(rs.getString("ponto_referencia")); lista.add(c); }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar clientes.", e); }
        return lista;
    }

    public int buscarIdPorNome(String nomeCliente) {
        String sql = "SELECT id FROM cliente WHERE nome = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCliente); try (ResultSet rs = stmt.executeQuery()) { return rs.next() ? rs.getInt("id") : -1; }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao buscar cliente.", e); }
    }

    public boolean existeId(int id) {
        String sql = "SELECT 1 FROM cliente WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); try (ResultSet rs = stmt.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao consultar cliente.", e); }
    }

    public boolean atualizar(int id, Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, contato=?, cpf=?, endereco=?, ponto_referencia=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencher(stmt, cliente); stmt.setInt(6, id); return stmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao atualizar cliente.", e); }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); return stmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao excluir cliente. Se houver pedidos vinculados, o banco pode impedir a exclusão.", e); }
    }

    private void preencher(PreparedStatement stmt, Cliente c) throws SQLException {
        stmt.setString(1, c.getNome().trim()); stmt.setString(2, c.getContato()); stmt.setString(3, c.getCpf()); stmt.setString(4, c.getEndereco()); stmt.setString(5, c.getPontoReferencia());
    }
}
