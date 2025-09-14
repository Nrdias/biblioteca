package com.projarq.biblioteca;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();
			
			dotenv.entries().forEach(entry -> 
				System.setProperty(entry.getKey(), entry.getValue())
			);
			
			System.out.println("✓ .env file loaded successfully!");
		} catch (Exception e) {
			System.out.println("Warning: Could not load .env file: " + e.getMessage());
		}
		
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
