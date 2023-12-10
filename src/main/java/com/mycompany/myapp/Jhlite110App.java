package com.mycompany.myapp;

import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing logs")
public class Jhlite110App {

  private static final Logger log = LoggerFactory.getLogger(Jhlite110App.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(Jhlite110App.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
