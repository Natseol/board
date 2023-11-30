package com.java4.board.user.service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.board.user.dao.UserDAO;
import com.java4.board.user.dao.UserDAOMysql;
import com.java4.board.user.domain.User;

@Service
public class UserService {
	@Autowired
	UserDAOMysql userDAO;
	
	public void add(User user) {
		user.setPassword(cryptoPassword(user.getPassword()));
		userDAO.add(user);
	}
	
	public User login(User user) {
		User tempUser = userDAO.get(user.getUserId());
		if(tempUser != null && tempUser.getPassword().equals(cryptoPassword(user.getPassword()))) {
			return tempUser;
		}
		return null;
	}
	
	public User get(String userId) {		
		return userDAO.get(userId);
	}
	
	private String cryptoPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] sha256Hash = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : sha256Hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
