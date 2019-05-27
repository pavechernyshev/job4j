--  1. Создать структур данных в базе.
--Таблицы.
--   Кузов. Двигатель, Коробка передач.
create table bodywork (
  id serial primary key,
  name varchar (200)
);
create table engine (
  id serial primary key,
  name varchar (200)
);
create table transmission (
  id serial primary key,
  name varchar (200)
);
-- 2. Создать структуру Машина. Машина не может существовать без данных из п.1.
create table auto (
  id serial primary key,
  name varchar (200),
  bodywork_id int references bodywork(id),
  engine_id int references engine(id),
  transmission_id int references transmission(id)
);
-- 3. Заполнить таблицы через insert.
insert into bodywork (name) values ('sedan');
insert into bodywork (name) values ('cabriolet');
insert into bodywork (name) values ('pickup');
insert into engine (name) values ('gasoline');
insert into engine (name) values ('diesel');
insert into engine (name) values ('hybrid');
insert into transmission (name) values ('automate');
insert into transmission (name) values ('hand');
insert into transmission (name) values ('hybrid');
insert into auto (name, bodywork_id, engine_id, transmission_id) values ('volvo', 1, 2, 3);
insert into auto (name, bodywork_id, engine_id, transmission_id) values ('mercedes', 2, 1, 2);
-- 1. Вывести список всех машин и все привязанные к ним детали
select * from auto
left join bodywork b on auto.bodywork_id = b.id
left join engine e on auto.engine_id = e.id
left join transmission t on auto.transmission_id = t.id;
-- 2. Вывести отдельно детали, которые не используются в машине, кузова, двигатели, коробки передач.
select b.name from auto right outer join bodywork b on auto.bodywork_id = b.id where auto.id is null;
select e.name from auto right outer join engine e on auto.bodywork_id = e.id where auto.id is null;
select t.name from auto right outer join transmission t on auto.bodywork_id = t.id where auto.id is null;