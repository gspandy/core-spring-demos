drop table T_ACCOUNT if exists;

create table T_ACCOUNT (ID integer identity primary key, NUMBER varchar(9), NAME varchar(50) not null, unique(NUMBER));

