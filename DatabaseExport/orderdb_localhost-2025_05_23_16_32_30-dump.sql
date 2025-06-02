--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: orderdb; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE orderdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1252';


ALTER DATABASE orderdb OWNER TO postgres;

\connect orderdb

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: bill; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (1, 'Alice', 'Widget', 3, 29.97);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (2, 'Bob', 'Gadget', 2, 39.98);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (3, 'Charlie', 'Thingamajig', 5, 49.95);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (4, 'Paul Marcus', 'Laptop', 1, 1200);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (5, 'Bob Smith', 'Tablet', 2, 1200.5);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (6, 'Raul', 'Headphones', 3, 600);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (7, 'Diana Princess', 'Laptop', 69, 82800);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (8, 'Raul', 'Smartphone', 5, 4000);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (9, 'Ethan Hunt', 'Xbox', 50, 1000);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (10, 'Bob Smith', 'PS', 1, 20.25);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (11, 'Diana Princess', 'Headphones', 7, 1400);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (12, 'Raul', 'Smartwatch', 5, 1500);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (13, 'Raul', 'Smartwatch', 4, 1200);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (14, 'Raul', 'Smartwatch', 3, 900);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (15, 'Diana Princess', 'Smartwatch', 5, 1500);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (16, 'Raul', 'Smartwatch', 5, 1500);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (17, 'Diana Princess', 'PS', 5, 1500);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (18, 'Alice Johnson', 'Laptop', 5, 101.25);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (19, 'Raul', 'Banane', 9, 180);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (20, 'Diana Princess', 'Minecraft', 9, 2700);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (22, 'Paul Marcus', 'PS', 6, 121.5);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (23, 'Ethan Hunt', 'Smartphone', 1, 800);
INSERT INTO public.bill (id, name, product_name, quantity, total_price) VALUES (24, 'Ethan Hunt', 'PS', 4, 81);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client (id, name, email) VALUES (1, 'Raul', 'raul@gmail.com');
INSERT INTO public.client (id, name, email) VALUES (2, 'Paul Marcus', 'marcuspaul@gmail.com');
INSERT INTO public.client (id, name, email) VALUES (9, 'Alice Johnson', 'alice.johnson@email.com');
INSERT INTO public.client (id, name, email) VALUES (10, 'Bob Smith', 'bob.smith@email.com');
INSERT INTO public.client (id, name, email) VALUES (11, 'Charlie Brown', 'charlie.brown@email.com');
INSERT INTO public.client (id, name, email) VALUES (13, 'Ethan Hunt', 'ethan.hunt@yahoo');
INSERT INTO public.client (id, name, email) VALUES (12, 'Diana Princess', 'diana.prince@email.com');


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product (id, productname, productprice, quantity) VALUES (3, 'Tablet', 600.25, 20);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (1, 'Laptop', 1200, 69);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (9, 'Xbox', 20, 100);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (13, 'Minecraft', 10.99, 190);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (4, 'Headphones', 200, 20);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (6, 'Banane', 20, 91);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (5, 'Smartwatch', 300, 41);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (2, 'Smartphone', 800, 14);
INSERT INTO public.product (id, productname, productprice, quantity) VALUES (10, 'PS', 20.25, 40);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (12, 2, 1, 1, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (13, 10, 3, 2, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (14, 1, 4, 3, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (16, 12, 1, 69, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (17, 1, 2, 5, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (18, 13, 9, 50, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (19, 10, 10, 1, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (20, 12, 4, 7, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (21, 1, 5, 5, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (22, 1, 5, 4, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (23, 1, 5, 3, 0);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (25, 12, 5, 5, 3000);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (26, 1, 5, 5, 1500);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (27, 10, 13, 9, 2187.01);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (28, 12, 5, 5, 16500);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (29, 9, 4, 3, 4600);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (30, 9, 10, 5, 1113.75);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (31, 1, 6, 9, 2000);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (32, 12, 5, 9, 15000);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (33, 12, 5, 0, 12300);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (34, 2, 10, 6, 1012.5);
INSERT INTO public.orders (id, id_client, id_product, quantity, total_price) VALUES (35, 13, 2, 1, 12000);


--
-- Name: bill_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bill_id_seq', 24, true);


--
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 14, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 36, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 14, true);


--
-- PostgreSQL database dump complete
--

