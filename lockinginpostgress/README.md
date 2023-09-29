# Locks in Database (Reference Postgresql)

What is a Transaction?
A transaction will follow ACID properties
1. Atomicity (Always) -- Responsiblity of Transaction Manager
2. Consistency () -- Application Developer
3. Isolation () -- Concurrency Control Manager 
4. Durability (Always) -- recovery software

To **START TRANSACTION** we just have to mention 
1. Begin
2. Commit
3. End
4. Savepoint (new transaction starts After this point)
5. Rollback (new transaction starts After this point)

Why Do we need to handle Concurrency?
1. Dirty Read (WR conflict) [here](https://www.tutorialspoint.com/what-is-dirty-read-in-a-transaction-dbms) :- Data of a not yet finished transaction are read by another transaction
   1. Example: T1-Read(A) , T1-Update(A=A-100), T2-Read(A), T2-Update(A=A+100), T1-Rollback, T2-Commit
2. Lost Update(WW conflict) [here](https://www.tutorialspoint.com/explain-the-main-problems-in-concurrency-control-dbms) :-> Two transaction a value same time and both of them do some operation and commits. In this case one commit will be overwritten by other.
3. Unrepeatable read (RR conflict): [all are here](https://www.geeksforgeeks.org/concurrency-problems-in-dbms-transactions/) :-> The unrepeatable problem occurs when two or more read operations of the same transaction read different values of the same variable.
4. Phantom Read :-> Here same query results in different number of rows becoz some other transaction inserted a new row which satisfies our main query condition.

**Locks Conflict**: https://infocenter.sybase.com/help/index.jsp?topic=/com.sybase.help.sqlanywhere.12.0.1/dbusage/transact-s-4726068.html

**Isolation Level**: (https://infocenter.sybase.com/help/index.jsp?topic=/com.sybase.help.sqlanywhere.12.0.1/dbusage/insert-how-transact.html)
Read This Mandatory: https://www.postgresql.org/docs/10/transaction-iso.html

1. read uncommitted: no locks, will read uncommitted changes. (All issues are possible)
2. read committed: It will put read lock on the selected rows. This guarantee the data read is reading the committed changes.
   1. How does this guarantee it will read committed changes? read locks conflicts with write lock hence if the row is being written query will be blocked until write lock is removed, then it will put read lock and returns the data OR first it will read then T2 will write new values.
   2. This takes care of Dirty Read.
3. repeatable read: Similar to above read committed, only difference is that read-committed will release read lock once the cursur move from that row but this one will held on to the lock until whole transaction is completed.
   1. This is considered as highest level of locking.
   2. This resolves Dirty Read and Unrepeatable read. (Not Lost Update Reproducible in current project).
   3. In this if some other 
   4. In Postgres: This level give execption if update is called concurrently
      1. ERROR: could not serialize access due to concurrent update
4. serializable: This makes all transaction serializable, hence no issues
   1. This does not execute concurrently.
   2. Resolves Dirty Read, Unrepeatable read, Phantom Read and Lost Update.
   3. To solve this we have **SNAPSHOT**
      4. In SNAPSHOT:-> we don't put any lock instead we take snapshot of the system at each start of transaction and is really fast

To understand how isolation level works in **select query**: https://infocenter.sybase.com/help/index.jsp?topic=/com.sybase.help.sqlanywhere.12.0.1/dbusage/insert-how-transact.html

In a Query we can **set Isolation Level** using this command:

  **SET TRANSACTION ISOLATION LEVEL ......**
  {READ UNCOMMITTED}/{READ COMMITTED}/{REPEATABLE READ}/{SERIALIZABLE

By **Default**: Most of the DBMS uses Lowest level of isolation which means **read committed**.

# [Get Row Level Locks](https://www.postgresql.org/docs/current/explicit-locking.html#LOCKING-ROWS)
1. For Update -> Exclusive lock
2. For No Key Update -> (I think Exclusive lock but only for primary key)
3. For Share -> Read lock
4. For Key Share -> Read lock but only for the primary key

[Example](https://levelup.gitconnected.com/preventing-data-inconsistencies-in-mysql-strategies-for-avoiding-lost-updates-cfdb04107f7c): SELECT * FROM inventory FOR UPDATE;
UPDATE inventory SET quantity = 6 WHERE item = A; COMMIT;

To Ignore Locks Use
1. NOWAIT -> if can't get lock immediately then raise exception (https://stackoverflow.com/questions/13248239/when-should-i-use-for-update-nowait-in-cursors)
2. Skip Locked -> This will skip that row and move forward





