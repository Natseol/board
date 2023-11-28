package com.java4.board.comment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			return new Comment(rs.getInt("id"),
					rs.getString("content"),
					rs.getTimestamp("created_at"),
					rs.getInt("is_withdrew")==1,
					rs.getInt("user_id"),
					rs.getInt("board_id"),
					rs.getInt("comment_id"),
					null,
					rs.getString("userStrId")
					);
		}
	};
	
	public void add(Comment comment) {
		jdbcTemplate.update("insert into comments (\"content\", \"user_id\", \"board_id\", \"comment_id\") values (?, ?, ?, ?)",
				comment.getContent(), comment.getUserId(), comment.getBoardId(), comment.getCommentId()>0 ? comment.getCommentId():null );
	}
	
	public Comment get(int id) {
		return jdbcTemplate.queryForObject("select * from comments where \"id\"=?", mapper, id);
	}	
	
	public List<Comment> getComments(int boardId) {
		return jdbcTemplate.query("select * from comments where \"board_id\"=? order by \"id\" desc offset 0 rows fetch first 5 rows only", mapper, boardId);
	}	 
	
	public List<Comment> getParent(int boardId, int start) {
		return jdbcTemplate.query("select a.*, b.\"user_id\" as \"userStrId\""
				+ " from comments a join users b on a.\"user_id\" = b.\"id\""
				+ " where a.\"board_id\"=? and a.\"comment_id\" is null"
				+ " order by a.\"id\" desc offset ? rows fetch first 5 rows only", mapper, boardId, start);
	}
	
	public List<Comment> getChildren(int boardId, int comentId) {
		return jdbcTemplate.query("select a.*, b.\"user_id\" as \"userStrId\" "
				+ "from comments a join users b on a.\"user_id\" = b.\"id\" "
				+ "where a.\"board_id\"=? and a.\"comment_id\" = ? order by a.\"id\"", mapper, boardId, comentId);
	}
	
	public int getCount(int boardId) {
		return jdbcTemplate.queryForObject("select count(*) from comments "
				+ "where \"board_id\"=? and \"comment_id\" is null", Integer.class, boardId);
	}
}
