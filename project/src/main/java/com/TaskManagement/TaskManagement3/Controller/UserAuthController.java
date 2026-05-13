package com.TaskManagement.TaskManagement3.Controller;

// import org.apache.catalina.connector.Response;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.TaskManagement3.DTO.LoginRequestDTO;
import com.TaskManagement.TaskManagement3.DTO.RegisterRequestDTO;
import com.TaskManagement.TaskManagement3.Service.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController  // HTTP requests and return responses in a RESTful manner
@RequestMapping
@RequiredArgsConstructor
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO register){
        String response = userAuthService.register(register);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<@Nullable Object> login(@RequestBody LoginRequestDTO login){
        return ResponseEntity.ok(userAuthService.login(login));
    }
    @PostMapping("/logOut")
	public ResponseEntity<String>logOut(HttpServletRequest request){
		return ResponseEntity.ok(userAuthService.logOut(request));
	}
	@PostMapping("/forgot-password")
	public ResponseEntity<String>forgotPasswrd(@RequestParam String userOfficialEmail){
		userAuthService.forgotPassword(userOfficialEmail);
		return ResponseEntity.ok("Reset password email sent over on your Email");
	}
	@PostMapping("/reset-password")
	public ResponseEntity<String>resetPassword(@RequestParam String token,@RequestParam String newPassword){
		userAuthService.resetPassword(token, newPassword);
		return ResponseEntity.ok("Password Reset successfully");
	}
    
}
