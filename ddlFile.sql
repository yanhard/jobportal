create table applications (id bigserial not null, cover_letter varchar(255) not null, resume varchar(255) not null, job_id bigint, user_id bigint, primary key (id));
create table companies (id bigserial not null, address varchar(255) not null, name varchar(255) not null, primary key (id));
create table jobs (id bigserial not null, category varchar(255) not null, description varchar(255) not null, title varchar(255) not null, company_id bigint, primary key (id));
create table users (id bigserial not null, cover_letter_path varchar(255), email varchar(255) not null, first_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, resume_path varchar(255), primary key (id));
alter table if exists applications add constraint FK65weib1lru9dkrbto5pv389vi foreign key (job_id) references jobs;
alter table if exists applications add constraint FKfsfqljedcla632u568jl5qf3w foreign key (user_id) references users;
alter table if exists jobs add constraint FKrtmqcrktb6s7xq8djbs2a2war foreign key (company_id) references companies;
