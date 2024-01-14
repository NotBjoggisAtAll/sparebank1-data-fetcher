package no.jonasandersen.sparebank1;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sparebank1Credentials {

  @Id
  @GeneratedValue
  private Long id;

  private String clientId;

  @Convert(converter = AttributeEncryptor.class)
  private String clientSecret;

  @Convert(converter = AttributeEncryptor.class)
  private String refreshToken;

  public Sparebank1Credentials() {
  }

  public Sparebank1Credentials(String clientId, String clientSecret, String refreshToken) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.refreshToken = refreshToken;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public String toString() {
    return "Sparebank1Credentials{" +
        "id=" + id +
        ", clientId='" + clientId + '\'' +
        ", clientSecret='" + clientSecret + '\'' +
        ", refreshToken='" + refreshToken + '\'' +
        '}';
  }
}
