-- 1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT * from product as p
INNER JOIN type as t ON p.type_id = t.id
WHERE t.name = 'СЫР';
-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT * from product as p
WHERE name LIKE '%мороженное%';
-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * from product as p
WHERE expired_date > '2019.05.31' AND expired_date < '2019.07.01'
-- 4. Написать запрос, который выводит самый дорогой продукт.
SELECT * FROM product
WHERE price = (SELECT MAX(price) from product);
-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT count(p.id), t.name as type_name FROM product as p
INNER JOIN type as t ON p.type_id = t.id
GROUP BY (t.name);
-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT * from product as p
INNER JOIN type as t ON p.type_id = t.id
WHERE t.name = 'СЫР' or t.name = 'МОЛОКО';
-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT t.name as type_name FROM product as p
INNER JOIN type as t ON p.type_id = t.id
GROUP BY (t.name)
HAVING count(p.id) < 10;
-- 8. Вывести все продукты и их тип.
SELECT * from product as p
INNER JOIN type as t ON p.type_id = t.id;