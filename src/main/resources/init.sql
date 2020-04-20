create sequence hibernate_sequence start with 33 increment by 1;

create table entry (
    mission_id bigint not null,
    user_id varchar(255) not null,
    easy integer not null,
    medium integer not null,
    hard integer not null,
    score integer not null,
    primary key (mission_id, user_id),
    foreign key (mission_id) references mission,
    foreign key (user_id) references users
);

create table mission (
    id bigint not null,
    start_date_time timestamp,
    end_date_time timestamp,
    goal_score integer not null,
    primary key (id)
);

create table point (
    date_time timestamp not null,
    user_id varchar(255) not null,
    description varchar(255),
    point integer not null,
    primary key (date_time, user_id),
    foreign key (user_id) references users
);

create table problem (
    problem_id bigint not null,
    title varchar(255),
    difficulty varchar(255),
    title_slug varchar(255),
    front_end_problem_id bigint,
    hide boolean not null,
    primary key (problem_id),
);

create table solved_problem (
    user_id varchar(255) not null,
    problem_id bigint not null,
    mission_id bigint,
    solved_time timestamp,
    updated_time timestamp,
    primary key (problem_id, user_id),
    foreign key (problem_id) references problem,
    foreign key (user_id) references users,
    foreign key (mission_id) references mission
);

create table users (
    id varchar(255) not null,
    telegram_id varchar(255) unique,
    name varchar(255),
    password varchar(255),
    solved_problem_count integer not null,
    primary key (id)
);
