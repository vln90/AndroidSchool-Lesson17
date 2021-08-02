package com.vleonidov.lesson_17.presentation.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * ViewModel главного экрана приложения.
 *
 * @author Леонидов Василий on 8/3/21
 */
public class PackageInstalledViewModel extends ViewModel {

    private static final String TAG = "PackagedViewModel";

    private final IPackageInstalledRepository mPackageInstalledRepository;
    private final ISchedulersProvider mSchedulersProvider;

    private final MutableLiveData<Boolean> mProgressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<InstalledPackageModel>> mInstallPackagesLiveData = new MutableLiveData<>();

    private Disposable mDisposable;

    /**
     * Конструктор ViewModel.
     *
     * @param packageInstalledRepository {@link IPackageInstalledRepository} репозиторий для получения данных.
     * @param schedulersProvider         {@link ISchedulersProvider} провайдер шедулеров RX.
     */
    public PackageInstalledViewModel(@NonNull IPackageInstalledRepository packageInstalledRepository,
                                     @NonNull ISchedulersProvider schedulersProvider) {
        mPackageInstalledRepository = packageInstalledRepository;
        mSchedulersProvider = schedulersProvider;
    }

    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync() {
        mProgressLiveData.setValue(true);
        List<InstalledPackageModel> data = mPackageInstalledRepository.loadDataSync(true);
        mInstallPackagesLiveData.setValue(data);
        mProgressLiveData.setValue(false);
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync() {
        mProgressLiveData.setValue(true);

        PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener = packageModels -> {
            mProgressLiveData.setValue(false);
            mInstallPackagesLiveData.setValue(packageModels);
        };

        mPackageInstalledRepository.loadDataAsync(true, onLoadingFinishListener);
    }

    public void loadDataAsyncRx(boolean isSystem) {
        mDisposable = mPackageInstalledRepository.loadDataAsyncRx(isSystem)
                .doOnSubscribe(disposable -> mProgressLiveData.postValue(true))
                .doAfterTerminate(() -> mProgressLiveData.postValue(false))
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(mInstallPackagesLiveData::setValue, mErrorLiveData::setValue);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }

        Log.d(TAG, "onCleared() called");
    }

    public LiveData<Boolean> getProgressLiveData() {
        return mProgressLiveData;
    }

    public LiveData<Throwable> getErrorLiveData() {
        return mErrorLiveData;
    }

    public LiveData<List<InstalledPackageModel>> getInstallPackagesLiveData() {
        return mInstallPackagesLiveData;
    }
}
