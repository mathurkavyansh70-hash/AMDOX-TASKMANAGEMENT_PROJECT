package com.TaskManagement.TaskManagement3.Service;

import com.TaskManagement.TaskManagement3.Security.TokenBlockListService;
import java.util.Date;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManagement.TaskManagement3.DTO.*;
import com.TaskManagement.TaskManagement3.Entity.UserAuth;
import com.TaskManagement.TaskManagement3.Repository.UserAuthRepository;
import com.TaskManagement.TaskManagement3.Security.EmailService;
import com.TaskManagement.TaskManagement3.Security.JWTAuthenticationFilter;
import com.TaskManagement.TaskManagement3.Security.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private EmailService emailService;

    @Autowired
	private TokenBlockListService tokenBlockService;

    public String register(RegisterRequestDTO register){
        if(userRepo.findByUserOfficialEmail(register.getUserOfficialEmail()).isPresent()){
            throw new RuntimeException("User Already Exist");
        }
        UserAuth user =new UserAuth();
        user.setUserName(register.getUsername());
        user.setUserOfficialEmail(register.getUserOfficialEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());
        userRepo.save(user);
        return "User registered successfully";
    }

    public AuthResponseDTO login(LoginRequestDTO login){
        UserAuth user=userRepo.findByUserOfficialEmail(login.getUserOfficialEmail())
        .orElseThrow(()->new RuntimeException("User Not Found"));
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }else{
            String token=jwtUtil.generateToken(user);
            return new AuthResponseDTO(token,"Login Successful");
        }
    }

    public void forgotPassword(String userOfficialEmail) {
        UserAuth user=userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()->new RuntimeException("User Not Found"));
        String token=UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpire(new Date(System.currentTimeMillis()+1000*60*60)); // 1 hour expiration
        userRepo.save(user);
        emailService.sendResetPasswordEmail(userOfficialEmail,token);
    }

    public void resetPassword(String token,String newPassword) {
		
		UserAuth user = userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
		
		if(user.getResetTokenExpire().before(new Date())) {
			throw new RuntimeException("Token expired");
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetToken(null);
		user.setResetTokenExpire(null);
		
		userRepo.save(user);
	}
	
	public String logOut(HttpServletRequest request) {
		String header= request.getHeader("Authorization");
		String token = jwtUtil.extractToken(header);
		if(token !=null) {
			tokenBlockService.blockListToken(token);
		}
		return "LoggedOut successfully";
	}

}
