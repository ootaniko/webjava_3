--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 10.10

-- Started on 2019-10-02 02:13:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2816 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16394)
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
    item_id integer NOT NULL,
    item_name text NOT NULL,
    price integer NOT NULL
);


ALTER TABLE public.items OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16419)
-- Name: t_character; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_character (
    character_id integer NOT NULL,
    character_name text NOT NULL,
    job_id integer NOT NULL,
    command_id integer
);


ALTER TABLE public.t_character OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16402)
-- Name: t_job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_job (
    job_id integer NOT NULL,
    job_name text NOT NULL,
    battle_message text NOT NULL,
    heal_message text NOT NULL
);


ALTER TABLE public.t_job OWNER TO postgres;

--
-- TOC entry 2806 (class 0 OID 16394)
-- Dependencies: 196
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (item_id, item_name, price) FROM stdin;
1111	ああああ	10003
2222	いいいい	20002
3333	うううう	50000
\.


--
-- TOC entry 2808 (class 0 OID 16419)
-- Dependencies: 198
-- Data for Name: t_character; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_character (character_id, character_name, job_id, command_id) FROM stdin;
0	名無しさん	0	1
\.


--
-- TOC entry 2807 (class 0 OID 16402)
-- Dependencies: 197
-- Data for Name: t_job; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_job (job_id, job_name, battle_message, heal_message) FROM stdin;
0	戦士	は剣で攻撃した！	はやくそうで回復した！
1	魔法使い	は魔法で攻撃した！	はまほうで回復した！
2	武闘家	は拳で攻撃した！	はやくそうで回復した！
\.


--
-- TOC entry 2684 (class 2606 OID 16423)
-- Name: t_character T_CHARACTER_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_character
    ADD CONSTRAINT "T_CHARACTER_pkey" PRIMARY KEY (character_id);


--
-- TOC entry 2682 (class 2606 OID 16406)
-- Name: t_job T_JOB_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_job
    ADD CONSTRAINT "T_JOB_pkey" PRIMARY KEY (job_id);


--
-- TOC entry 2680 (class 2606 OID 16401)
-- Name: items items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (item_id);


-- Completed on 2019-10-02 02:13:03

--
-- PostgreSQL database dump complete
--

