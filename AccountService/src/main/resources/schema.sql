create table Account (
     id uuid primary key ,
     user_name VARCHAR unique not null ,
     password VARCHAR not null
);