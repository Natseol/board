alter session set "_ORACLE_SCRIPT"=true;
create user board identified by qwer;
grant connect, resource, dba to board 

CREATE TABLE users
   (	"id" NUMBER GENERATED AS IDENTITY primary key,
	"user_id" varchar2(50) not null unique, 
	"password" VARCHAR2(64) NOT NULL,
	"name" VARCHAR2(15) NOT NULL,
    "phone" VARCHAR2(14) NOT NULL,
    "address" VARCHAR2(100),
    "email" VARCHAR2(100) NOT NULL UNIQUE,
    "git_address" VARCHAR2(100),
    "gender" NUMBER(1),
    "birth" DATE,
    "created_at" timestamp default current_timestamp
    );
  
CREATE TABLE boards
   (	"id" NUMBER GENERATED AS IDENTITY primary key,
	
	"title" VARCHAR2(100 BYTE) NOT NULL ENABLE,     
	"content" LONG NOT NULL,
    "views" number default 0,
    "created_at" timestamp default current_timestamp,
    "is_withdrew" number(1),
    "user_id" NUMBER,
    constraint fk_user foreign key ("user_id") references users ("id")
    );
    
CREATE TABLE likes_and_hates 
   (	"id" NUMBER GENERATED AS IDENTITY primary key,
	"user_id" NUMBER,
	"board_id" number,
	"likes_or_hates" NUMBER(1),    
    "created_at" timestamp default current_timestamp,
    constraint fk_user_lah foreign key ("user_id") references users ("id"),
    constraint fk_board_lah foreign key ("board_id") references boards ("id")
    );
    
CREATE TABLE comments (
    "id" NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    "content" VARCHAR2(1000),    
    "created_at" TIMESTAMP default current_timestamp,
    "is_withdrew" number(1) default 0,
    "user_id" NUMBER not null,
    "board_id" NUMBER not null,
    "comment_id" NUMBER,    
    
    constraint fk_user_cmt FOREIGN KEY ("user_id") REFERENCES users ("id"),
    constraint fk_board_cmt FOREIGN KEY ("board_id") REFERENCES boards ("id"),
    constraint fk_id_cmt FOREIGN KEY ("comment_id") REFERENCES comments ("id")    
);