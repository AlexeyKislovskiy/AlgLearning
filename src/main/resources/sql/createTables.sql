create table cuber
(
    id                serial primary key,
    nickname          varchar(16)                       not null unique,
    email             varchar(256),
    password          varchar(60)                       not null,
    registration_date date    default current_timestamp not null,
    visited_days      int     default 1                 not null,
    visited_days_row  int     default 1                 not null,
    last_visited      date    default current_timestamp not null,
    status            varchar default 'user'            not null
);
insert into cuber(id, nickname, password)
values (-1, 'unknown', 'unknown');
alter table cuber
    add constraint check_status
        check (status in ('user', 'moderator', 'admin'));
create table cube
(
    id          serial primary key,
    name        varchar unique not null,
    description varchar unique not null,
    image       varchar        not null
);
create table method
(
    id                   serial primary key,
    name                 varchar unique not null,
    cube_id              int            not null,
    number_of_situations int            not null,
    description          text           not null,
    learning             int default 0  not null,
    learned              int default 0  not null,
    image                varchar        not null,
    foreign key (cube_id) references cube (id)
);
create table situation
(
    id                serial primary key,
    name              varchar not null,
    method_id         int     not null,
    mirror_id         int     not null,
    reverse_id        int     not null,
    mirror_reverse_id int     not null,
    image             varchar not null,
    foreign key (method_id) references method (id),
    foreign key (mirror_id) references situation (id),
    foreign key (reverse_id) references situation (id),
    foreign key (mirror_reverse_id) references situation (id)
);
create table scramble
(
    situation_id int     not null,
    text         varchar not null,
    foreign key (situation_id) references situation (id),
    primary key (situation_id, text)
);
create table algorithm
(
    id             serial primary key,
    text           varchar           not null,
    situation_id   int               not null,
    number_of_uses int  default 0    not null,
    verified       bool default true not null,
    add_cuber_id   int  default 1    not null,
    foreign key (situation_id) references situation (id),
    unique (text, situation_id, add_cuber_id),
    foreign key (add_cuber_id) references cuber (id) on delete set default
);
create table cuber_learning_method
(
    cuber_id  int not null,
    method_id int not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (method_id) references method (id),
    primary key (cuber_id, method_id)
);
create table cuber_learned_method
(
    cuber_id  int not null,
    method_id int not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (method_id) references method (id),
    primary key (cuber_id, method_id)
);
create table cuber_learning_situation
(
    cuber_id      int                   not null,
    situation_id  int                   not null,
    last_interval float8                not null,
    next_repeat   date                  not null,
    status        varchar default 'new' not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (situation_id) references situation (id),
    primary key (cuber_id, situation_id),
    constraint check_status check (status in ('new', 'forgot', 'repeat', 'await'))
);
create table cuber_training_situation
(
    cuber_id     int not null,
    situation_id int not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (situation_id) references situation (id),
    primary key (cuber_id, situation_id)
);
create table cuber_learned_situation
(
    cuber_id     int not null,
    situation_id int not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (situation_id) references situation (id),
    primary key (cuber_id, situation_id)
);
create table cuber_uses_algorithm
(
    cuber_id     int not null,
    algorithm_id int not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    foreign key (algorithm_id) references algorithm (id) on delete cascade,
    primary key (cuber_id, algorithm_id)
);
create table cuber_statistics
(
    cuber_id       int                            not null,
    date           date default current_timestamp not null,
    number_forgot  int  default 0                 not null,
    number_repeat  int  default 0                 not null,
    number_new     int  default 0                 not null,
    number_trained int  default 0                 not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    primary key (cuber_id, date)
);
create table cuber_settings
(
    cuber_id   int     not null,
    setting_id int     not null,
    value      varchar not null,
    foreign key (cuber_id) references cuber (id) on delete cascade,
    primary key (cuber_id, setting_id)
);
create table cuber_session
(
    session_id varchar(60) primary key,
    cuber_id   int  not null,
    remember   bool not null,
    foreign key (cuber_id) references cuber (id) on delete cascade
);