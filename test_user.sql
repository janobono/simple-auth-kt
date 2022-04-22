insert into simple_auth_user(id, username, password, enabled)
values (nextval('sq_simple_auth_user'), 'jimbop', '$2a$12$wWa8P0wPNm1.JLTP5Yavnu0xnp1tMT4Bqt2NI7eJJmyIM671MI0ki', true);

insert into simple_auth_user_attribute(user_id, key, value)
values (currval('sq_simple_auth_user'), 'email', 'jimbo.pytlik@test.com');
insert into simple_auth_user_attribute(user_id, key, value)
values (currval('sq_simple_auth_user'), 'given_name', 'Jimbo');
insert into simple_auth_user_attribute(user_id, key, value)
values (currval('sq_simple_auth_user'), 'family_name', 'Pytlik');
insert into simple_auth_user_attribute(user_id, key, value)
values (currval('sq_simple_auth_user'), 'app_code', 'simple-123');

insert into simple_auth_user_authority(user_id, authority_id)
values (currval('sq_simple_auth_user'), 1);
insert into simple_auth_user_authority(user_id, authority_id)
values (currval('sq_simple_auth_user'), 2);
