package com.java4.board.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public Board get(int id) {
		return boardDAO.get(id);		
	}
	
	public void del(int withdrew, int id) {
		boardDAO.del(withdrew, id);
	}
	
	public void edit(String title, String content, int id) {
		boardDAO.edit(title, content, id);
	}

	public List<String> getBoardUserIDs() {
		List<String> idList = boardDAO.getAll().stream()
				.map(board -> userDAO.get(board.getUserId()).getUserId())
				.collect(Collectors.toList());		
		return idList;
	}
	
	public List<Board> getPage(int page, int num) {
		List<Board> list = boardDAO.getPage(page, num);		
		return getExceptWithdrew(list);
	}
	
	public List<Board> getAll() {
		List<Board> list = boardDAO.getAll();	
		return getExceptWithdrew(list);
	}
	
	private List<Board> getExceptWithdrew(List<Board> tempList) {
		List<Board> list = tempList.stream()
				.filter(board -> board.isWithdrew()==false)
				.collect(Collectors.toList());
		return list;
	}
	
	public int getPageCount(int count) {
		int num = (boardDAO.getCount()-1)/count +1;
		return num;
	}
}
