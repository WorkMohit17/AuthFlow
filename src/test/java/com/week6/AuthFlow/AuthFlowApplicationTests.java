package com.week6.AuthFlow;

import com.week6.AuthFlow.dtos.LoginDTO;
import com.week6.AuthFlow.dtos.LoginResponseDTO;
import com.week6.AuthFlow.dtos.SignUpDTO;
import com.week6.AuthFlow.dtos.SignUpResponseDTO;
import com.week6.AuthFlow.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthFlowApplicationTests {

	@Autowired
	private AuthService authService;

	@Test
	void signUpAndLogin(){
		signup();
		login();
	}

	void signup() {
		SignUpDTO dto = new SignUpDTO();
		dto.setName("Mohit Bansal");
		dto.setEmail("mohitbansal@gmail.com");
		dto.setPassword("password");

		SignUpResponseDTO signup = authService.signUp(dto);
		System.out.println(signup.toString());

	}

	void login() {
		LoginDTO dto = new LoginDTO();
		dto.setEmail("mohitbansal@gmail.com");
		dto.setPassword("password");

		LoginResponseDTO login = authService.login(dto);
		System.out.println(login.toString());

	}

}
