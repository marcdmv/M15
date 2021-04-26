CREATE TABLE players (
id int(11) NOT NULL primary key auto_increment,
name varchar(100) NOT NULL,
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);