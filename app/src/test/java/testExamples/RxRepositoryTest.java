package testExamples;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

/**
 * Тестирование {@link RxRepository}
 */
public class RxRepositoryTest {

    private static final String FIRST = "first";
    private static final String SECOND = "second";
    private static final String THIRD = "third";
    private static final String FOURTH = "fourth";
    private RxRepository mRxRepository;
    private RxRepository.SomeService mSomeService;

    @Before
    public void setUp() {
        mSomeService = Mockito.mock(RxRepository.SomeService.class);
        mRxRepository = new RxRepository(mSomeService);
    }

    @Test
    public void getData() {

        // Arrange
        Mockito.doReturn(Observable.just(FIRST, SECOND, THIRD)).when(mSomeService).getDataFromServer();

        // Act
        TestObserver<String> testObserver = mRxRepository.getData().test();

        // Assert
        // testObserver.assertComplete();
        testObserver.assertResult(FIRST, SECOND, THIRD);
        testObserver.assertValueAt(1, SECOND);
        testObserver.assertValueAt(2, value -> value.equals(THIRD));
        // testObserver.assertEmpty();
        testObserver.assertNever(FOURTH);
        // testObserver.assertFailureAndMessage(RuntimeException.class, "Message");
        testObserver.dispose();
    }
}