package com.chris.spring.data.mongodb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		String ecode = pe.encode("1234");
		System.out.println(ecode);
		boolean matches = pe.matches("1234", ecode);
		System.out.println(matches);

		System.out.println();
		String ecode2 = pe.encode("1234");
		System.out.println(ecode2);
		boolean matches2 = pe.matches("1234", ecode2);
		System.out.println(matches2);
	}

}
