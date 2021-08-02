package com.vleonidov.lesson_17.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты на {@link PackageInstalledViewModel}.
 *
 * @author Леонидов Василий on 8/3/21
 */
@RunWith(MockitoJUnitRunner.class)
public class PackageInstalledViewModelTest {

    @Rule
    public InstantTaskExecutorRule mRule = new InstantTaskExecutorRule();

    @Mock
    private IPackageInstalledRepository mPackageInstalledRepository;
    @Mock
    private ISchedulersProvider mSchedulersProvider;

    @Mock
    private Observer<Boolean> mProgressLiveDataObserver;
    @Mock
    private Observer<Throwable> mErrorLiveDataObserver;
    @Mock
    private Observer<List<InstalledPackageModel>> mPackageInstalledModelLiveDataObserver;

    private PackageInstalledViewModel mViewModel;

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    public void setUp() {
        when(mSchedulersProvider.io()).thenReturn(Schedulers.trampoline());
        when(mSchedulersProvider.ui()).thenReturn(Schedulers.trampoline());

        mViewModel = new PackageInstalledViewModel(mPackageInstalledRepository, mSchedulersProvider);

        mViewModel.getProgressLiveData().observeForever(mProgressLiveDataObserver);
        mViewModel.getErrorLiveData().observeForever(mErrorLiveDataObserver);
        mViewModel.getInstallPackagesLiveData().observeForever(mPackageInstalledModelLiveDataObserver);
    }

    /**
     * Тестирование синхронного получения данных в презентере.
     */
    @Test
    public void testLoadDataSync() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.loadDataSync(anyBoolean())).thenReturn(createTestData());

        //Вызов тестируемого метода
        mViewModel.loadDataSync();

        //Проверка, что презентер действительно вызывает методы представления
        verify(mProgressLiveDataObserver).onChanged(true);
        verify(mPackageInstalledModelLiveDataObserver).onChanged(createTestData());
        verify(mProgressLiveDataObserver).onChanged(false);
    }

    /**
     * Тестирование синхронного метода получения данных в презентере.
     * <p> В данном тесте дополнительно проверяется порядок вызова методов.
     */
    @Test
    public void testLoadDataSync_withOrder() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.loadDataSync(anyBoolean())).thenReturn(createTestData());

        //Вызов тестируемого метода
        mViewModel.loadDataSync();

        InOrder inOrder = Mockito.inOrder(mErrorLiveDataObserver, mPackageInstalledModelLiveDataObserver, mProgressLiveDataObserver);

        //Проверка, что презентер действительно вызывает методы представления, причем в порядке вызова этих методов. Можно сравнить с предыдущим тестом.
        inOrder.verify(mProgressLiveDataObserver).onChanged(true);
        inOrder.verify(mPackageInstalledModelLiveDataObserver).onChanged(createTestData());
        inOrder.verify(mProgressLiveDataObserver).onChanged(false);

        //Проверка, что никакой метод не будет вызван у mPackageInstalledView.
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadDataAsyncRx() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.loadDataAsyncRx(anyBoolean())).thenReturn(Single.just(createTestData()));

        mViewModel.loadDataAsyncRx(true);

        InOrder inOrder = Mockito.inOrder(mProgressLiveDataObserver, mPackageInstalledModelLiveDataObserver);

        //Проверка, что презентер действительно вызывает методы представления, причем в порядке вызова этих методов. Можно сравнить с предыдущим тестом.
        inOrder.verify(mProgressLiveDataObserver).onChanged(true);
        inOrder.verify(mPackageInstalledModelLiveDataObserver).onChanged(createTestData());
        inOrder.verify(mProgressLiveDataObserver).onChanged(false);

        //Проверка, что никакой метод не будет вызван у mPackageInstalledView.
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadDataAsyncRx_returnsError() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.loadDataAsyncRx(anyBoolean())).thenReturn(Single.error(new IllegalAccessException()));

        mViewModel.loadDataAsyncRx(true);

        verify(mErrorLiveDataObserver).onChanged(isA(IllegalAccessException.class));
    }

    private List<InstalledPackageModel> createTestData() {
        List<InstalledPackageModel> testData = new ArrayList<>();

        testData.add(new InstalledPackageModel("Sberbank",
                "ru.sberbankmobile", null));
        testData.add(new InstalledPackageModel("Test", "TestPackage",
                null));

        return testData;
    }
}