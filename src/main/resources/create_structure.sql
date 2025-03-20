create role userdata with password '123456';
create database data owner userdata;
ALTER ROLE userdata LOGIN;