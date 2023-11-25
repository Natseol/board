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
	private int commentId;
	@NonNull
	private int boardId;
	@NonNull
	private int parentCommentId;
	@NonNull
	private String userId;
	@NonNull
	private String commentContent;
	private Timestamp commentCreatedAt;	
}
