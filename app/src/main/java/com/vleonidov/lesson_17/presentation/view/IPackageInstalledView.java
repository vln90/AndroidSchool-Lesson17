package com.vleonidov.lesson_17.presentation.view;

import androidx.annotation.NonNull;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;

import java.util.List;

/**
 * Интерфейс, описывающий возможности View.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public interface IPackageInstalledView {

    /**
     * Показать ProgressBar.
     */
    void showProgress();

    /**
     * Скрыть ProgressBar.
     */
    void hideProgress();

    /**
     * Отобразить данные об установленных приложениях.
     *
     * @param modelList список приложений.
     */
    void showData(@NonNull List<InstalledPackageModel> modelList);

    /**
     * Отобразить ошибку при получении данных.
     *
     * @param throwable возникшая ошибка при получении данных.
     */
    void showError(@NonNull Throwable throwable);
}
