package br.com.bolosystem.service;

import br.com.bolosystem.dao.ProdutoDAO;
import br.com.bolosystem.model.Produto;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public List<Produto> listar() {
        return produtoDAO.listar();
    }

    public Produto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do produto deve ser válido."
            );
        }

        return produtoDAO.buscarPorId(id);
    }

    public void cadastrar(Produto produto) {
        validar(produto);
        produtoDAO.inserir(produto);
    }

    public boolean atualizar(int id, Produto produto) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do produto deve ser válido."
            );
        }

        validar(produto);
        return produtoDAO.atualizar(id, produto);
    }

    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do produto deve ser válido."
            );
        }

        return produtoDAO.excluir(id);
    }

    public void validar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException(
                "O produto não pode ser nulo."
            );
        }

        if (produto.getNome() == null
                || produto.getNome().isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto é obrigatório."
            );
        }

        if (produto.getPreco() < 0
                || !Double.isFinite(produto.getPreco())) {
            throw new IllegalArgumentException(
                "O preço deve ser válido e não negativo."
            );
        }
    }
}