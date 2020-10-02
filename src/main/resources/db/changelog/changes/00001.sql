CREATE TABLE IF NOT EXISTS "user" (
    id bigint PRIMARY KEY,
    name character varying NOT NULL,
    imageUrl character varying,
    email character varying NOT NULL,
    encoded_password character varying,
    user_type character varying NOT NULL,
    active bool,
    email_verified bool,
    auth_provider character varying NOT NULL,
    auth_provider_id character varying NOT NULL,
    CONSTRAINT email_uq UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS "money" (
    symbol varchar,
    description character varying,
    CONSTRAINT symbol_pk PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS "exchange_rate" (
    id bigint PRIMARY KEY (symbol),
    "timestamp" bigint,
    "from" character varying,
    "to" character varying,
    value double precision,
    CONSTRAINT from_fk FOREIGN KEY ("from")
        REFERENCES "money" (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT to_fk FOREIGN KEY ("to")
        REFERENCES "money" (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


