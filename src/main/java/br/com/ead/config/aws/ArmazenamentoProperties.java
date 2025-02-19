package br.com.ead.config.aws;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Validated
@Configuration
@ConfigurationProperties("aw.armazenamento")
public class ArmazenamentoProperties {

    @Valid
    private S3 s3 = new S3();

    @Data
    public class S3 {
        @NotBlank
        private String keyId;
        @NotBlank
        private String keySecret;
        @NotBlank
        private String bucket;
        @NotBlank
        private String region;
    }
}
