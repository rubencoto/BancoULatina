package cr.ac.ulatina.banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AplicacionBancoULatina {
    public static void main(String[] args) {
        SpringApplication.run(AplicacionBancoULatina.class, args);
    }
}
