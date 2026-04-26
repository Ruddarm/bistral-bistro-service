--
-- PostgreSQL database dump
--

--restrict CT06vxNcMTrjESzS7KFrqg3i7FFQBdB9FebVEGRufTzvz8mcVQmpyUdB3M1fvmF

-- Dumped from database version 17.6 (Debian 17.6-1.pgdg13+1)
-- Dumped by pg_dump version 17.6 (Debian 17.6-1.pgdg13+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bistro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bistro (
    bistro_id uuid NOT NULL,
    bistro_name character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    logo_url character varying(255),
    updated_at timestamp(6) without time zone,
    user_id uuid NOT NULL
);


ALTER TABLE public.bistro OWNER TO postgres;

--
-- Name: bistro_branch; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bistro_branch (
    branch_id uuid NOT NULL,
    address character varying(255),
    branch_name character varying(255) NOT NULL,
    bistro_id uuid
);


ALTER TABLE public.bistro_branch OWNER TO postgres;

--
-- Name: bistro_tables; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bistro_tables (
    table_id uuid NOT NULL,
    table_no integer NOT NULL,
    branch uuid,
    zone_id uuid
);


ALTER TABLE public.bistro_tables OWNER TO postgres;

--
-- Name: branch_zones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.branch_zones (
    zone_id uuid DEFAULT gen_random_uuid() NOT NULL,
    branch_id uuid NOT NULL,
    name character varying(100) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.branch_zones OWNER TO postgres;



--
-- Name: import_job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.import_job (
    job_id uuid NOT NULL,
    failed_rows integer NOT NULL,
    job_status character varying(30) DEFAULT 'PENDING'::character varying NOT NULL,
    row_process integer NOT NULL,
    success_rows integer NOT NULL,
    CONSTRAINT import_job_job_status_check CHECK (((job_status)::text = ANY ((ARRAY['PENDING'::character varying, 'STARTED'::character varying, 'RUNNING'::character varying, 'COMPLETE'::character varying, 'FAILED'::character varying])::text[])))
);


ALTER TABLE public.import_job OWNER TO postgres;

--
-- Name: menu_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_item (
    item_id uuid NOT NULL,
    is_veg boolean NOT NULL,
    item_name character varying(255) NOT NULL,
    menu_id uuid,
    category_id uuid NOT NULL
);


ALTER TABLE public.menu_item OWNER TO postgres;

--
-- Name: menu_item_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_item_category (
    category_id uuid NOT NULL,
    category_name character varying(255) NOT NULL,
    menu_id uuid
);


ALTER TABLE public.menu_item_category OWNER TO postgres;

--
-- Name: menu_item_variant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_item_variant (
    variant_id uuid NOT NULL,
    is_tax_included boolean NOT NULL,
    price numeric(10,2) NOT NULL,
    qty numeric(38,2) NOT NULL,
    tax_rate numeric(10,2),
    unit character varying(255) NOT NULL,
    variant_name character varying(255) NOT NULL,
    menu_item_id uuid NOT NULL,
    CONSTRAINT menu_item_variant_unit_check CHECK (((unit)::text = ANY ((ARRAY['PIECE'::character varying, 'PLATE'::character varying, 'ML'::character varying, 'LITRE'::character varying, 'G'::character varying, 'KG'::character varying, 'Glass'::character varying, 'Inch'::character varying])::text[])))
);


ALTER TABLE public.menu_item_variant OWNER TO postgres;

--
-- Name: menus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menus (
    menu_id uuid NOT NULL,
    menu_name character varying(255),
    bistro_id uuid
);


ALTER TABLE public.menus OWNER TO postgres;

--
-- Name: bistro_branch bistro_branch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro_branch
    ADD CONSTRAINT bistro_branch_pkey PRIMARY KEY (branch_id);


--
-- Name: bistro bistro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro
    ADD CONSTRAINT bistro_pkey PRIMARY KEY (bistro_id);


--
-- Name: bistro_tables bistro_tables_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro_tables
    ADD CONSTRAINT bistro_tables_pkey PRIMARY KEY (table_id);


--
-- Name: menus bistroid_menuid; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT bistroid_menuid UNIQUE (menu_id, bistro_id);


--
-- Name: branch_zones branch_zones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.branch_zones
    ADD CONSTRAINT branch_zones_pkey PRIMARY KEY (zone_id);


--
-- Name: bistro_branch branchid_bistro; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro_branch
    ADD CONSTRAINT branchid_bistro UNIQUE (bistro_id, branch_id);



--
-- Name: import_job import_job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.import_job
    ADD CONSTRAINT import_job_pkey PRIMARY KEY (job_id);


--
-- Name: menu_item itemid_menuid; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item
    ADD CONSTRAINT itemid_menuid UNIQUE (item_id, menu_id);


--
-- Name: menu_item_category menu_item_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item_category
    ADD CONSTRAINT menu_item_category_pkey PRIMARY KEY (category_id);


--
-- Name: menu_item menu_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item
    ADD CONSTRAINT menu_item_pkey PRIMARY KEY (item_id);


--
-- Name: menu_item_variant menu_item_variant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item_variant
    ADD CONSTRAINT menu_item_variant_pkey PRIMARY KEY (variant_id);


--
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (menu_id);


--
-- Name: menu_item_variant variantid_menu_item_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item_variant
    ADD CONSTRAINT variantid_menu_item_id UNIQUE (variant_id, menu_item_id);


--
-- Name: branchindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX branchindex ON public.bistro_tables USING btree (branch);



--
-- Name: menu_item_variant fk3ej0ha9gdf35xi0dkdhufvrlp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item_variant
    ADD CONSTRAINT fk3ej0ha9gdf35xi0dkdhufvrlp FOREIGN KEY (menu_item_id) REFERENCES public.menu_item(item_id);


--
-- Name: menu_item fk9bb8cwxyt5wuvlofuxaw95es0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item
    ADD CONSTRAINT fk9bb8cwxyt5wuvlofuxaw95es0 FOREIGN KEY (menu_id) REFERENCES public.menus(menu_id);


--
-- Name: branch_zones fk_zone_branch; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.branch_zones
    ADD CONSTRAINT fk_zone_branch FOREIGN KEY (branch_id) REFERENCES public.bistro_branch(branch_id);


--
-- Name: bistro_tables fkb5yif5tt6gv1o3t3s600ms7nd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro_tables
    ADD CONSTRAINT fkb5yif5tt6gv1o3t3s600ms7nd FOREIGN KEY (branch) REFERENCES public.bistro_branch(branch_id);


--
-- Name: menu_item_category fkb8y6ul18tgwle5yyoipabobwk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item_category
    ADD CONSTRAINT fkb8y6ul18tgwle5yyoipabobwk FOREIGN KEY (menu_id) REFERENCES public.menus(menu_id);


--
-- Name: bistro_branch fkbcmeiqkqdye585bhy22uuphkl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bistro_branch
    ADD CONSTRAINT fkbcmeiqkqdye585bhy22uuphkl FOREIGN KEY (bistro_id) REFERENCES public.bistro(bistro_id);


--
-- Name: menus fkmj1gmhx6rf9snmrjd12wci0mg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT fkmj1gmhx6rf9snmrjd12wci0mg FOREIGN KEY (bistro_id) REFERENCES public.bistro(bistro_id);


--
-- Name: menu_item fkrg1jd0yrke3v4ca1gfhksblxx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_item
    ADD CONSTRAINT fkrg1jd0yrke3v4ca1gfhksblxx FOREIGN KEY (category_id) REFERENCES public.menu_item_category(category_id);


--
-- PostgreSQL database dump complete
--
----\unrestrict CT06vxNcMTrjESzS7KFrqg3i7FFQBdB9FebVEGRufTzvz8mcVQmpyUdB3M1fvmF
Alter table public.bistro_branch add constraint unique_branch_name unique(branch_name,bistro_id);
Alter table public.bistro_branch alter column bistro_id set not null

