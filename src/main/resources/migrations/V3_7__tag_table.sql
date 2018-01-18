CREATE TABLE public.tag
(
  id bigserial NOT NULL,
  name text,
  CONSTRAINT tag_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

INSERT INTO tag (name) VALUES ('huge hitter');
INSERT INTO tag (name) VALUES ('great speed');
INSERT INTO tag (name) VALUES ('great balance');
INSERT INTO tag (name) VALUES ('great agility');
INSERT INTO tag (name) VALUES ('hand catcher');
INSERT INTO tag (name) VALUES ('body control');
INSERT INTO tag (name) VALUES ('jumping');
INSERT INTO tag (name) VALUES ('strong arm');
INSERT INTO tag (name) VALUES ('accurate');
INSERT INTO tag (name) VALUES ('vision');
INSERT INTO tag (name) VALUES ('powerful runner');
INSERT INTO tag (name) VALUES ('elusive');
INSERT INTO tag (name) VALUES ('good ball skills');
INSERT INTO tag (name) VALUES ('ballhawk');
INSERT INTO tag (name) VALUES ('playmaker');
INSERT INTO tag (name) VALUES ('strong legs');
INSERT INTO tag (name) VALUES ('great pass blocker');
INSERT INTO tag (name) VALUES ('road grader');
INSERT INTO tag (name) VALUES ('great strength');
INSERT INTO tag (name) VALUES ('speed rusher');
INSERT INTO tag (name) VALUES ('power rusher');
INSERT INTO tag (name) VALUES ('interior penetration');
INSERT INTO tag (name) VALUES ('run defender');
INSERT INTO tag (name) VALUES ('good tackler');
INSERT INTO tag (name) VALUES ('smart');

CREATE TABLE public.player_notes_tag
(
  id bigserial NOT NULL,
  note integer NOT NULL,
  tag integer NOT NULL,
  CONSTRAINT player_notes_tag_pk PRIMARY KEY (id),
  CONSTRAINT player_notes_tag_fk_note FOREIGN KEY (note)
      REFERENCES player_notes (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);