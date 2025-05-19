BEGIN;

DROP TABLE users, collection_metadata, coordinates, chapters, space_marines CASCADE ;


CREATE TABLE IF NOT EXISTS users (
    login VARCHAR(50) PRIMARY KEY,
    password_hash CHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS collection_metadata (
    singleton_id BOOLEAN PRIMARY KEY DEFAULT TRUE,
    creation_date TIMESTAMP WITH TIME ZONE,
    CONSTRAINT single_row CHECK (singleton_id)
);

CREATE TABLE IF NOT EXISTS coordinates (
    coord_id SERIAL PRIMARY KEY,
    x DOUBLE PRECISION,
    y Real NOT NULL CHECK (y > -501)
);

CREATE TABLE IF NOT EXISTS chapters (
    chapter_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL CHECK (name <> ''),
    world VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS space_marines (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL REFERENCES users(login) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL CHECK (name <> ''),
    coord_id INTEGER NOT NULL REFERENCES coordinates(coord_id),
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    health Real NOT NULL CHECK (health > 0),
    loyal BOOLEAN,
    weapon_type VARCHAR(20) CHECK (weapon_type IN ('BOLTGUN', 'MELTAGUN', 'BOLT_RIFLE', 'COMBI_PLASMA_GUN')),
    melee_weapon VARCHAR(20) CHECK (melee_weapon IN ('CHAIN_SWORD', 'POWER_SWORD', 'CHAIN_AXE', 'LIGHTING_CLAW', 'POWER_FIST')),
    chapter_id INTEGER REFERENCES chapters(chapter_id)
);

CREATE INDEX idx_marines_login ON space_marines(login);

COMMIT;