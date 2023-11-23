package com.java4.board.user.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java4.board.user.domain.User;
import com.java4.board.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("regist")
	public String registPage(Model model) {
		model.addAttribute("title", "회원가입");
		model.addAttribute("path", "/user/regist"); 
		model.addAttribute("content", "registFragment"); 
		model.addAttribute("contentHead", "registFragmentHead");
		return "/basic/layout";
	}
	
	@PostMapping("regist")
	public String registUser(@RequestParam Map<String, String> data, Model model) {
		System.out.println("regist post");
		try {
			User user = new User(data.get("user-id"),
					data.get("password"),
					data.get("name"),
					data.get("phone"),
					data.get("email"));
			if (data.get("address")!=null) user.setAddress(data.get("address"));
			if (data.get("git-address")!=null) user.setGitAddress(data.get("git-address"));
			if (data.get("gender")!=null) user.setGender(Integer.parseInt(data.get("gender")));
			if (data.get("birth")!="") user.setBirth(Date.valueOf(data.get("birth")));
			userService.add(user);
			System.out.println("가입성공");
			return "redirect:/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			model.addAttribute("requestError","회원가입 실패");
			return "redirect:/user/regist";
		}
	}
	
	@GetMapping("login")
	public String loginPage(Model model, HttpSession session) {
		model.addAttribute("title", "로그인");
		model.addAttribute("path", "/user/login"); 
		model.addAttribute("content", "loginFragment"); 
		model.addAttribute("contentHead", "loginFragmentHead");
		return "/basic/layout";
	}
	
	@PostMapping("login")
	public String loginUser(@RequestParam Map<String, String> data, HttpSession session,
			Model model, RedirectAttributes redirectAttribute) {
		try {
			User tempUser = new User();
			tempUser.setUserId(data.get("user-id"));
			tempUser.setPassword(data.get("password"));
			tempUser = userService.login(tempUser);
			if (tempUser != null) {
				session.setAttribute("userId", tempUser.getUserId());
				session.setAttribute("userName", tempUser.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			redirectAttribute.addFlashAttribute("requestError","로그인 실패");
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String loginOut(Model model, HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		return "redirect:/";
	}
}
