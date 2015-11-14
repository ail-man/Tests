package com.ail.home.fortest;

import org.junit.Before;
import org.junit.Test;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAResource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static org.mockito.Mockito.*;

public class XATransactionsTest {

    public static final String QUERY = "SELECT * FROM TABLE_NAME";
    public static final String COLUMN_VALUE = "Column Value";
    private XADataSource mockedXaDataSource;

    private static int counter = 0;

    @Before
    public void before() throws SQLException {
        mockedXaDataSource = mock(XADataSource.class);
        XAConnection mockedXaConnection = mock(XAConnection.class);
        when(mockedXaDataSource.getXAConnection()).thenReturn(mockedXaConnection);
        XAResource mockedXaResource = mock(XAResource.class);
        when(mockedXaConnection.getXAResource()).thenReturn(mockedXaResource);
        Connection mockedConnection = mock(Connection.class);
        when(mockedXaConnection.getConnection()).thenReturn(mockedConnection);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        ResultSet mockedResultSet = mock(ResultSet.class);
        when(mockedPreparedStatement.executeQuery(anyString())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("ARGS: " + args);
            return counter++ < 10;
        });
        Random random = new Random();
        when(mockedResultSet.getString(anyInt())).thenReturn(COLUMN_VALUE);
        when(mockedResultSet.getLong(anyInt())).thenAnswer(invocation -> random.nextLong());
        when(mockedResultSet.getBoolean(anyInt())).thenAnswer(invocation -> random.nextBoolean());
    }

    @Test
    public void test() throws SQLException {
        XAConnection xaConnection = mockedXaDataSource.getXAConnection();
        Connection connection = xaConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        ResultSet rs = preparedStatement.executeQuery(QUERY);
        while (rs.next()) {
            System.out.println(rs.getLong(3) + "\t" + rs.getString(3) + "\t" + rs.getBoolean(3));
        }

        XAResource xaResource = xaConnection.getXAResource();
        System.out.println(xaResource);
    }
}
