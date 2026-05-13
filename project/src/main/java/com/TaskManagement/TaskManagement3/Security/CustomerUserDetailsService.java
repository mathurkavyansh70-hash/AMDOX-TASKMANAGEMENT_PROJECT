package com.TaskManagement.TaskManagement3.Security;

// import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

import com.TaskManagement.TaskManagement3.Enum.Permissions;
import com.TaskManagement.TaskManagement3.Entity.UserAuth;
import com.TaskManagement.TaskManagement3.Repository.UserAuthRepository;

public abstract class CustomerUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private UserAuthRepository userRepo;
	
	public UserDetails loadUserEmail(String userOfficialEmail)throws Exception {
		
		UserAuth user= userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()->new RuntimeException("user not found"));
		
		Set<Permissions> perm = RoleBasedPermission.getRolePermission().get(user.getRole());
		
		return new org.springframework.security.core.userdetails.User(user.getUserOfficialEmail(), user.getPassword(), null);
    }
}
