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
    public static final String COLUMN = "COLUMN";
    public static final String COLUMN_VALUE = "Column Value";
    public static final String ID = "ID";
    private XADataSource mockedXaDataSource;
    private XAConnection mockedXaConnection;
    private XAResource mockedXaResource;
    private Connection mockedConnection;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;

    @Before
    public void before() throws SQLException {
        mockedXaDataSource = mock(XADataSource.class);
        mockedXaConnection = mock(XAConnection.class);
        when(mockedXaDataSource.getXAConnection()).thenReturn(mockedXaConnection);
        mockedXaResource = mock(XAResource.class);
        when(mockedXaConnection.getXAResource()).thenReturn(mockedXaResource);
        mockedConnection = mock(Connection.class);
        when(mockedXaConnection.getConnection()).thenReturn(mockedConnection);
        mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        mockedResultSet = mock(ResultSet.class);
        when(mockedPreparedStatement.executeQuery(anyString())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true);
        Random random = new Random(10000);
        when(mockedResultSet.getString(anyString())).thenReturn(COLUMN_VALUE);
        when(mockedResultSet.getLong(anyString())).thenReturn(random.nextLong());
    }

    @Test
    public void test() throws SQLException {
        XAConnection xaConnection = mockedXaDataSource.getXAConnection();
        Connection connection = xaConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        ResultSet rs = preparedStatement.executeQuery(QUERY);
        if (rs.next()) {
            System.out.println(rs.getLong(124414) + "\t" + rs.getString("fasfkasf"));
        }

        XAResource xaResource = xaConnection.getXAResource();
        System.out.println(xaResource);
    }
}
