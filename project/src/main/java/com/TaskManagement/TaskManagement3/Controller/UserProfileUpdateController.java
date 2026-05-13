package com.TaskManagement.TaskManagement3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.TaskManagement3.DTO.UserProfileUpdateDTO;
import com.TaskManagement.TaskManagement3.Service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user_profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {
	
	@Autowired
	private UserProfileUpdateService userProfileService;
	
	
	@PutMapping("/updateUseeProfile/{email}")
	public ResponseEntity<UserProfileUpdateDTO>updateUserProfile(@RequestBody UserProfileUpdateDTO userProfile,@PathVariable String userOfficialEmail){
		return ResponseEntity.ok(userProfileService.updateUserProfile(userOfficialEmail, userProfile));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserProfileUpdateDTO>>getAllUser(){
		return ResponseEntity.ok(userProfileService.getAllProfile());
	}
	@GetMapping("/{email}")
	public ResponseEntity<UserProfileUpdateDTO>getUserByEmail(@PathVariable String userOfficialEmail){
		return ResponseEntity.ok(userProfileService.getUserByEmail(userOfficialEmail));
	}
	

}

