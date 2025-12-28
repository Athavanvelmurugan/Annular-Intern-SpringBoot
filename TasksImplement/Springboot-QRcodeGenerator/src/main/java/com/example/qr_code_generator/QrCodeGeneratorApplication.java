package com.example.qr_code_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class QrCodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeGeneratorApplication.class, args);
	}

}
