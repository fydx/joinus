drop schema if exists `JSDB` ;
CREATE SCHEMA `JSDB` ;
use `JSDB` ;

/* clean up tables*/

drop table if exists person;
drop table if exists activities;

/* create table */

-- 个人账户表
create table person
(
    person_id varchar(15) not null comment '个人id号',
    person_name varchar(10) not null comment '个人姓名',
    person_pwd varchar(15) not null comment '个人密码',
    person_sex enum('男','女') comment '性别',
    person_tel varchar(15) comment '电话号码',
    person_qq varchar(10) comment 'qq号码',
    person_email varchar(25) comment '电子邮箱' ,
    primary key(person_id)
);


-- 社团表
create table club
( 
    club_id varchar(15) not null comment '社团id',
    club_name varchar(15) not null comment '社团名称',
    club_description nvarchar(2000) not null comment '社团详情',
    primary key(club_id)
);

-- 活动表
create table activities
(
    activities_id int unsigned auto_increment not null comment '活动id',
    activities_name nvarchar(40) not null comment '活动名称',
    activities_content nvarchar(2000) comment '活动内容',
    activities_publisher_id varchar(15) comment '发起社团id',
    activities_position varchar(20) not null comment '活动地点',
    activities_love_count int unsigned default 0 comment '喜爱人数',
    activities_join_count int unsigned default 0 comment '参与人数',
    activities_time timestamp not null default current_timestamp comment '活动开始时间',
    primary key(activities_id),
    foreign key(activities_publisher_id) references club(club_id) on delete cascade on update cascade
);

-- 参与活动表
create table join_activities
(
    activities_id int unsigned not null comment '活动id',
    person_id varchar(15) not null comment '参与者id',
    primary key(activities_id,person_id),
    foreign key(activities_id) references activities(activities_id) on delete cascade on update cascade,
    foreign key(person_id) references person(person_id) on delete cascade on update cascade
);

-- 喜爱活动表
create table love_activities
(
    activities_id int unsigned not null comment '活动id',
    person_id varchar(15) not null comment '喜爱者id',
    primary key(activities_id,person_id),
    foreign key(activities_id) references activities(activities_id) on delete cascade on update cascade,
    foreign key(person_id) references person(person_id) on delete cascade on update cascade
);

-- 活动留言表
create table activities_message
(
    activities_id int unsigned not null comment '活动id',
    person_id varchar(15) not null comment '留言者id',
    activities_message_content nvarchar(200) not null comment '留言内容',
    primary key(activities_id,person_id),
    foreign key(activities_id) references activities(activities_id) on delete cascade on update cascade,
    foreign key(person_id) references person(person_id) on delete cascade on update cascade
);

/*静态数据*/
insert into person values('lx2882065','李恂','1234qwer','男','18392382876','409237225','409237225@qq.com');

insert into club values('sssta','西电软院科协','这是社团详情');

insert into activities values(1,'这是活动名称','这是活动内容','sssta','机房',1,1,NULL);

insert into join_activities values(1,'lx2882065');

insert into love_activities values(1,'lx2882065');

insert into activities_message values(1,'lx2882065','这是留言内容');
