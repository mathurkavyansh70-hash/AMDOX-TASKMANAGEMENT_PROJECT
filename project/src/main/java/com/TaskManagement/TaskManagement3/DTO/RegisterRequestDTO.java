package com.TaskManagement.TaskManagement3.DTO;
import com.TaskManagement.TaskManagement3.Enum.*;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
    private String username;
    private String userOfficialEmail;
    private String password;
    private Role role;
    
}
