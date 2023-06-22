package com.exam.examportal.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examportal.config.JwtUtils;
import com.exam.examportal.helper.UserFoundException;
import com.exam.examportal.model.JwtRequest;
import com.exam.examportal.model.JwtResponse;
import com.exam.examportal.model.User;
import com.exam.examportal.service.Impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin(origins = "http://16.171.79.89/")
public class AuthenticateController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		try {
			authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
		}catch(UserFoundException e) {
			System.out.println("username not found");
			throw new Exception("User not found");
		}
		
		UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		}catch(DisabledException e) {
			throw new Exception("user disabled" + e.getMessage());
		}catch(BadCredentialsException e) {
			throw new Exception("invalid exception" + e.getMessage());
		}
	}
	
	@GetMapping("/current-user")
	public User currentUser(Principal principal) {
		return ((User)this.userDetailService.loadUserByUsername(principal.getName()));
	}

}
