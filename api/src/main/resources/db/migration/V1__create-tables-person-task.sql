CREATE TABLE person(
	id bigint not null auto_increment,
	person_name varchar(200) not null,
	department varchar(200) not null,
    
    primary key(id)
);

CREATE TABLE task(
	id bigint not null auto_increment,
	person_id bigint not null,
	title varchar(200) not null,
	description varchar(1000),
	deadline date not null,
	duration bigint not null,
	isDone boolean not null default false,
	
	primary key(id),
	
	constraint fk_person 
		foreign key (person_id) references person (id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);