create database certificates;

create table if not exists certificates
(
    id               serial  not null
        constraint gift_certificate_pkey
            primary key,
    name             text    not null,
    description      text,
    price            integer not null         default 0,
    duration         integer not null,
    create_date      timestamp with time zone default now(),
    last_update_date timestamp with time zone default now()
);

create table if not exists tags
(
    id   serial not null
        constraint tags_pkey
            primary key,
    name text   not null
);

create unique index if not exists tags_name_uindex
    on tags (name);

create table if not exists certificates_tags
(
    gift_certificates_id integer not null
        constraint gift_certificates_tags_gift_certificates_id_fkey
            references certificates,
    tags_id              integer not null
        constraint gift_certificates_tags_tags_id_fkey
            references tags
);

create unique index if not exists "UI_user_to_site_site_id_user_id"
    on certificates_tags (gift_certificates_id, tags_id);