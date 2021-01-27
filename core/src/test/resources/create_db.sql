create table certificates
(
    id               serial  not null,
    name             text    not null,
    description      text,
    price            numeric not null,
    duration         integer not null,
    create_date      timestamp with time zone default now(),
    last_update_date timestamp with time zone default now(),
    constraint gift_certificate_pkey
        primary key (id)
);

create table tags
(
    id   serial not null,
    name text   not null,
    constraint tags_pkey
        primary key (id)
);

create table certificates_tags
(
    gift_certificates_id integer not null,

    tags_id              integer not null,
    constraint gift_certificates_tags_gift_certificates_id_fkey foreign key (gift_certificates_id)
        references certificates (id),
    constraint gift_certificates_tags_tags_id_fkey foreign key (tags_id)
        references tags (id)
);

create unique index "UI_user_to_site_site_id_user_id"
    on certificates_tags (gift_certificates_id, tags_id);