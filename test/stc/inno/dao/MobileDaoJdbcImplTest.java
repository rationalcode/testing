package stc.inno.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stc.inno.ConnectionManager.ConnectionManager;
import stc.inno.ConnectionManager.ConnectionManagerJdbcImpl;
import stc.inno.pojo.Mobile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class MobileDaoJdbcImplTest {
    private MobileDao         mobileDao;
    private ConnectionManager connectionManager;
    @Mock
    private Connection        connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        connection = mock(Connection.class);
        connectionManager = spy(ConnectionManagerJdbcImpl.getInstance());
        doReturn(connection).when(connectionManager).getConnection();
        mobileDao = spy(new MobileDaoJdbcImpl(connectionManager));
    }

    @Test
    void testAddMobile() throws SQLException {
        when(connection.prepareStatement(MobileDaoJdbcImpl.INSERT_INTO_MOBILE, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        //doReturn(preparedStatement).when(connection).prepareStatement(MobileDaoJdbcImpl.INSERT_INTO_MOBILE);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);
        final Mobile mobile = new Mobile();
        mobile.setId(1);
        mobile.setPrice(12);
        mobile.setManufacturer("A");
        mobile.setModel("A1");

        final Long aLong = mobileDao.addMobile(mobile);

        assertEquals(1L, aLong);
        verify(connection, times(1)).prepareStatement(MobileDaoJdbcImpl.INSERT_INTO_MOBILE, Statement.RETURN_GENERATED_KEYS);
        verify(preparedStatement, atMost(1)).executeUpdate();
    }
}