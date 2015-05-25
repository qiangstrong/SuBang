package com.subang.bean;

import com.subang.domain.Admin;

public class AdminBean extends Admin {
	
	private String passwordCopy;

	public AdminBean() {
	}

	public AdminBean(Integer id, String username, String password, String passwordCopy) {
		super(id, username, password);
		this.passwordCopy = passwordCopy;
	}

	public AdminBean(Admin admin){
		super(admin.getId(),admin.getPassword(),admin.getUsername());
		this.passwordCopy=admin.getPassword();
	}
	
	public String getPasswordCopy() {
		return passwordCopy;
	}

	public void setPasswordCopy(String passwordCopy) {
		this.passwordCopy = passwordCopy;
	}
	
	public boolean validate(){
		if (password.equals(passwordCopy)) {
			return true;
		}
		return false;
	}
}
