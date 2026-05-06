package com.vaultgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VaultGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaultGateApplication.class, args);
	}

}
