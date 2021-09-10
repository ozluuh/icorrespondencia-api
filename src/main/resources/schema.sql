CREATE TYPE "person_type" AS ENUM (
  'F',
  'J'
);

CREATE TYPE "phone_type" AS ENUM (
  'CELLPHONE',
  'LANDLINE'
);

CREATE TYPE "role_type" AS ENUM (
  'ROLE_USER',
  'ROLE_ADMIN'
);

CREATE TABLE "person" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar(255) NOT NULL,
  "public_id" uuid NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "excluded_at" timestamp,
  "active" boolean NOT NULL DEFAULT true,
  "person_type" person_type NOT NULL
);

CREATE TABLE "users" (
  "id" bigint NOT NULL,
  "email" varchar(255) UNIQUE,
  "username" varchar(20) UNIQUE NOT NULL,
  "password" varchar(128) NOT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "townhouse" (
  "id" bigint NOT NULL,
  "email" varchar(255) UNIQUE,
  "cnpj" varchar(18) UNIQUE NOT NULL,
  "site" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "phone" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "townhouse_id" bigint NOT NULL,
  "phone" varchar(11) NOT NULL,
  "phone_type" phone_type NOT NULL DEFAULT 'LANDLINE',
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "excluded_at" timestamp
);

CREATE TABLE "city" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar(70) NOT NULL,
  "state" char(2) NOT NULL
);

CREATE TABLE "district" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "city_id" bigint NOT NULL,
  "name" varchar(70) NOT NULL
);

CREATE TABLE "street" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "district_id" bigint NOT NULL,
  "zipcode" char(8) UNIQUE NOT NULL,
  "name" varchar(255) NOT NULL
);

CREATE TABLE "address" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "townhouse_id" bigint NOT NULL,
  "street_id" bigint NOT NULL,
  "street_number" varchar(5),
  "complement" varchar(50),
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "excluded_at" timestamp
);

CREATE TABLE "block" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "townhouse_id" bigint NOT NULL,
  "name" varchar(25) NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "excluded_at" timestamp
);

CREATE TABLE "room" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "block_id" bigint NOT NULL,
  "number" smallint NOT NULL,
  "floor" varchar(3) NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "excluded_at" timestamp
);

CREATE TABLE "delivery" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "room_id" bigint NOT NULL,
  "delivery_date" timestamp NOT NULL,
  "read" boolean NOT NULL DEFAULT false,
  "read_at" timestamp
);

CREATE TABLE "user_role" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "user_id" bigint NOT NULL,
  "type" role_type NOT NULL DEFAULT 'ROLE_USER',
  "townhouse_id" bigint NOT NULL,
  "room_id" bigint
);

ALTER TABLE "users" ADD FOREIGN KEY ("id") REFERENCES "person" ("id");

ALTER TABLE "townhouse" ADD FOREIGN KEY ("id") REFERENCES "person" ("id");

ALTER TABLE "phone" ADD FOREIGN KEY ("townhouse_id") REFERENCES "townhouse" ("id");

ALTER TABLE "district" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id");

ALTER TABLE "street" ADD FOREIGN KEY ("district_id") REFERENCES "district" ("id");

ALTER TABLE "address" ADD FOREIGN KEY ("townhouse_id") REFERENCES "townhouse" ("id");

ALTER TABLE "address" ADD FOREIGN KEY ("street_id") REFERENCES "street" ("id");

ALTER TABLE "block" ADD FOREIGN KEY ("townhouse_id") REFERENCES "townhouse" ("id");

ALTER TABLE "room" ADD FOREIGN KEY ("block_id") REFERENCES "block" ("id");

ALTER TABLE "delivery" ADD FOREIGN KEY ("room_id") REFERENCES "room" ("id");

ALTER TABLE "user_role" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_role" ADD FOREIGN KEY ("townhouse_id") REFERENCES "townhouse" ("id");

ALTER TABLE "user_role" ADD FOREIGN KEY ("room_id") REFERENCES "room" ("id");

