package com.book.store.model.payloads;

import javax.persistence.Persistence;
import javax.validation.GroupSequence;
import javax.xml.crypto.Data;

import org.hibernate.type.Type;

import com.book.store.model.validation.PasswordMatch;

@GroupSequence({
	Type.class, 
	Data.class, 
	Persistence.class, 
	ConfirmPasswordCommand.class})
@PasswordMatch(
		groups = Data.class, 
		first = "password", 
		second = "confirmPassword", 
		message = "The New Password and Confirm New Password fields must match.")
public class ConfirmPasswordCommand {
	
	private String password;
	
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
