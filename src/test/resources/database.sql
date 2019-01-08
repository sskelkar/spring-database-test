create table department(
  id integer,
  name varchar(20),
  primary key(id)
);

create table employee(
  id integer,
  name varchar(20),
  department_id integer,
  primary key(id),
  foreign key(department_id) references department(id)
);