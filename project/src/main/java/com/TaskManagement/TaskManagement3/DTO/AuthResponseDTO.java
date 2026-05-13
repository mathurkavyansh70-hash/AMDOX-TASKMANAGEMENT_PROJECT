package com.TaskManagement.TaskManagement3.DTO;

public class AuthResponseDTO {
    private String token;
    private String messages;

    public AuthResponseDTO(String token,String messages) {
		this.token=token;
		this.messages=messages;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}

}
