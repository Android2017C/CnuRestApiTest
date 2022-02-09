package com.cnu.test.pojo;

import java.io.Serializable;

public class UserDTO implements Serializable{
	 private  long id;
	    private String username;
	    private String password;
	    private boolean loggedIn;
	    
	    private RegisterDTO registerDTO;
		public UserDTO() {
		}
		public UserDTO(long id, String username, String password, boolean loggedIn, RegisterDTO registerDTO) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.loggedIn = loggedIn;
			this.registerDTO = registerDTO;
		}
		public UserDTO(String username, String password, boolean loggedIn, RegisterDTO registerDTO) {
			super();
			this.username = username;
			this.password = password;
			this.loggedIn = loggedIn;
			this.registerDTO = registerDTO;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public boolean isLoggedIn() {
			return loggedIn;
		}
		public void setLoggedIn(boolean loggedIn) {
			this.loggedIn = loggedIn;
		}
		public RegisterDTO getRegisterDTO() {
			return registerDTO;
		}
		public void setRegisterDTO(RegisterDTO registerDTO) {
			this.registerDTO = registerDTO;
		}
		@Override
		public String toString() {
			return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", loggedIn=" + loggedIn
					+ ", registerDTO=" + registerDTO + "]";
		}
		
	    
	    
}
