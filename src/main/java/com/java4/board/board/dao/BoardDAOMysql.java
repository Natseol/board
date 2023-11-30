package com.java4.board.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.java4.board.board.domain.Board;

@Repository
public class BoardDAOMysql {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Board> mapper = new RowMapper<Board>() {
		@Override
		public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Board(rs.getInt("id"),
					rs.getString("title"),
					rs.getString("content"),
					rs.getInt("views"),
					0,
					0,
					rs.getTimestamp("created_at"),
					rs.getInt("is_withdrew")==1,
					rs.getInt("user_id"),
					rs.getString("userStrId"),
					rs.getString("git_address"));
		}
	};
	
	public void add(Board board) {
		jdbcTemplate.update("insert into boards (title, content, is_withdrew, user_id) values (?, ?, ?, ?)", board.getTitle(),
				board.getContent(), board.isWithdrew() ? 1:0, board.getUserId());
	}

	public Board get(int id) {
		return jdbcTemplate.queryForObject("select a.*, b.user_id as userStrId, b.git_address from boards a join users b on a.user_id = b.id where a.id=?", mapper, id);
	}
	
	public void del(int withdrew, int id) {
		jdbcTemplate.update("update boards set is_withdrew=? where id=?", withdrew, id);
	}
	
	public void edit(String title, String content, int id) {
		jdbcTemplate.update("update boards set title=?, content=? where id=?", title, content, id);
	}
	
	public void views(int view, int id) {
		jdbcTemplate.update("update boards set views=? where id=?", view, id);
	}
	
	public List<Board> getPage(int page, int itemNum) {
		return jdbcTemplate.query("select a.*, b.user_id as userStrId, b.git_address from boards a join users b on a.user_id = b.id order by a.id desc limit ?, ?", mapper,page,itemNum);
	}

	public List<Board> getAll() {
		return jdbcTemplate.query("select a.*, b.user_id as userStrId, b.git_address from boards a join users b on a.user_id = b.id order by a.id desc ", mapper);
	}
	
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from boards", Integer.class);
	}
}
