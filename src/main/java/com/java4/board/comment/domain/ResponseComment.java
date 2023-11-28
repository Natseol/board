package com.java4.board.comment.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseComment {
	List<Comment> list;
	boolean isEnd;
}
