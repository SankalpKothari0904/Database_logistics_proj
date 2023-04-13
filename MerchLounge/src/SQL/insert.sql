insert into customer values(101,'Sankalp','IIITB, Electronic City','1112223334');
insert into customer values(102,'Siddharth','IIITB, Electronic City','1122334455');
insert into customer values(103,'Srinivasan','IIITB, Electronic City','1010101010');

insert into product values(9090,'Samsung A71',25000.00);
insert into product values(9029,'Pencil Stand',600.00);
insert into product values(9038,'Boat Speaker',3500.00);
insert into product values(9084,'Iphone 13 Pro',96000.00);
insert into product values(9070,'Hair wax',1400.00);

insert into deliveryAgent values(501,'6655544444');
insert into deliveryAgent values(502,'9090909090');
insert into deliveryAgent values(503,'9898980000');

insert into seller values(904,'Vikas','26, Chennai','9876543210');
insert into seller values(905,'Neha','B1, Kolkata','1234567890');
insert into seller values(907,'Ansh','Mayur Vihar, Delhi','9080706050');

insert into order values(4001,102,'23-03-2023');
insert into order values(4002,103,'01-04-2023');
insert into order values(4003,102,'14-04-2023');
insert into order values(4004,102,'19-04-2023');

insert into orderDesc values(1,9029,4001,2,'D','Delivered');
insert into orderDesc values(2,9038,4002,1,'D','Delivered');
insert into orderDesc values(3,9070,4002,1,'D','Delivered');
insert into orderDesc values(4,9084,4003,3,'D','Packed');
insert into orderDesc values(5,9038,4003,'R','Returned');
insert into orderDesc values(6,9090,4004,'D','Shipped');

insert into inventory values(1, 9090, 904, 10);
insert into inventory values(2, 9038, 904, 15);
insert into inventory values(3, 9084, 904, 10);
insert into inventory values(4, 9029, 905, 100);
insert into inventory values(5, 9070, 907, 20);

insert into shipments values (2,502,4003,"Road");
insert into shipments values (3,502,4004,"Rail");