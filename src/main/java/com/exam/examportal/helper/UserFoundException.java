package com.exam.examportal.helper;

public class UserFoundException extends Exception{
	public UserFoundException() {
		super("User with this username already exists");
	}
	
	public UserFoundException(String msg) {
		super(msg);
	}

}
