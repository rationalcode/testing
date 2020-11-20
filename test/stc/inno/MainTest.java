package stc.inno;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stc.inno.ConnectionManager.ConnectionManager;
import stc.inno.ConnectionManager.ConnectionManagerJdbcImpl;
import stc.inno.dao.MobileDao;
import stc.inno.dao.MobileDaoJdbcImpl;
import stc.inno.mocks.ConnectionManagerMock;

import java.sql.SQLException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Logger logger = LoggerFactory.getLogger("MainTest");
    private Main      main;
    private MobileDao mobileDao;
    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() throws SQLException {
        logger.info("setUp");
        main = new Main();
        connectionManager = new ConnectionManagerMock();
        mobileDao = new MobileDaoJdbcImpl(connectionManager);
    }

    @AfterAll
    static void tearDownAll() {
        logger.info("tearDownAll");
    }

    @BeforeAll
    static void setUpAll() {
        logger.info("setUpAll");
    }

    @AfterEach
    void tearDown() {
        logger.info("tearDown");
    }

    @Test
    @DisplayName("Тестируем метод1 из класса Main")
    void testMethod1WithOk() {
        assertDoesNotThrow(() -> main.method1(mobileDao));
    }

    @Test
    void testMethod1WithNullPointerExeption() {
        final NullPointerException nullPointerException
                = assertThrows(NullPointerException.class, () -> main.method1(null));
        assertNull(nullPointerException.getMessage());
    }

    @ParameterizedTest
    @MethodSource("getSource")
    @Disabled
    void testGetPrice(ArgumentsAccessor accessor) {
        final int price = main.getPrice(accessor.getInteger(1), accessor.getInteger(2));
        assertEquals(accessor.getInteger(3), price);
    }

    private static Stream<Arguments> getSource() {
        return Stream.of(
          Arguments.of(2,2,4),
          Arguments.of(3,3,8)
        );
    }
}