CREATE TABLE art
(
    key VARCHAR(10) NOT NULL,
    item BYTEA NOT NULL,
    CONSTRAINT art_pk PRIMARY KEY (key)
);

CREATE TABLE artists
(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    bio TEXT,
    CONSTRAINT artists_pk PRIMARY KEY (id)
);

CREATE TABLE artisttoart
(
    artist_id INT NOT NULL,
    art_key VARCHAR(10) NOT NULL,
    CONSTRAINT artists_fk FOREIGN KEY (artist_id) REFERENCES artists (id),
    CONSTRAINT art_fk FOREIGN KEY (art_key) REFERENCES art (key),
    CONSTRAINT uq_artisttoart UNIQUE (artist_id, art_key)
);

CREATE TABLE events
(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    from_date TIMESTAMP NOT NULL,
    to_date TIMESTAMP NOT NULL,
    venue VARCHAR(50) NOT NULL,
    highlight BOOLEAN NOT NULL,
    CONSTRAINT events_pk PRIMARY KEY (id)
);

CREATE TABLE eventsart
(
    event_id INT NOT NULL,
    art_key VARCHAR(10) NOT NULL,
    CONSTRAINT events_fk FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT art_fk FOREIGN KEY (art_key) REFERENCES art (key),
    CONSTRAINT uq_eventsart UNIQUE (event_id, art_key)
);

CREATE TABLE shopitems
(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    artist_id INT NOT NULL,
    description TEXT,
    price DECIMAL(10, 5) NOT NULL,
    CONSTRAINT shopitems_pk PRIMARY KEY (id),
    CONSTRAINT artists_fk FOREIGN KEY (artist_id) REFERENCES artists (id)
);