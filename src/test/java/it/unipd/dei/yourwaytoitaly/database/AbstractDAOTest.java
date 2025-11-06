package it.unipd.dei.yourwaytoitaly.database;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.InOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * Unit test for AbstractDAO class
 * Tests cleanup operations for database resources
 * 
 * @author YourWayToItaly Test Team
 * @version 1.0
 * @since 1.0
 */
public class AbstractDAOTest {

    @Test
    public void testCleaningOperationsWithAllResources() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockResult = Mockito.mock(ResultSet.class);
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(mockStmt, mockResult, mockConn);

        verify(mockStmt, times(1)).close();
        verify(mockResult, times(1)).close();
        verify(mockConn, times(1)).close();
    }

    @Test
    public void testCleaningOperationsWithNullStatement() throws SQLException {
        ResultSet mockResult = Mockito.mock(ResultSet.class);
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(null, mockResult, mockConn);

        verify(mockResult, times(1)).close();
        verify(mockConn, times(1)).close();
    }

    @Test
    public void testCleaningOperationsWithNullResultSet() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(mockStmt, null, mockConn);

        verify(mockStmt, times(1)).close();
        verify(mockConn, times(1)).close();
    }

    @Test
    public void testCleaningOperationsWithNullConnection() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockResult = Mockito.mock(ResultSet.class);

        AbstractDAO.cleaningOperations(mockStmt, mockResult, null);

        verify(mockStmt, times(1)).close();
        verify(mockResult, times(1)).close();
    }

    @Test
    public void testCleaningOperationsWithAllNull() throws SQLException {
        AbstractDAO.cleaningOperations(null, null, null);
    }

    @Test
    public void testCleaningOperationsTwoParameters() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(mockStmt, mockConn);

        verify(mockStmt, times(1)).close();
        verify(mockConn, times(1)).close();
    }

    @Test
    public void testCleaningOperationsTwoParametersWithNullStatement() throws SQLException {
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(null, mockConn);

        verify(mockConn, times(1)).close();
    }

    @Test
    public void testCleaningOperationsTwoParametersWithNullConnection() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);

        AbstractDAO.cleaningOperations(mockStmt, null);

        verify(mockStmt, times(1)).close();
    }

    @Test
    public void testCleaningOperationsTwoParametersWithBothNull() throws SQLException {
        AbstractDAO.cleaningOperations(null, null);
    }

    @Test(expected = SQLException.class)
    public void testCleaningOperationsThrowsSQLException() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        Connection mockConn = Mockito.mock(Connection.class);

        doThrow(new SQLException("Close failed")).when(mockStmt).close();

        AbstractDAO.cleaningOperations(mockStmt, mockConn);
    }

    @Test
    public void testCleaningOperationsClosesInCorrectOrder() throws SQLException {
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockResult = Mockito.mock(ResultSet.class);
        Connection mockConn = Mockito.mock(Connection.class);

        AbstractDAO.cleaningOperations(mockStmt, mockResult, mockConn);

        InOrder inOrder = inOrder(mockStmt, mockResult, mockConn);
        inOrder.verify(mockStmt).close();
        inOrder.verify(mockResult).close();
        inOrder.verify(mockConn).close();
    }
}
