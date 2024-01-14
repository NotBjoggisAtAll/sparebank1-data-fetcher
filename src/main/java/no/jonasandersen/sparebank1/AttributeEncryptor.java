package no.jonasandersen.sparebank1;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
@Converter
public class AttributeEncryptor implements AttributeConverter<String, String> {

  private static final String AES = "AES";

  private final Key key;
  private final Cipher cipher;

  public AttributeEncryptor(ApplicationProperties properties) throws Exception {
    key = new SecretKeySpec(properties.encryptionKey().getBytes(), AES);
    cipher = Cipher.getInstance(AES);
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
    } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      throw new IllegalStateException(e);
    }
  }
}