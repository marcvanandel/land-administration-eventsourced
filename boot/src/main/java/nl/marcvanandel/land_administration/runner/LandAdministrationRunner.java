package nl.marcvanandel.land_administration.runner;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LandAdministrationRunner {

    public static void main(final String[] args) {
        SpringApplication landAdministrationRunner = new SpringApplication(LandAdministrationRunner.class);
        landAdministrationRunner.setBannerMode(Banner.Mode.OFF);
        landAdministrationRunner.run(args);
    }

}
