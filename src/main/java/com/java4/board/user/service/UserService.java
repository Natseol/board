package com.java4.board.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.board.user.dao.UserDAO;
import com.java4.board.user.domain.User;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	
	public void add(User user) {
		userDAO.add(user);
		
	}
	
	public User get(String userId) {		
		return userDAO.get(userId);
	}	

}
