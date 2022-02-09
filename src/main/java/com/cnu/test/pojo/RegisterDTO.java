package com.cnu.test.pojo;

import java.io.Serializable;

public class RegisterDTO implements Serializable {
	 private  long id;
	    private String email;
	    private String address;
	    private int phonenumber;
	    
		public RegisterDTO() {
		}
		public RegisterDTO(long id, String email, String address, int phonenumber) {
			super();
			this.id = id;
			this.email = email;
			this.address = address;
			this.phonenumber = phonenumber;
		}
		public RegisterDTO(String email, String address, int phonenumber) {
			super();
			this.email = email;
			this.address = address;
			this.phonenumber = phonenumber;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public int getPhonenumber() {
			return phonenumber;
		}
		public void setPhonenumber(int phonenumber) {
			this.phonenumber = phonenumber;
		}
		@Override
		public String toString() {
			return "RegisterDTO [id=" + id + ", email=" + email + ", address=" + address + ", phonenumber="
					+ phonenumber + "]";
		}
	    
	    
	    
}
