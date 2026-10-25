
package br.com.bolosystemweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "br.com.bolosystemweb",
        "br.com.bolosystem"
    }
)
public class BolosystemwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BolosystemwebApplication.class, args);
    }
}
