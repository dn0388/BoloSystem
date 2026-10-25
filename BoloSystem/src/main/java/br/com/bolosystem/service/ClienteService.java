
package br.com.bolosystem.service;

import br.com.bolosystem.dao.ClienteDAO;
import br.com.bolosystem.model.Cliente;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public List<Cliente> listar() {
        return clienteDAO.listar();
    }

    public int buscarIdPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Informe o nome do cliente."
            );
        }

        return clienteDAO.buscarIdPorNome(nome.trim());
    }

    public void cadastrar(Cliente cliente) {
        validar(cliente);
        clienteDAO.cadastrar(cliente);
    }

    public boolean atualizar(int id, Cliente cliente) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do cliente deve ser válido."
            );
        }

        validar(cliente);
        return clienteDAO.atualizar(id, cliente);
    }

    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do cliente deve ser válido."
            );
        }

        return clienteDAO.excluir(id);
    }

    public void validar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                "O cliente não pode ser nulo."
            );
        }

        if (cliente.getNome() == null
                || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException(
                "O nome do cliente é obrigatório."
            );
        }
    }
}
