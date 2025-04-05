package br.com.ead.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static br.com.ead.EadApplication.EAD_PACKAGE;

@Configuration
@EnableJpaRepositories(basePackages = EAD_PACKAGE)
@EntityScan(basePackages = EAD_PACKAGE)
public class JpaConfiguration {
}
