package br.com.ead;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import static br.com.ead.EadApplication.EAD_PACKAGE;

@ComponentScan(EAD_PACKAGE)
@EnableAutoConfiguration
@SpringBootApplication
public class EadApplication {

    public static final String EAD_PACKAGE = "br.com.ead";
    public static final String PERFIL_DIFERENTE_DE_TESTE_INTEGRACAO = "!it";

    public static void main(String[] args) {
        var applicationBuilder = new SpringApplicationBuilder(EadApplication.class)
                .profiles("default")
                .properties("management.info.git.mode:full")
                .properties("server.port:8080");
        applicationBuilder.run();
    }

}
