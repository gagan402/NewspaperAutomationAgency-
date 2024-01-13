create database PaperMaster;
use PaperMaster;
create table papers(pname varchar(30) primary key,pprice float);
select * from papers;

create table hawker(hname varchar(30) primary key,mobile varchar(10),dob varchar(30),address varchar(30),area varchar(40),picpath varchar(30));
alter table hawker modify column picpath varchar(100);
select * from hawker;


create table customer(mobile varchar(10) primary key,cname varchar(30), spapers varchar(100),sprices varchar(100),area varchar(100),hawker varchar(30),
email varchar(100), address varchar(100), dos date);
select * from customer;

create table billgen(mobile varchar(10),dateFrom date,dateTo date,bill int, billStatus int);
select * from billgen;