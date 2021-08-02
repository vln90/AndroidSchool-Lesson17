package com.vleonidov.lesson_17.data.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.provider.PackageInstalledProvider;

import java.util.List;

import io.reactivex.Single;

/**
 * Репозиторий - провайдер данных об установленных приложениях.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledRepository implements IPackageInstalledRepository {

    private final PackageInstalledProvider mPackageInstalledProvider;

    /**
     * Конструктор провайдера данных для установленных приложений.
     *
     * @param packageInstalledProvider {@link PackageInstalledProvider} провайдер данных об установленных в системе пакетах.
     */
    public PackageInstalledRepository(@NonNull PackageInstalledProvider packageInstalledProvider) {
        if (packageInstalledProvider == null) {
            throw new IllegalArgumentException("packageInstalledProvider can't be null");
        }

        mPackageInstalledProvider = packageInstalledProvider;
    }

    @Override
    public List<InstalledPackageModel> loadDataSync(boolean isSystem) {
        return mPackageInstalledProvider.getData(isSystem);
    }

    @Override
    public Single<List<InstalledPackageModel>> loadDataAsyncRx(boolean isSystem) {
        return Single.fromCallable(() -> mPackageInstalledProvider.getData(isSystem));
    }

    @Override
    public void loadDataAsync(boolean isSystem, @NonNull OnLoadingFinishListener onLoadingFinishListener) {
        LoadingPackagesAsyncTask loadingPackagesAsyncTask = new LoadingPackagesAsyncTask(onLoadingFinishListener, mPackageInstalledProvider);
        loadingPackagesAsyncTask.execute(isSystem);
    }

    //Вообще, не стоит использовать AsyncTask в 2021 году, здесь он для простоты примера Unit Testа с переданным слушателем на метод loadDataAsync
    private static class LoadingPackagesAsyncTask extends AsyncTask<Boolean, Void, List<InstalledPackageModel>> {

        private final OnLoadingFinishListener mOnLoadingFinishListener;
        private final PackageInstalledProvider mPackageInstalledProvider;

        LoadingPackagesAsyncTask(@NonNull OnLoadingFinishListener onLoadingFinishListener,
                                 @NonNull PackageInstalledProvider packageInstalledProvider) {
            mOnLoadingFinishListener = onLoadingFinishListener;
            mPackageInstalledProvider = packageInstalledProvider;
        }

        @Override
        protected void onPostExecute(List<InstalledPackageModel> installedPackageModels) {
            super.onPostExecute(installedPackageModels);

            mOnLoadingFinishListener.onFinish(installedPackageModels);
        }

        @Override
        protected List<InstalledPackageModel> doInBackground(Boolean... booleans) {
            return mPackageInstalledProvider.getData(booleans[0]);
        }
    }

    /**
     * Интерфейс слушателя окончания загрузки данных.
     */
    public interface OnLoadingFinishListener {

        /**
         * Метод, вызываемый после окончания загрузки данных.
         *
         * @param packageModels {@link List} of {@link InstalledPackageModel} список приложений установленных в системе.
         */
        void onFinish(List<InstalledPackageModel> packageModels);
    }
}
