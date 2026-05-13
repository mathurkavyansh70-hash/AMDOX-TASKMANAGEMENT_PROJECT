package com.TaskManagement.TaskManagement3.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.TaskManagement3.DTO.UserProfileUpdateDTO;
import com.TaskManagement.TaskManagement3.Entity.UserProfileUpdate;
import com.TaskManagement.TaskManagement3.Repository.UserProfileUpdateRepository;

@Service
public class UserProfileUpdateService {

    @Autowired
    private UserProfileUpdateRepository userProfileUpdateRepo;

    public UserProfileUpdateDTO updateUserProfile(String userOfficialEmail, UserProfileUpdateDTO prfileUpdate) {

        UserProfileUpdate userProfile = userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userProfile.setUserName(prfileUpdate.getUserName());
        userProfile.setDepartment(prfileUpdate.getDeartment());
        userProfile.setDesignation(prfileUpdate.getDesignation());
        userProfile.setOrganizationName(prfileUpdate.getOrganizationName());
        userProfile.setActive(prfileUpdate.isActive());

        userProfileUpdateRepo.save(userProfile);

        return toDTO(userProfile);

    }

    public List<UserProfileUpdateDTO> getAllProfile() {
        return userProfileUpdateRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserProfileUpdateDTO getUserByEmail(String userOfficialEmail) {
        UserProfileUpdate userProfile = userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return toDTO(userProfile);
    }

    private UserProfileUpdateDTO toDTO(UserProfileUpdate userProfileUpdate) {
		
		UserProfileUpdateDTO dto= new UserProfileUpdateDTO();
		
		dto.setId(userProfileUpdate.getId());
		dto.setUserName(userProfileUpdate.getUserName());
		dto.setUserOfficialEmail(userProfileUpdate.getUserOfficialEmail());
		dto.setDeartment(userProfileUpdate.getDepartment());
		dto.setDesignation(userProfileUpdate.getDesignation());
		dto.setOrganizationName(userProfileUpdate.getOrganizationName());
		dto.setActive(userProfileUpdate.isActive());
		
		return dto;
		
		
	}
}