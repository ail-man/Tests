package com.ail.home.fortest.xa;

import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.naming.NamingException;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.SQLException;

class XASample {
    XADataSource xaDS1;
    XADataSource xaDS2;
    XAConnection xaconn1;
    XAConnection xaconn2;
    XAResource xares1;
    XAResource xares2;
    Connection conn1;
    Connection conn2;

    public static void main(String args[]) throws java.sql.SQLException {
        XASample xat = new XASample();
        xat.runThis(args);
    }

    // As the transaction manager, this program supplies the global
    // transaction ID and the branch qualifier. The global
    // transaction ID and the branch qualifier must not be
    // equal to each other, and the combination must be unique for
    // this transaction manager.
    public void runThis(String[] args) {
        byte[] gtrid = new byte[]{0x44, 0x11, 0x55, 0x66};
        byte[] bqual = new byte[]{0x00, 0x22, 0x00};
        int rc1 = 0;
        int rc2 = 0;

        try {

            javax.naming.InitialContext context = new javax.naming.InitialContext();

            // Note that javax.sql.XADataSource is used instead of a specific
            // driver implementation such as com.ibm.db2.jcc.DB2XADataSource.
            xaDS1 = (XADataSource) context.lookup("checkingAccounts");
            xaDS2 = (XADataSource) context.lookup("savingsAccounts");

            // The XADatasource contains the user ID and password.
            // Get the XAConnection object from each XADataSource
            xaconn1 = xaDS1.getXAConnection();
            xaconn2 = xaDS2.getXAConnection();

            // Get the java.sql.Connection object from each XAConnection
            conn1 = xaconn1.getConnection();
            conn2 = xaconn2.getConnection();

            // Get the XAResource object from each XAConnection
            xares1 = xaconn1.getXAResource();
            xares2 = xaconn2.getXAResource();

            // Create the Xid object for this distributed transaction.
            // This example uses the com.ibm.db2.jcc.DB2Xid implementation
            // of the Xid interface. This Xid can be used with any JDBC driver
            // that supports JTA.
            Xid xid1 = new MysqlXid(gtrid, bqual, 100);

            // Start the distributed transaction on the two connections.
            // The two connections do NOT need to be started and ended together.
            // They might be done in different threads, along with their SQL operations.
            xares1.start(xid1, XAResource.TMNOFLAGS);
            xares2.start(xid1, XAResource.TMNOFLAGS);


            // …
            // Do the SQL operations on connection 1.
            // Do the SQL operations on connection 2.
            // …


            // Now end the distributed transaction on the two connections.
            xares1.end(xid1, XAResource.TMSUCCESS);
            xares2.end(xid1, XAResource.TMSUCCESS);

            // If connection 2 work had been done in another thread,
            // a thread.join() call would be needed here to wait until the
            // connection 2 work is done.

            try {
                // Now prepare both branches of the distributed transaction.
                // Both branches must prepare successfully before changes
                // can be committed.
                // If the distributed transaction fails, an XAException is thrown.
                rc1 = xares1.prepare(xid1);
                if (rc1 == XAResource.XA_OK) {
                    // Prepare was successful. Prepare the second connection.
                    rc2 = xares2.prepare(xid1);
                    if (rc2 == XAResource.XA_OK) {
                        // Both connections prepared successfully and neither was read-only.
                        xares1.commit(xid1, false);
                        xares2.commit(xid1, false);
                    } else if (rc2 == XAException.XA_RDONLY) {
                        // The second connection is read-only, so just commit the first connection.
                        xares1.commit(xid1, false);
                    }
                } else if (rc1 == XAException.XA_RDONLY) {
                    // SQL for the first connection is read-only (such as a SELECT).
                    // The prepare committed it. Prepare the second connection.
                    rc2 = xares2.prepare(xid1);
                    if (rc2 == XAResource.XA_OK) {
                        // The first connection is read-only but the second is not.
                        // Commit the second connection.
                        xares2.commit(xid1, false);
                    } else if (rc2 == XAException.XA_RDONLY) {
                        // Both connections are read-only, and both already committed,
                        // so there is nothing more to do.
                    }
                }
            } catch (XAException xae) {
                // Distributed transaction failed, so roll it back.
                // Report XAException on prepare/commit.
                System.out.println("Distributed transaction prepare/commit failed. Rolling it back.");
                System.out.println("XAException error code = " + xae.errorCode);
                System.out.println("XAException message = " + xae.getMessage());
                xae.printStackTrace();
                try {
                    xares1.rollback(xid1);
                } catch (XAException xae1) {
                    // Report failure of rollback.
                    System.out.println("distributed Transaction rollback xares1 failed");
                    System.out.println("XAException error code = " + xae1.errorCode);
                    System.out.println("XAException message = " + xae1.getMessage());
                }
                try {
                    xares2.rollback(xid1);
                } catch (XAException xae2) {
                    // Report failure of rollback.
                    System.out.println("distributed Transaction rollback xares2 failed");
                    System.out.println("XAException error code = " + xae2.errorCode);
                    System.out.println("XAException message = " + xae2.getMessage());
                }
            }

            try {
                conn1.close();
                xaconn1.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection 1: " + e.toString());
                e.printStackTrace();
            }
            try {
                conn2.close();
                xaconn2.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection 2: " + e.toString());
                e.printStackTrace();
            }
        } catch (SQLException sqe) {
            System.out.println("SQLException caught: " + sqe.getMessage());
            sqe.printStackTrace();
        } catch (XAException xae) {
            System.out.println("XA error is " + xae.getMessage());
            xae.printStackTrace();
        } catch (NamingException nme) {
            System.out.println(" Naming Exception: " + nme.getMessage());
        }
    }
}