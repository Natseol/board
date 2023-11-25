package com.java4.board.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.board.comment.dao.CommentDAO;
import com.java4.board.comment.domain.Comment;

@Service
public class CommentService {	
	@Autowired
	CommentDAO commentDAO;
	
	public void add(Comment comment) {
		commentDAO.add(comment);
	}
	
	public List<Comment> getComments(int boardId) {
		return commentDAO.getComments(boardId);
	}
}
