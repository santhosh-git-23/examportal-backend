package com.exam.examportal.service.Impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examportal.Repo.RoleRepository;
import com.exam.examportal.Repo.UserRepository;
import com.exam.examportal.helper.UserFoundException;
import com.exam.examportal.helper.UserNotFoundException;
import com.exam.examportal.model.User;
import com.exam.examportal.model.UserRole;
import com.exam.examportal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception{
		
		User local = userRepository.findByUsername(user.getUsername());
		if(local!=null) {
			System.out.println("User already exists");
			throw new UserFoundException();
		}else {
			for(UserRole ur: userRoles)
				roleRepository.save(ur.getRole());
			
			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);
			
		}
		return local;
		
	}

	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
		
	}

	@Override
	public void deleteUser(Long userid) {
		this.userRepository.deleteById(userid);
		
	}

}
