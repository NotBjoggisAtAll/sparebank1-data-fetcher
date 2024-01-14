package no.jonasandersen.sparebank1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTask
@EnableConfigurationProperties(ApplicationProperties.class)
public class Sparebank1Application {

  @Bean
  public ApplicationRunner applicationRunner(AccessTokenGenerator accessTokenGenerator) {
    return new HelloWorldApplicationRunner(accessTokenGenerator);
  }

  public static void main(String[] args) {
    SpringApplication.run(Sparebank1Application.class, args);
  }


  public static class HelloWorldApplicationRunner implements ApplicationRunner {

    private final AccessTokenGenerator accessTokenGenerator;

    public HelloWorldApplicationRunner(AccessTokenGenerator accessTokenGenerator) {
      this.accessTokenGenerator = accessTokenGenerator;
    }

    @Override
    public void run(ApplicationArguments args) {

      String accessToken = accessTokenGenerator.getAccessToken();
      String url = "https://api.sparebank1.no/personal/banking/accounts";

      RestTemplate restTemplate = new RestTemplateBuilder()
          .rootUri(url)
          .defaultHeader("Authorization", "Bearer " + accessToken)
          .build();

      String forObject = restTemplate.getForObject("/", String.class);

      System.out.println("forObject = " + forObject);
    }
  }
}
