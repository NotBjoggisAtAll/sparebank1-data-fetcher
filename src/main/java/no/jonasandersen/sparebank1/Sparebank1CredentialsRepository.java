package no.jonasandersen.sparebank1;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Sparebank1CredentialsRepository extends
    JpaRepository<Sparebank1Credentials, Long> {


  @Query("select s from Sparebank1Credentials s where s.clientId = ?1")
  Sparebank1Credentials findByClientId(String clientId);
}