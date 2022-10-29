create database gizlo_test;

create table customers (
	customer_id int identity,
	dni varchar(10),
	name varchar(255),
	surname varchar(255),
	primary key (customer_id)
);

insert
	into
	customers (dni,
	name,
	surname)
values ('0956257497',
'LUIS JEANPIER',
'MENDOZA NAVARRO');

insert
	into
	customers (dni,
	name,
	surname)
values ('0947663545',
'KALEB DAVID',
'CHARA TOALA');

insert
	into
	customers (dni,
	name,
	surname)
values ('084872363',
'NADIA ESPERANZA',
'MENDOZA CEDEÑO');


create procedure sp_select_customers
	as
	select
	*
from
	customers;

create procedure sp_select_customers_by_dni (@dni varchar(10))
	as
	select
	*
from
	customers
where
	dni = @dni;

{ call sp_select_customers };

{ call sp_select_customers_by_dni(@dni = '0956257497') };

drop procedure dbo.sp_select_customers;
drop procedure dbo.sp_select_customers_by_dni;

select * from customers;

select * from customers where dni = '0956257497';