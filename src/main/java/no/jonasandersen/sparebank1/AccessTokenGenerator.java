package no.jonasandersen.sparebank1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AccessTokenGenerator {

  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String SPAREBANK1_AUTH_URL = "https://api-auth.sparebank1.no/oauth/token";
  private final Logger logger = LoggerFactory.getLogger(AccessTokenGenerator.class);

  private final ApplicationProperties properties;

  private final Sparebank1CredentialsRepository repository;
  private final RestTemplate restTemplate = new RestTemplate();
  private final HttpHeaders headers = new HttpHeaders();

  public AccessTokenGenerator(ApplicationProperties properties,
      Sparebank1CredentialsRepository repository) {
    this.properties = properties;
    this.repository = repository;

    headers.add("Content-Type", "application/x-www-form-urlencoded");
  }

  @Transactional(transactionManager = "transactionManager")
  public String getAccessToken() {
    logger.debug("Retrieving access token");
    final Sparebank1Credentials credentials = repository.findByClientId(properties.clientId());

    MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap<>();
    bodyPair.add("client_id", credentials.getClientId());
    bodyPair.add("client_secret", credentials.getClientSecret());
    bodyPair.add("grant_type", REFRESH_TOKEN);
    bodyPair.add("refresh_token", credentials.getRefreshToken());

    ResponseEntity<Response> exchange = restTemplate.exchange(SPAREBANK1_AUTH_URL, HttpMethod.POST,
        new HttpEntity<>(bodyPair, headers), Response.class);

    final Response response = exchange.getBody();

    if (response == null) {
      throw new IllegalStateException("Response was null");
    }

    save(bodyPair, response);
    return response.access_token();
  }

  private void save(MultiValueMap<String, String> body, Response response) {
    repository.deleteAll();

    repository.save(new Sparebank1Credentials(
        body.get("client_id").getFirst(),
        body.get("client_secret").getFirst(),
        response.refresh_token()
    ));
  }

  record Response(String access_token, String token_type, Long expires_in, String state,
                  String refresh_token) {

  }
}
