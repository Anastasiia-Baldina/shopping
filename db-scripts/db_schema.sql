create schema if not exists shop;

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

create table if not exists shop.shop_order (
	order_id	varchar(64)		not null,
	user_id		varchar(64)		not null,
	description	varchar(1024)	not	null,
	amount		numeric(32)		not null,
	status		varchar(64)		not null
);
create unique index if not exists shop_oder_primary_key on shop.shop_order(order_id);
create unique index if not exists shop_oder_user_id_idx on shop.shop_order(user_id);