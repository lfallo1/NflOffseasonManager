create table public.parser_progress
(
  id text not null
    constraint parser_progress_pk
    primary key,
  username text not null
    constraint parser_progress_username_fk
    references public.user(username),
  date timestamp not null,
  started timestamp,
  finished timestamp,
  description text,
  progress integer
);