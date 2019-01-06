create database example;
grant all privileges on example.* to 'example'@'localhost' identified by 'example';
use example;
create table department(
  id integer,
  name varchar(20),
  primary key(id)
);
insert into department values(10, 'finance');
insert into department values(20, 'hr');

create table employee(
  id integer,
  name varchar(20),
  department_id integer,
  primary key(id),
  foreign key(department_id) references department(id)
);
insert into employee values(1, 'jason', 10);
insert into employee values(2, 'wendy', 20);
insert into employee values(3, 'karun', 20);