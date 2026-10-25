
package br.com.bolosystem.service;

import br.com.bolosystem.dao.UsuarioDAO;

public class AutenticacaoService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String login, String senha) {
        if (login == null || login.isBlank()) {
            return false;
        }

        if (senha == null || senha.isBlank()) {
            return false;
        }

        return usuarioDAO.validarLogin(login, senha);
    }
}
