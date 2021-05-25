create table locations
(
    id serial
        constraint locations_pk
            primary key,
    name text not null
);

create unique index locations_name_uindex
    on locations (name);

create table routes
(
    id serial
        constraint routes_pk
            primary key,
    from_id int
        constraint from_id
            references locations,
    to_id int
        constraint to_id
            references locations,
    cost int

        check ( cost <= 200000 )
);

create table problems
(
    id serial
        constraint problems_pk
            primary key,
    from_id int
        constraint from_id
            references locations,
    to_id int
        constraint to_id
            references locations
);

create table solutions
(
    id int
        constraint solutions_pk
            primary key
        constraint id
            references problems,
    cost int

        check ( cost <= 200000 )
);


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