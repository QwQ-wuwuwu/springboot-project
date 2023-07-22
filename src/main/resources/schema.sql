use `mybatis`;
create table if not exists user
(
    id bigint(19) not null primary key,
    name varchar(10) not null,
    number varchar(45) not null,
    password varchar(65) not null,
    role int not null,
    description varchar(600) null,
    teacher_id bigint(19) null,
    teacher_name varchar (45) null,
    total int null,
    count int null,
    select_time datetime null,
    insert_time datetime not null default current_timestamp,
    index (number),
    index (teacher_id),
    index (role),
    unique (number)
);