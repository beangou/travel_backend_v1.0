# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog_post (
  id                            bigint auto_increment not null,
  subject                       varchar(255) not null,
  content                       TEXT,
  user_id                       bigint,
  comment_count                 bigint,
  constraint pk_blog_post primary key (id)
);

create table my_course (
  id                            integer auto_increment not null,
  type                          integer,
  title                         varchar(255),
  content                       varchar(255),
  parent_id                     integer,
  constraint pk_my_course primary key (id)
);

create table my_option (
  id                            integer auto_increment not null,
  content                       varchar(255),
  question_id                   integer,
  created_at                    datetime(6),
  updated_at                    datetime(6),
  deleted_at                    datetime(6),
  constraint pk_my_option primary key (id)
);

create table post_comment (
  id                            bigint auto_increment not null,
  blog_post_id                  bigint,
  user_id                       bigint,
  content                       TEXT,
  constraint pk_post_comment primary key (id)
);

create table my_question (
  id                            integer auto_increment not null,
  title                         varchar(255),
  answers                       varchar(255),
  analysis                      varchar(255),
  constraint pk_my_question primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  email                         varchar(255) not null,
  sha_password                  varbinary(64) not null,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

alter table blog_post add constraint fk_blog_post_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_blog_post_user_id on blog_post (user_id);

alter table my_option add constraint fk_my_option_question_id foreign key (question_id) references my_question (id) on delete restrict on update restrict;
create index ix_my_option_question_id on my_option (question_id);

alter table post_comment add constraint fk_post_comment_blog_post_id foreign key (blog_post_id) references blog_post (id) on delete restrict on update restrict;
create index ix_post_comment_blog_post_id on post_comment (blog_post_id);

alter table post_comment add constraint fk_post_comment_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_comment_user_id on post_comment (user_id);


# --- !Downs

alter table blog_post drop foreign key fk_blog_post_user_id;
drop index ix_blog_post_user_id on blog_post;

alter table my_option drop foreign key fk_my_option_question_id;
drop index ix_my_option_question_id on my_option;

alter table post_comment drop foreign key fk_post_comment_blog_post_id;
drop index ix_post_comment_blog_post_id on post_comment;

alter table post_comment drop foreign key fk_post_comment_user_id;
drop index ix_post_comment_user_id on post_comment;

drop table if exists blog_post;

drop table if exists my_course;

drop table if exists my_option;

drop table if exists post_comment;

drop table if exists my_question;

drop table if exists user;

