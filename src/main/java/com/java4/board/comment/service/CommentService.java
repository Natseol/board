package com.java4.board.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.board.comment.dao.CommentDAO;
import com.java4.board.comment.dao.CommentDAOMysql;
import com.java4.board.comment.domain.Comment;

@Service
public class CommentService {	
	@Autowired
	CommentDAOMysql commentDAO;
	
	public void add(Comment comment) {
		commentDAO.add(comment);
	}
	
	public List<Comment> getComments(int boardId, int start) {
		List<Comment> list = commentDAO.getParent(boardId, start);		
		list.forEach((item)-> {
			item.setChildren(getChildren(boardId, item));
		});
		return list;
	}
	
	private List<Comment> getChildren(int boardId, Comment comment) {
		List<Comment> list = commentDAO.getChildren(boardId, comment.getId());
		list.forEach((item) -> {
			item.setChildren(getChildren(boardId, item));
		});
		return list;
	}
	
	public int getCount(int boardId) {
		return commentDAO.getCount(boardId);
	}
	
}
