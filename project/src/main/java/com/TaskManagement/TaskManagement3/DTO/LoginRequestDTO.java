package com.TaskManagement.TaskManagement3.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDTO {
	public String userOfficialEmail;
	public String password;

    }
