CREATE TABLE user_share_player
(
  id bigserial NOT NULL,
  username_sender text NOT NULL,
  username_receiver text NOT NULL,
  has_viewed boolean NOT NULL DEFAULT false,
  date timestamp without time zone DEFAULT now(),
  player integer NOT NULL,
  message_body text,
  message_media_url text,
  CONSTRAINT user_share_player_pk PRIMARY KEY (id),
  CONSTRAINT user_share_player_fk_player FOREIGN KEY (player)
      REFERENCES player (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_share_player_fk_receiver FOREIGN KEY (username_receiver)
      REFERENCES "user" (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_share_player_fk_sender FOREIGN KEY (username_sender)
      REFERENCES "user" (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE user_friend
(
  sender text NOT NULL,
  receiver text NOT NULL,
  pending boolean NOT NULL,
  accepted boolean,
  CONSTRAINT user_friend_pk PRIMARY KEY (sender, receiver),
  CONSTRAINT user_friend_fk_receiver FOREIGN KEY (receiver)
      REFERENCES "user" (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_friend_fk_sender FOREIGN KEY (sender)
      REFERENCES "user" (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE "user" ADD COLUMN phone text;

ALTER TABLE player_notes ADD COLUMN grade integer;