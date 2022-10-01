create table roles(
	role_id character varying(36) not null,
	role_name character varying(20) not null,
	created_by character varying(20),
	created_date timestamp with time zone,
	lastupdated_by character varying(20),
	lastupdated_date timestamp with time zone,
	
	constraint roles_pkey primary key (role_id),
	constraint roles_ukey unique (role_name)	
);

create sequence users_user_id_seq;
create table users(
	user_id character varying(20) not null default ('U'::text || to_char(nextval('public.users_user_id_seq'::regclass),'000000FM'::text)),
	username character varying(20) not null,
	password character varying(1000) not null,
	email character varying(40) not null,
	created_by character varying(20),
	created_date timestamp with time zone,
	lastupdated_by character varying(20),
	lastupdated_date timestamp with time zone,
	
	constraint users_pkey primary key (user_id),
	constraint users_ukey unique (username),
	constraint users_ukey_2 unique (email)
);

CREATE TABLE IF NOT EXISTS public.users_role
(
    user_id character varying(20) NOT NULL,
    role_id character varying(36) NOT NULL,
	
    CONSTRAINT users_role_pkey PRIMARY KEY (user_id, role_id),
	
    CONSTRAINT users_role_fk1 FOREIGN KEY (role_id)
        REFERENCES public.roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
		
	CONSTRAINT users_role_fk2 FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);