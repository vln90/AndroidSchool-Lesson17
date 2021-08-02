package com.vleonidov.lesson_17.data.repository;

import androidx.annotation.NonNull;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Интерфейс репозитория по загрузке данных об установленных в системе пакетах.
 *
 * @author Леонидов Василий on 8/2/21
 */
public interface IPackageInstalledRepository {

    /**
     * Метод для синхронной загрузки данных об установленных в системе приложениях.
     *
     * @param isSystem {@code true} если необходимо показывать системные приложения, {@code false} иначе.
     */
    List<InstalledPackageModel> loadDataSync(boolean isSystem);

    /**
     * Метод для асинхронной загрузки данных об установленных в системе приложениях.
     *
     * @param isSystem {@code true} если необходимо показывать системные приложения, {@code false} иначе.
     * @return Single со списком моделей {@link InstalledPackageModel}.
     */
    Single<List<InstalledPackageModel>> loadDataAsyncRx(boolean isSystem);

    /**
     * Метод для получения данных по выбранному пакету.
     *
     * @param packageName имя пакета.
     * @return Single с моделью описания выбранного пакета.
     */
    Single<InstalledPackageModel> loadDataByPackageName(@NonNull String packageName);

    /**
     * Метод для асинхронной загрузки данных об установленных в системе приложениях.
     *
     * @param isSystem                {@code true} если необходимо показывать системные приложения, {@code false} иначе.
     * @param onLoadingFinishListener {@link PackageInstalledRepository.OnLoadingFinishListener} слушатель окончания загрузки.
     */
    void loadDataAsync(boolean isSystem, @NonNull PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener);
}
