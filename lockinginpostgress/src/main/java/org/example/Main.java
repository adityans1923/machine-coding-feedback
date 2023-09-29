package org.example;

import org.example.bookingsystem.DatabaseUtil;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class MyThread extends Thread {
    Connection conn;
    MyThread(int x, Connection conn) {
        super(Integer.toString(x));
        this.conn = conn;
        if (this.conn == null)
            this.conn = DatabaseUtil.getConnection();
    }

    @Override
    public void run() {
        try {
//            DatabaseUtil.runInsert(this.conn, 1, ThreadLocalRandom.current().nextInt(2) + 1);
            DatabaseUtil.runInsert(this.conn, 1, 1);
        } catch (SQLException e) {
            System.out.println(currentThread().getName() + " --> " + e.getMessage());
        } finally {
            try {
                this.conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    static void copy(InputStream in, OutputStream out) throws IOException {
        while (true) {
            int c = in.read();
            if (c == -1) break;
            out.write((char)c);
        }
    }
    private static void executeSqlFile(String scriptName) {
        try {
            Runtime rt = Runtime.getRuntime();
            String executeSqlCommand = "psql -U aditya.singh -d bookingsystem -h localhost -f ./lockinginpostgress/src/main/plsql/" +scriptName;
//            String executeSqlCommand = "ls -al ./lockinginpostgress/src/main/plsql/";
            Process p = rt.exec(executeSqlCommand);
            copy(p.getErrorStream(), System.out);
            copy(p.getInputStream(), System.out);
            int exitVal = p.waitFor();
            System.out.println("Exited with error code " + exitVal);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
    public static void main(String[] args) throws SQLException, InterruptedException {
        executeSqlFile("main.sql");
//        executeSqlFile("setupdata.sql");
//        Connection conn = DatabaseUtil.getConnection();
//        DatabaseUtil.runInit(conn);
        List<Thread> list = new ArrayList<>();
        int n = 80;
        for (int i=0;i<n;i++) {
            list.add(new MyThread(i,null));
        }
        long start = System.currentTimeMillis();
        for (int i=0;i<n;i++) {
            list.get(i).start();
        }

        for (int i=0;i<n;i++)
                list.get(i).join();
        System.out.println("Total time: " + Long.toString(System.currentTimeMillis() - start));
        System.out.println("System exited");
    }
}