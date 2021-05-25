create table if not exists locations
(
    id serial not null
    constraint locations_pk
    primary key,
    name text not null
);

alter table locations owner to postgres;

create unique index if not exists locations_name_uindex
	on locations (name);

create table if not exists routes
(
    id serial not null
    constraint routes_pk
    primary key,
    from_id integer
    constraint from_id
    references locations,
    to_id integer
    constraint to_id
    references locations,
    cost integer
    constraint routes_cost_check
    check (cost <= 200000)
    );

alter table routes owner to postgres;

create table if not exists problems
(
    id serial not null
    constraint problems_pk
    primary key,
    from_id integer
    constraint from_id
    references locations,
    to_id integer
    constraint to_id
    references locations
);

alter table problems owner to postgres;

create table if not exists solutions
(
    id integer not null
    constraint solutions_pk
    primary key
    constraint id
    references problems,
    cost integer
    constraint solutions_cost_check
    check (cost <= 200000)
    );

alter table solutions owner to postgres;

insert into locations (name) values ('gdansk');
insert into locations (name) values ('bydgoszcz');
insert into locations (name) values ('torun');
insert into locations (name) values ('warszawa');

insert into routes (from_id, to_id, cost) values (1, 2, 1);
insert into routes (from_id, to_id, cost) values (1, 3, 3);
insert into routes (from_id, to_id, cost) values (2, 1, 1);
insert into routes (from_id, to_id, cost) values (2, 3, 1);
insert into routes (from_id, to_id, cost) values (2, 4, 4);
insert into routes (from_id, to_id, cost) values (3, 1, 3);
insert into routes (from_id, to_id, cost) values (3, 2, 1);
insert into routes (from_id, to_id, cost) values (3, 4, 1);
insert into routes (from_id, to_id, cost) values (4, 2, 4);
insert into routes (from_id, to_id, cost) values (4, 3, 1);

insert into problems (from_id, to_id) VALUES (1,4);
insert into problems (from_id, to_id) VALUES (2,4);