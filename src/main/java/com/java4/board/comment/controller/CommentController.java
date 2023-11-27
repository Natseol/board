package com.java4.board.comment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java4.board.comment.domain.Comment;
import com.java4.board.comment.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@GetMapping
	@ResponseBody
	public List<Comment> getComments(@RequestParam Map<String, String> data) {		
		return commentService.getComments(Integer.parseInt(data.get("boardId")), 0);
	}
	
	@PostMapping("add")
	public String addComment(@RequestParam Map<String, String> data) {
		Comment comment = new Comment(data.get("content"),
				Integer.parseInt(data.get("user_id")), Integer.parseInt(data.get("board_id")));
		comment.setCommentId(Integer.parseInt(data.get("parent_id")));
		commentService.add(comment);

		return "redirect:/board/"+data.get("board_id");
	}
}
