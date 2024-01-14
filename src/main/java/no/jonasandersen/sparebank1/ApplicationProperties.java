package no.jonasandersen.sparebank1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sparebank1")
@Valid
public record ApplicationProperties(@NotNull String clientId, @NotNull String encryptionKey) {

}
