create or replace function setupdata()
returns boolean
language 'plpgsql'
as $body$
    BEGIN
        insert into bookedseat(showId, seatid, status)
        values (1,1,'f'), (1, 2, 'f');
        return 't';
    end;
$body$
