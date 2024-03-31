package br.com.fms.authentication;

import br.com.fms.authentication.controller.AuthController;
import br.com.fms.authentication.controller.TestController;
import br.com.fms.authentication.model.ERole;
import br.com.fms.authentication.model.Role;
import br.com.fms.authentication.payload.request.LoginRequest;
import br.com.fms.authentication.payload.request.SignupRequest;
import br.com.fms.authentication.payload.response.JwtResponse;
import br.com.fms.authentication.payload.response.MessageResponse;
import br.com.fms.authentication.repository.RoleRepository;
import br.com.fms.authentication.repository.UserRepository;
import br.com.fms.authentication.security.jwt.JwtUtils;
import br.com.fms.authentication.service.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import br.com.fms.authentication.model.User;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AuthenticationApplicationTests {
	@Autowired
	private AuthController authController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Test
	public void testRegisterUser_Success() {
		// Prepare test data
		String username = "testuser2";
		String email = "testuser2@example.com";
		String password = "testpassword";

		// Create a new user request
		SignupRequest request = new SignupRequest();
		request.setUsername(username);
		request.setEmail(email);
		request.setPassword(password);

		// Call the controller method
		ResponseEntity<?> response = authController.registerUser(request);

		// Verify response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("User registered successfully!", ((MessageResponse) response.getBody()).getMessage());
	}
	@Test
	public void testAuthenticateUser_Success() {
		// Prepare test data
		String username = "testuser10";
		String password = "testpassword";
		String email = "testuser10@example.com";

		// Create a new user
		User user = new User(username, email, passwordEncoder.encode(password));
		userRepository.save(user);

		// Create a LoginRequest object
		LoginRequest request = new LoginRequest();
		request.setUsername(username);
		request.setPassword(password);

		// Call the controller method
		ResponseEntity<?> response = authController.authenticateUser(request);

		// Verify response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		// Additional assertions based on response body
	}

}


