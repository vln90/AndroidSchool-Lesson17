package com.vleonidov.lesson_17.presentation.presenter;

import androidx.annotation.NonNull;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.presentation.view.IPackageInstalledView;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Presenter главного экрана приложения.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledPresenter {

    private final WeakReference<IPackageInstalledView> mMainActivityWeakReference;
    private final IPackageInstalledRepository mPackageInstalledRepository;
    private final ISchedulersProvider mSchedulersProvider;

    private Disposable mDisposable;

    /**
     * Конструктор презентера.
     *
     * @param mainActivity               {@link IPackageInstalledView} контракт View.
     * @param packageInstalledRepository {@link IPackageInstalledRepository} репозиторий для получения данных.
     * @param schedulersProvider         {@link ISchedulersProvider} провайдер шедулеров RX.
     */
    public PackageInstalledPresenter(@NonNull IPackageInstalledView mainActivity,
                                     @NonNull IPackageInstalledRepository packageInstalledRepository,
                                     @NonNull ISchedulersProvider schedulersProvider) {
        mMainActivityWeakReference = new WeakReference<>(mainActivity);
        mPackageInstalledRepository = packageInstalledRepository;
        mSchedulersProvider = schedulersProvider;
    }

    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync() {
        IPackageInstalledView packageInstalledView = mMainActivityWeakReference.get();
        if (packageInstalledView != null) {
            packageInstalledView.showProgress();

            List<InstalledPackageModel> data = mPackageInstalledRepository.loadDataSync(true);

            packageInstalledView.hideProgress();

            packageInstalledView.showData(data);
        }
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync() {
        IPackageInstalledView packageInstalledView = mMainActivityWeakReference.get();
        if (packageInstalledView != null) {
            packageInstalledView.showProgress();
        }

        PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener = packageModels -> {
            IPackageInstalledView installedView = mMainActivityWeakReference.get();
            if (installedView != null) {
                installedView.hideProgress();
                installedView.showData(packageModels);
            }
        };

        mPackageInstalledRepository.loadDataAsync(true, onLoadingFinishListener);
    }

    public void loadDataAsyncRx(boolean isSystem) {
        IPackageInstalledView packageInstalledView = mMainActivityWeakReference.get();

        mDisposable = mPackageInstalledRepository.loadDataAsyncRx(isSystem)
                .doOnSubscribe(disposable -> {
                    if (packageInstalledView != null) {
                        packageInstalledView.showProgress();
                    }
                })
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(installedPackageModels -> {
                    if (packageInstalledView != null) {
                        packageInstalledView.hideProgress();
                        packageInstalledView.showData(installedPackageModels);
                    }
                }, throwable -> {
                    if (packageInstalledView != null) {
                        packageInstalledView.hideProgress();
                        packageInstalledView.showError(throwable);
                    }
                });
    }

    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mMainActivityWeakReference.clear();
    }
}
