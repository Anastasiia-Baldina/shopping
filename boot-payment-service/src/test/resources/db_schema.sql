create schema if not exists shop;

set schema shop;

create table if not exists shop.account (
	user_id		varchar(64)		not null,
	balance		numeric(32)		not null
);
create unique index if not exists account_primary_key on shop.account(user_id);

create table if not exists shop.payment (
	order_id	varchar(64)	not null,
	user_id		varchar(64)	not null,
	amount		numeric(32)	not null
);
create unique index if not exists payment_primary_key on shop.payment(order_id);
