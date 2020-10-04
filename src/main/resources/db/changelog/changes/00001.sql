CREATE SEQUENCE IF NOT EXISTS "hibernate_sequence" INCREMENT BY 1 MINVALUE 1;

CREATE TABLE IF NOT EXISTS "money" (
    symbol varchar,
    description character varying,
    CONSTRAINT symbol_pk PRIMARY KEY (symbol)
) ;

CREATE TABLE IF NOT EXISTS "user" (
    id bigserial PRIMARY KEY,
    "user_name" character varying NOT NULL,
    image_url character varying,
    "email" character varying UNIQUE NOT NULL ,
    encoded_password character varying,
    user_type character varying NOT NULL,
    active bool,
    email_verified bool,
    auth_provider character varying NOT NULL,
    auth_provider_id character varying NOT NULL
) ;


CREATE TABLE IF NOT EXISTS "exchange_rate" (
    id bigserial PRIMARY KEY,
    "timestamp" bigint,
    "base_money_symbol" character varying,
    "exchange_money_symbol" character varying,
    value double precision,
    CONSTRAINT from_fk FOREIGN KEY ("base_money_symbol")
        REFERENCES "money" (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT to_fk FOREIGN KEY ("exchange_money_symbol")
        REFERENCES "money" (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
) ;


