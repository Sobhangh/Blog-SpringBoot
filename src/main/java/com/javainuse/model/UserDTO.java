package com.javainuse.model;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

public class UserDTO {
	@NotNull
	private String username;
	@NotNull
	private String password;

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
}