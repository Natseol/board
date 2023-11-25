package com.java4.board.comment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.java4.board.comment.domain.Comment;

@Repository
public class CommentDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Comment> mapper = new RowMapper<>() {
		@Override
		public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Comment(rs.getInt("comment_id"),
					rs.getInt("board_id"),
					rs.getInt("parent_comment_id"),
					rs.getString("user_id"),
					rs.getString("content"),
					rs.getTimestamp("created_at"));
		}
	};
	
	public void add(Comment comment) {
		jdbcTemplate.update("insert into comments (\"board_id\", \"parent_comment_id\", \"user_id\", \"content\") values (?, ?, ?, ?)",
				comment.getBoardId(), comment.getParentCommentId(), comment.getUserId(), comment.getCommentContent());
	}
	
	public Comment get(int id) {
		return jdbcTemplate.queryForObject("select * from comments where \"id\"=?", mapper, id);
	}	
	
	public List<Comment> getComments(int boardId) {
		return jdbcTemplate.query("select * from comments where \"board_id\"=?", mapper, boardId);
	}	 
	
	public List<Comment> getAll() {
		return jdbcTemplate.query("select * from comments order by \"id\" desc ", mapper);
	}
}
