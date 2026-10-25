
package Conexao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionFactory {

    private static final Properties CONFIG = carregarConfiguracao();

    private static final String URL = configuracao(
            "DB_URL",
            "spring.datasource.url",
            "jdbc:mysql://localhost:3306/bolosystem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
    );

    private static final String USER = configuracao(
            "DB_USER",
            "spring.datasource.username",
            "root"
    );

    private static final String PASSWORD = configuracao(
            "DB_PASSWORD",
            "spring.datasource.password",
            "Dnmrochajcc1234@"
    );

    private ConnectionFactory() {
    }

    private static Properties carregarConfiguracao() {
        Properties properties = new Properties();

        // Carrega a configuração principal.
        carregarArquivo(properties, "/application.properties");

        // Permite que as configurações locais sobrescrevam as principais.
        carregarArquivo(properties, "/application-local.properties");

        return properties;
    }

    private static void carregarArquivo(
            Properties properties, String arquivo) {

        try (InputStream input =
                     ConnectionFactory.class.getResourceAsStream(arquivo)) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Erro ao ler o arquivo " + arquivo, e);
        }
    }

    private static String configuracao(
            String variavelAmbiente,
            String propriedade,
            String padrao) {

        String ambiente = System.getenv(variavelAmbiente);

        if (ambiente != null && !ambiente.isBlank()) {
            return ambiente;
        }

        String sistema = System.getProperty(variavelAmbiente);

        if (sistema != null && !sistema.isBlank()) {
            return sistema;
        }

        String valor = CONFIG.getProperty(propriedade);

        if (valor != null && !valor.isBlank()
                && !valor.startsWith("COLOQUE_")) {
            return valor.trim();
        }

        return padrao;
    }

    public static Connection getConnection() {

        if (PASSWORD.isBlank()) {
            throw new IllegalStateException(
                    "Senha do MySQL não configurada. "
                    + "Confira spring.datasource.password "
                    + "em application.properties."
            );
        }

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Falha na conexão com o MySQL. "
                    + "Confira se o servidor está ligado, "
                    + "se o banco bolosystem existe "
                    + "e se as credenciais estão corretas.",
                    e
            );
        }
    }
}
