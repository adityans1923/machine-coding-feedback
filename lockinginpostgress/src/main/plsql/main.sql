CREATE OR REPLACE FUNCTION public.init()
    RETURNS Boolean
    LANGUAGE 'plpgsql'
As $Body$
    declare
        dbExist boolean := false;
        tableExist boolean := false;
    begin

        select exists (SELECT 1
                       FROM pg_database WHERE datname = 'bookingsystem')
        into dbExist;

        if not dbExist then
            CREATE DATABASE bookingsystem;
        end if;

        select exists (select 1 from pg_tables where tablename = 'bookedseat')
        into tableExist;

        if not tableExist then
            create table bookedseat (
                                        id serial not null primary key,
                                        seatId int not null,
                                        showId int not null,
                                        status boolean not null
            );
        end if;

        return true;
    end
$Body$;

DROP FUNCTION IF EXISTS public.bookshowseat(integer, integer);

CREATE OR REPLACE FUNCTION public.bookshowseat(
    showno integer,
    seatno integer)
    RETURNS boolean
    LANGUAGE 'plpgsql'
AS $BODY$
declare
    curr_status boolean;
begin
    Select status
    into curr_status
    from BookedSeat as bs
    where bs.seatId = seatno and bs.showId= showno
    for update;

    IF curr_status THEN
        RAISE EXCEPTION 'show seat is already booked';
    END IF;

    update bookedseat as bs
    set status='TRUE'
    where bs.seatId=seatno and bs.showId=showno;
    return 't';
end;
$BODY$;
