package com.vleonidov.lesson_17.presentation.detail.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;

import io.reactivex.disposables.Disposable;

/**
 * ViewModel главного экрана приложения.
 *
 * @author Леонидов Василий on 8/3/21
 */
public class PackageDetailViewModel extends ViewModel {

    private static final String TAG = "PackagedViewModel";

    private final IPackageInstalledRepository mPackageInstalledRepository;
    private final ISchedulersProvider mSchedulersProvider;

    private final MutableLiveData<Boolean> mProgressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<InstalledPackageModel> mInstallPackageLiveData = new MutableLiveData<>();

    private Disposable mDisposable;

    /**
     * Конструктор ViewModel.
     *
     * @param packageInstalledRepository {@link IPackageInstalledRepository} репозиторий для получения данных.
     * @param schedulersProvider         {@link ISchedulersProvider} провайдер шедулеров RX.
     */
    public PackageDetailViewModel(@NonNull IPackageInstalledRepository packageInstalledRepository,
                                  @NonNull ISchedulersProvider schedulersProvider) {
        mPackageInstalledRepository = packageInstalledRepository;
        mSchedulersProvider = schedulersProvider;
    }

    public void loadDataAsyncRx(@NonNull String packageName) {
        mDisposable = mPackageInstalledRepository.loadDataByPackageName(packageName)
                .doOnSubscribe(disposable -> mProgressLiveData.postValue(true))
                .doAfterTerminate(() -> mProgressLiveData.postValue(false))
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(mInstallPackageLiveData::setValue, mErrorLiveData::setValue);
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

    public LiveData<InstalledPackageModel> getInstallPackageLiveData() {
        return mInstallPackageLiveData;
    }
}
