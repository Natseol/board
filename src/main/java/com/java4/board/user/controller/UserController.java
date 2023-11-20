package com.java4.board.user.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java4.board.user.domain.User;
import com.java4.board.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/regist")
	public String registPage(Model model) {
		model.addAttribute("title", "회원가입");
		model.addAttribute("path", "/board/regist"); 
		model.addAttribute("content", "registFragment"); 
		model.addAttribute("contentHead", "registFragmentHead");
		return "/basic/layout";
	}
	
	@PostMapping("/regist")
	public String registUser(@RequestParam Map<String, String> data) {
		User user = new User(data.get("user-id"),
				data.get("password"),
				data.get("name"),
				data.get("phone"),
				data.get("email"));
		if (data.get("address")!=null) user.setAddress(data.get("address"));
		if (data.get("git-address")!=null) user.setGitAddress(data.get("git-address"));
		if (data.get("gender")!=null) user.setGender(Integer.parseInt(data.get("gender"))==1);
		if (data.get("birth")!="") user.setBirth(Date.valueOf(data.get("birth")));
		userService.add(user);

		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("title", "로그인");
		model.addAttribute("path", "/board/login"); 
		model.addAttribute("content", "loginFragment"); 
		model.addAttribute("contentHead", "loginFragmentHead");
		return "/basic/layout";
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam Map<String, String> data, HttpSession session) {
		User user = userService.get(data.get("user-id"));
		if(user.getPassword().equals(data.get("password"))) {
			session.setAttribute("userId", user.getUserId());
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String loginPage(Model model, HttpSession session) {
		session.setAttribute("userId", "");
		return "redirect:/";
	}
}
