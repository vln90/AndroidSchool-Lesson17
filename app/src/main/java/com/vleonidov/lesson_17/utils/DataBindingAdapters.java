package com.vleonidov.lesson_17.utils;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.presentation.view.adapter.PackageInstalledRecyclerAdapter;

import java.util.List;

/**
 * Адаптерпы для DataBinding.
 *
 * @author Леонидов Василий on 8/3/21
 */
public final class DataBindingAdapters {
    private DataBindingAdapters() {
        throw new UnsupportedOperationException("Can't instantiate this object");
    }

    /**
     * Биндинг адаптер для ячеек списка. Осуществляет привязку {@link RecyclerView} с адаптером {@link PackageInstalledRecyclerAdapter}.
     *
     * @param recyclerView           экземпляр {@link RecyclerView}.
     * @param packageInstalledModels список моделей для секции.
     */
    @BindingAdapter("packageInstalledModels")
    public static void bindItemList(@NonNull RecyclerView recyclerView,
                                    List<InstalledPackageModel> packageInstalledModels) {
        if (packageInstalledModels != null) {
            PackageInstalledRecyclerAdapter packageInstalledRecyclerAdapter = new PackageInstalledRecyclerAdapter(packageInstalledModels);
            recyclerView.setAdapter(packageInstalledRecyclerAdapter);
        }
    }
}
