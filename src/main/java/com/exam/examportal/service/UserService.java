package com.exam.examportal.service;

import java.util.Set;

import com.exam.examportal.model.User;
import com.exam.examportal.model.UserRole;

public interface UserService {

	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	public User getUser(String username);
	
	public void deleteUser(Long userid);
}
