package testExamples;


import io.reactivex.Observable;

public class RxRepository {

    private SomeService mSomeService;

    public RxRepository(SomeService someService) {
        mSomeService = someService;
    }

    public Observable<String> getData() {
        return mSomeService.getDataFromServer();
    }

    public interface SomeService {
        Observable<String> getDataFromServer();
    }
}
