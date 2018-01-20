create table "user_role"
(
  username text not null
    constraint user_role_username_fk
    references public.user,
  role text not null
);

insert into user_role (username, role)
select username, 'ROLE_USER' from public.user;

insert into user_role(username, role) VALUES ('lfallo1', 'ROLE_ADMIN');