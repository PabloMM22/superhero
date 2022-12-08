drop table if exists superhero;
create table superhero (id number auto_increment  primary key,  name varchar(100) not null, company varchar(100) not null);
insert into superhero (name, company) VALUES ('Spiderman', 'Marvel'), ('Batman', 'DC'), ('Manolito el fuerte', 'Unknown'),  ('Ironman', 'Marvel'),  ('Thor', 'Marvel'),  ('Deadpool', 'Marvel');