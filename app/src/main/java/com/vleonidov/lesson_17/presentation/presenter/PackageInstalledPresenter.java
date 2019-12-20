package com.vleonidov.lesson_17.presentation.presenter;

import androidx.annotation.NonNull;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.presentation.view.IPackageInstalledView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Presenter главного экрана приложения.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledPresenter {

    private final WeakReference<IPackageInstalledView> mMainActivityWeakReference;

    private final PackageInstalledRepository mPackageInstalledRepository;

    public PackageInstalledPresenter(@NonNull IPackageInstalledView mainActivity,
                                     @NonNull PackageInstalledRepository packageInstalledRepository) {
        mMainActivityWeakReference = new WeakReference<>(mainActivity);
        mPackageInstalledRepository = packageInstalledRepository;
    }

    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync() {
        IPackageInstalledView packageInstalledView = mMainActivityWeakReference.get();
        if (packageInstalledView != null) {
            packageInstalledView.showProgress();

            List<InstalledPackageModel> data = mPackageInstalledRepository.getData(true);

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

    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        mMainActivityWeakReference.clear();
    }
}
