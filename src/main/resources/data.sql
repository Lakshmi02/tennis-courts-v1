insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');
insert into guest(id, name) values(null, 'User3');
insert into guest(id, name) values(null, 'User4');
insert into guest(id, name) values(null, 'User5');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'TennisCourt2');
insert into tennis_court(id, name) values(null, 'TennisCourt3');
insert into tennis_court(id, name) values(null, 'TennisCourt4');

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-14T18:00:00.0', '2021-11-14T19:00:00.0', 1);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-14T19:00:00.0', '2021-11-14T20:00:00.0', 1);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-14T20:00:00.0', '2021-11-14T21:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-15T18:00:00.0', '2021-11-15T19:00:00.0', 1);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-15T19:00:00.0', '2021-11-15T20:00:00.0', 1);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-15T20:00:00.0', '2021-11-15T21:00:00.0', 1);
 
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-16T18:00:00.0', '2021-11-16T19:00:00.0', 2);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-16T19:00:00.0', '2021-11-16T20:00:00.0', 2);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-16T20:00:00.0', '2021-11-16T21:00:00.0', 2);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-17T18:00:00.0', '2021-11-17T19:00:00.0', 3);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-17T19:00:00.0', '2021-11-17T20:00:00.0', 3);
insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2021-11-17T20:00:00.0', '2021-11-17T21:00:00.0', 3);