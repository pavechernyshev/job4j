CREATE TABLE company
(
id integer NOT NULL,
name character varying,
CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
id integer NOT NULL,
name character varying,
company_id integer,
CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company VALUES (5, 'mining');
INSERT INTO company VALUES (3, 'lifts');
INSERT INTO company VALUES (2, 'webrise');

INSERT INTO person VALUES (1, 'Pavel ch', 5);
INSERT INTO person VALUES (2, 'Ilys ch', 5);
INSERT INTO person VALUES (3, 'Ivan ch', 5);
INSERT INTO person VALUES (9, 'Stas tc', 5);
INSERT INTO person VALUES (4, 'Vitalik ch', 3);
INSERT INTO person VALUES (5, 'Alena ch', 3);
INSERT INTO person VALUES (6, 'Pavel k', 2);
INSERT INTO person VALUES (7, 'Misha d', 2);
INSERT INTO person VALUES (8, 'Dmitriy b', 2);
-- 1) Retrieve in a single query:
-- - names of all persons that are NOT in the company with id = 5
-- - company name for each person
SELECT p.name as person_name, c.name as company_name FROM person as p
LEFT JOIN company as c ON p.company_id = c.id
WHERE c.id != 5;
-- Select the name of the company with the maximum number of persons + number of persons in this company
SELECT c.name, COUNT(p.name) as count_p FROM company as c
LEFT JOIN person as p ON c.id = p.company_id
GROUP BY c.name
ORDER BY count_p DESC
LIMIT 1;