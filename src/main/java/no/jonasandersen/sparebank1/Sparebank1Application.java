package no.jonasandersen.sparebank1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class Sparebank1Application {


  @Bean
  public ApplicationRunner applicationRunner() {
    return new HelloWorldApplicationRunner();
  }

  public static void main(String[] args) {
    SpringApplication.run(Sparebank1Application.class, args);
  }


  public static class HelloWorldApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
      System.out.println("Hello, World!");

    }

  }

}
