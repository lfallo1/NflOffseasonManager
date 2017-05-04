CREATE TABLE user_login_metadata
(
  id bigserial NOT NULL,
  username text NOT NULL,
  host text NOT NULL,
  addr text NOT NULL,
  last_login timestamp without time zone NOT NULL,
  count integer NOT NULL,
  CONSTRAINT user_login_metadata_pk PRIMARY KEY (id),
  CONSTRAINT user_login_metadata_username_fk FOREIGN KEY (username)
      REFERENCES "user" (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_login_metadata_unique UNIQUE (username, host, addr)
)
WITH (
  OIDS=FALSE
);