package com.java4.board.board.domain;

import java.sql.Date;

import com.java4.board.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Board {
	private int id;
	@NonNull
	private String title;
	@NonNull
	private String content;
	@NonNull
	private int views;
	@NonNull
	private int likes;
	@NonNull
	private int hates;
	@NonNull
	private Date createAt;
	@NonNull
	private User writer;
	@NonNull
	private boolean isWithdrew;
//	private Category category;
}
