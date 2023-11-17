CREATE TABLE "USERS" 
   (	"ID" NUMBER GENERATED AS IDENTITY constraint pk_users_id primary key,
	"USER_ID" varchar2(50) not null unique, 
	"PASSWORD" VARCHAR2(64) NOT NULL,
	"NAME" VARCHAR2(15) NOT NULL,
    "PHONE" VARCHAR2(14) NOT NULL,
    "ADDRESS" VARCHAR2(100),
    "EMAIL" VARCHAR2(100) NOT NULL UNIQUE,
    "GIT_ADDRESS    " VARCHAR2(100),
    "GENDER" NUMBER(1),
    "BIRTH" DATE,
    "CREATED_AT" TIMESTAMP
    );
  
CREATE TABLE "BOARDS" 
   (	"ID" NUMBER GENERATED AS IDENTITY constraint pk_boardsd_id primary key,
	"USER_ID" NUMBER, 
	"TITLE" VARCHAR2(100 BYTE) NOT NULL ENABLE,     
	"CONTENT" LONG NOT NULL ENABLE,
    "VIEW" number default 0,
    "CREATED_AT" timestamp,
    "IS_WITHDREW" number(1),
    constraint fk_boards_id foreign key (id) references users (id)
    );
    
CREATE TABLE "LIKES_AND_HATES" 
   (	"ID" NUMBER GENERATED AS IDENTITY constraint pk_lah_id primary key,
	"USER_ID" NUMBER, 
	"BOARD_ID" number,
	"LIKES_OR_HATES" NUMBER(1),    
    "CREATED_AT" timestamp,
    constraint fk_lah_user_id foreign key (USER_ID) references USERS (ID),
    constraint fk_lah_board_id foreign key (BOARD_ID) references BOARDS (ID)
    );