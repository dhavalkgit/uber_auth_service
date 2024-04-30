CREATE TABLE passenger (
  id bigint not null AUTO_INCREMENT,
  email varchar(255) not null unique,
  name varchar(255) not null,
  password varchar(255) not null,
  phone_number varchar(255)  not null unique,
  primary key(id)
);
