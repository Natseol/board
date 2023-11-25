package com.java4.board.comment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java4.board.comment.domain.Comment;
import com.java4.board.comment.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@PostMapping("/comment/add")
	public String addComment(@RequestParam Map<String, String> data, Model model, HttpServletRequest request) {
		int boardId = Integer.parseInt(data.get("board-id"));
		String userId = data.get("user-id");
		String commentContent = data.get("content");
		
		System.out.println(boardId);		
		System.out.println(userId);
		System.out.println(commentContent);
		
		if (data.get("parent-comment-id")!=null) {
			int parentCommentId = Integer.parseInt(data.get("parent-comment-id"));
			System.out.println(parentCommentId);
			commentService.add(new Comment(boardId,parentCommentId,userId,commentContent));
		} else {
			commentService.add(new Comment(boardId,0,userId,commentContent));			
		}

		return "redirect:"+data.get("currentPageUrl");
	}
}
