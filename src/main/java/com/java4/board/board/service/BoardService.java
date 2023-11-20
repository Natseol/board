package com.java4.board.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.board.board.dao.BoardDAO;
import com.java4.board.board.domain.Board;
import com.java4.board.user.dao.UserDAO;
;

@Service
public class BoardService {
	@Autowired
	BoardDAO boardDAO;
	@Autowired
	UserDAO userDAO;
	
	public void add(Board board) {
		boardDAO.add(board);
		
	}

	public List<String> getBoardUserIDs() {
		List<Board> list = boardDAO.getAll();
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {	
			idList.add(i, userDAO.get(list.get(i).getUserId()).getUserId());
		}		
		return idList;
	}
	
	
	public List<Board> getAll() {
		List<Board> list = boardDAO.getAll();		
		return list;
	}
}
