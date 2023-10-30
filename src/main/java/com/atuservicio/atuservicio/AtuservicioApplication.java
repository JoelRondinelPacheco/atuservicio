package com.atuservicio.atuservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AtuservicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtuservicioApplication.class, args);


		UUID uud1 = UUID.randomUUID();
		UUID uud2 = UUID.randomUUID();
		UUID uud3 = UUID.randomUUID();
		UUID uud4 = UUID.randomUUID();
		UUID uud5 = UUID.randomUUID();
		UUID uud6 = UUID.randomUUID();
		UUID uud7 = UUID.randomUUID();
		UUID uud8 = UUID.randomUUID();
		System.out.println(uud1);
		System.out.println(uud2);
		System.out.println(uud3);
		System.out.println(uud4);
		System.out.println(uud5);
		System.out.println(uud6);
		System.out.println(uud7);
		System.out.println(uud8);
	}

}
