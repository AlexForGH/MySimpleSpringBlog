create table if not exists posts (
        id bigserial primary key,
        title varchar(256) not null,
        imagePath varchar(512) not null,
        likesCount integer not null default 0,
        text varchar(1024) not null,
        tags varchar(1024) not null
);

create table if not exists comments (
        id bigserial primary key,
        post_id bigserial not null references posts(id) on delete cascade,
        content varchar(256) not null
);

