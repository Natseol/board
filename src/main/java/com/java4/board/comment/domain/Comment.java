package com.java4.board.comment.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment {
	private int id;
	@NonNull
	private String content;
	private Timestamp createdAt;
	private boolean withdrew = false;
	@NonNull
	private int userId;
	@NonNull
	private int boardId;	
	private int commentId;
	private List<Comment> children;
	
	private String userStrId;
}
