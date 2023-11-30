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
import com.java4.board.comment.domain.ResponseComment;
import com.java4.board.comment.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@GetMapping
	@ResponseBody
	public ResponseComment getComments(@RequestParam Map<String, String> data) {
		int boardId = Integer.parseInt(data.get("boardId").trim());
		ResponseComment res = new ResponseComment(
				commentService.getComments(boardId,
						Integer.parseInt(data.get("start"))),
				commentService.getCount(boardId) <= Integer.parseInt(data.get("start"))+ 5);
		return res;
	}
	
	@PostMapping("add")
	public String addComment(@RequestParam Map<String, String> data) {
		Comment comment = new Comment(data.get("content"),
				Integer.parseInt(data.get("user_id")), Integer.parseInt(data.get("board_id")));
		if (data.get("parent_id") != null) {
			comment.setCommentId(Integer.parseInt(data.get("parent_id")));			
		}		
		commentService.add(comment);

		return "redirect:/board/"+data.get("board_id");
	}
}
