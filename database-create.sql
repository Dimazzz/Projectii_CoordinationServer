CREATE SEQUENCE pii_gamers_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_wpn_tps_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_wpn_mdls_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_egen_mdls_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_eshld_mdls_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;


CREATE SEQUENCE pii_ngn_mdls_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_sship_mdls_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_sships_pk_seq
  MINVALUE 0
  INCREMENT BY 1
  NO CYCLE
  NO MAXVALUE;

CREATE SEQUENCE pii_gsessions_pk_seq 
  minvalue 0
  increment by 1
  cycle
  no maxvalue;

CREATE TABLE pii_gamers (
  id integer
    CONSTRAINT pii_gamers_pk PRIMARY KEY
    CONSTRAINT pii_gamers_df DEFAULT nextval('pii_gamers_pk_seq'),
  email varchar
    CONSTRAINT pii_gamers_email_nn NOT NULL
    CONSTRAINT pii_gamers_email_u UNIQUE,
  passwd varchar
    CONSTRAINT pii_gamers_password_nn NOT NULL,
  nickname varchar
    CONSTRAINT pii_gamers_nickname_nn NOT NULL
    CONSTRAINT pii_gamers_nickname_u UNIQUE,
  exp integer
    CONSTRAINT pii_gamers_exp_nn NOT NULL
);

CREATE TABLE pii_wpn_tps (
  id integer
    CONSTRAINT pii_wpn_tps_pk PRIMARY KEY
    CONSTRAINT pii_wpn_tps_df DEFAULT nextval('pii_wpn_tps_pk_seq'),
  name varchar
    CONSTRAINT pii_wpn_tps_name_nn NOT NULL
    CONSTRAINT pii_wpn_tps_name_u UNIQUE
);

CREATE TABLE pii_wpn_mdls (
  id integer
    CONSTRAINT pii_wpn_mdls_pk PRIMARY KEY
    CONSTRAINT pii_wpn_mdls_df DEFAULT nextval('pii_wpn_mdls_pk_seq'),
  name varchar
    CONSTRAINT pii_wpn_mdls_name_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_name_u UNIQUE,
  type integer
    CONSTRAINT pii_wpn_mdls_type_fk REFERENCES pii_wpn_tps (id),
  rate integer
    CONSTRAINT pii_wpn_mdls_rate_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_rate_ck CHECK (rate >= 0),
  pspeed integer
    CONSTRAINT pii_wpn_mdls_pspeed_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_pspeed_ck CHECK (pspeed >= 0),
  damage integer
    CONSTRAINT pii_wpn_mdls_damage_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_damage_ck CHECK (damage >= 0),
  nrgcons integer
    CONSTRAINT pii_wpn_mdls_nrgcons_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_nrgcons_ck CHECK (nrgcons >= 0),
  distance integer
    CONSTRAINT pii_wpn_mdls_distance_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_distance_ck CHECK (distance >= 0),
  range integer
    CONSTRAINT pii_wpn_mdls_range_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_range_ck CHECK (range >= 0),
  cd integer
    CONSTRAINT pii_wpn_mdls_cd_nn NOT NULL
    CONSTRAINT pii_wpn_mdls_cd_ck CHECK (cd >= 0),
  price integer
    CONSTRAINT pii_wpn_mdls_price_ck CHECK (price >= 0),
  master integer
    CONSTRAINT pii_wpn_mdls_master_fk REFERENCES pii_wpn_mdls(id)
);

CREATE TABLE pii_egen_mdls (
  id integer
    CONSTRAINT pii_egen_mdls_pk PRIMARY KEY
    CONSTRAINT pii_egen_mdls_df DEFAULT nextval('pii_egen_mdls_pk_seq'),
  name varchar
    CONSTRAINT pii_egen_mdls_name_nn NOT NULL
    CONSTRAINT pii_egen_mdls_name_u UNIQUE,
  max_nrg_lvl integer
    CONSTRAINT pii_egen_mdls_mnl_nn NOT NULL
    CONSTRAINT pii_egen_mdls_mnl_ck CHECK (max_nrg_lvl >= 0),
  reg_spd integer
    CONSTRAINT pii_egen_mdls_rs_nn NOT NULL
    CONSTRAINT pii_egen_mdls_rs_ck CHECK (reg_spd >= 0),
  price integer
    CONSTRAINT pii_egen_mdls_price_ck CHECK (price >= 0),
  master integer
    CONSTRAINT pii_egen_mdls_master_fk REFERENCES pii_egen_mdls(id)
);

CREATE TABLE pii_eshld_mdls (
  id integer
    CONSTRAINT pii_eshld_mdls_pk PRIMARY KEY
    CONSTRAINT pii_eshld_mdls_df DEFAULT nextval('pii_eshld_mdls_pk_seq'),
  name varchar
    CONSTRAINT pii_eshld_mdls_name_nn NOT NULL
    CONSTRAINT pii_eshld_mdls_name_u UNIQUE,
  max_nrg_lvl integer
    CONSTRAINT pii_eshld_mdls_mnl_nn NOT NULL
    CONSTRAINT pii_eshld_mdls_mnl_ck CHECK (max_nrg_lvl >= 0),
  reg_spd integer
    CONSTRAINT pii_eshld_mdls_rs_nn NOT NULL
    CONSTRAINT pii_eshld_mdls_rs_ck CHECK (reg_spd >= 0),
  reg_dly integer
    CONSTRAINT pii_eshld_mdls_rd_nn NOT NULL
    CONSTRAINT pii_eshld_mdls_rd_ck CHECK (reg_dly >= 0),
  price integer
    CONSTRAINT pii_eshld_mdls_price_ck CHECK (price >= 0),
  master integer
    CONSTRAINT pii_eshld_mdls_master_fk REFERENCES pii_eshld_mdls(id)
);

CREATE TABLE pii_ngn_mdls (
  id integer
    CONSTRAINT pii_ngn_mdls_pk PRIMARY KEY
    CONSTRAINT pii_ngn_mdls_df DEFAULT nextval('pii_ngn_mdls_pk_seq'),
  name varchar
    CONSTRAINT pii_ngn_mdls_name_nn NOT NULL
    CONSTRAINT pii_ngn_mdls_name_u UNIQUE,
  max_spd integer
    CONSTRAINT pii_ngn_mdls_ms_nn NOT NULL,
  accl integer
    CONSTRAINT pii_ngn_mdls_accl_nn NOT NULL,
  mbility integer
    CONSTRAINT pii_ngn_mdls_mbility_nn NOT NULL,
  price integer
   CONSTRAINT pii_ngn_mdls_price_ck CHECK (price >= 0),
  master integer
    CONSTRAINT pii_ngn_mdls_master_fk REFERENCES pii_ngn_mdls(id)
);

CREATE TABLE pii_sship_mdls (
  id integer
    CONSTRAINT pii_sship_mdls_pk PRIMARY KEY
    CONSTRAINT pii_sship_mdls_df DEFAULT nextval('pii_sship_mdls_pk_seq'),
  name varchar
    CONSTRAINT pii_sship_mdls_name_nn NOT NULL
    CONSTRAINT pii_sship_mdls_name_u UNIQUE,
  wpn_slot_num integer
    CONSTRAINT pii_sship_mdls_wsn_nn NOT NULL
    CONSTRAINT pii_sship_mdls_wsn_ck CHECK (wpn_slot_num >= 0),
  hp integer
    CONSTRAINT pii_sship_mdls_hp_nn NOT NULL
    CONSTRAINT pii_sship_mdls_hp_ck CHECK (hp >= 0),
  armor integer
    CONSTRAINT pii_sship_mdls_armor_nn NOT NULL
    CONSTRAINT pii_sship_mdls_armor_ck CHECK (wpn_slot_num >= 0),
  width integer
    CONSTRAINT pii_sship_mdls_width_nn NOT NULL
    CONSTRAINT pii_sship_mdls_width_ck CHECK (width >= 0),
  length integer
    CONSTRAINT pii_sship_mdls_length_nn NOT NULL
    CONSTRAINT pii_sship_mdls_length_ck CHECK (length >= 0),
  price integer
    CONSTRAINT pii_sship_mdls_price_ck CHECK (price >= 0),
  master integer
    CONSTRAINT pii_sship_mdls_master_fk REFERENCES pii_sship_mdls(id)
);

CREATE TABLE pii_sships (
  id integer
    CONSTRAINT pii_sships_pk PRIMARY KEY
    CONSTRAINT pii_sships_df DEFAULT nextval('pii_sships_pk_seq'),
  owner integer
    CONSTRAINT pii_sships_owner_fk REFERENCES pii_gamers(id)
    CONSTRAINT pii_sships_owner_nn NOT NULL,
  mdl integer
    CONSTRAINT pii_sships_mdls_fk REFERENCES pii_sship_mdls(id)
    CONSTRAINT pii_sships_mdls_nn NOT NULL,
  ngn integer
    CONSTRAINT pii_sships_ngn_fk REFERENCES pii_ngn_mdls(id)
    CONSTRAINT pii_sships_ngn_nn NOT NULL,
  nrg_gen integer
    CONSTRAINT pii_sships_ng_fk REFERENCES pii_egen_mdls(id)
    CONSTRAINT pii_sships_ng_nn NOT NULL,
  nrg_shld integer
    CONSTRAINT pii_sships_ns_fk REFERENCES pii_eshld_mdls(id)
    CONSTRAINT pii_sships_ns_nn NOT NULL,
  wpn_a integer
    CONSTRAINT pii_sships_wpna_fk REFERENCES pii_wpn_mdls(id),
  wpn_b integer
    CONSTRAINT pii_sships_wpnb_fk REFERENCES pii_wpn_mdls(id),
  wpn_c integer
    CONSTRAINT pii_sships_wpnc_fk REFERENCES pii_wpn_mdls(id)
);


INSERT INTO pii_gamers (email, passwd, nickname, exp)
VALUES ('loller@loldomain.lol', '12345', 'Loller', 10);

INSERT INTO pii_gamers (email, passwd, nickname, exp)
VALUES ('lamer@lala.la', '12345', 'Boss', 0);

INSERT INTO pii_wpn_tps (name)
VALUES ('energy');

INSERT INTO pii_wpn_tps (name)
VALUES ('machine gun');

INSERT INTO pii_wpn_tps (name)
VALUES ('rocket');

INSERT INTO pii_wpn_mdls (name, type, rate, pspeed, damage, nrgcons, distance, range, cd, price)
VALUES ('Rocky', 2, 1, 10, 250, 100, 250, 5, 10, 100);

INSERT INTO pii_wpn_mdls (name, type, rate, pspeed, damage, nrgcons, distance, range, cd, price)
VALUES ('Jabber', 1, 10, 100, 100, 1, 500, 1, 1, 100);

INSERT INTO pii_wpn_mdls (name, type, rate, pspeed, damage, nrgcons, distance, range, cd, price)
VALUES ('Scratch', 0, 5, 1000, 150, 20, 1000, 1, 1, 100);

INSERT INTO pii_egen_mdls (name, max_nrg_lvl, reg_spd, price)
VALUES ('Electron', 1000, 10, 100);

INSERT INTO pii_eshld_mdls (name, max_nrg_lvl, reg_spd, reg_dly, price)
VALUES ('Eel300', 100, 20, 5, 100);

INSERT INTO pii_ngn_mdls (name, max_spd, accl, mbility, price)
VALUES ('Worm', 70, 25, 70, 100);

INSERT INTO pii_ngn_mdls (name, max_spd, accl, mbility, price)
VALUES ('Libra', 100, 10, 50, 100);

INSERT INTO pii_ngn_mdls (name, max_spd, accl, mbility, price)
VALUES ('Bull', 250, 1, 5, 100);

INSERT INTO pii_sship_mdls (name, wpn_slot_num, hp, armor, width, length, price)
VALUES ('Furry mosquito', 1, 100, 10, 120, 100, 100);

INSERT INTO pii_sship_mdls (name, wpn_slot_num, hp, armor, width, length, price)
VALUES ('Red mouse', 2, 120, 10, 200, 300, 100);

INSERT INTO pii_sship_mdls (name, wpn_slot_num, hp, armor, width, length, price)
VALUES ('Huge cucumber', 3, 200, 20, 250, 350, 100);

INSERT INTO pii_sships (owner, mdl, nrg_gen, ngn, nrg_shld, wpn_a, wpn_b, wpn_c)
VALUES (0, 0, 0, 0, 0, 1, 2, 2);

INSERT INTO pii_sships (owner, mdl, nrg_gen, ngn, nrg_shld, wpn_a, wpn_b, wpn_c)
VALUES (1, 1, 0, 1, 0, 2, 1, 1);

CREATE TABLE pii_gsessions(
  id integer 
    CONSTRAINT pii_gsessions_pk PRIMARY KEY
    CONSTRAINT pii_gsessions_pk DEFAULT nextval('pii_gsessions_pk_seq'),
  gamer_id integer
    CONSTRAINT pii_gsessions_gi_u UNIQUE
    CONSTRAINT pii_gsessions_gi_fk REFERENCES pii_gamers(id)
);
