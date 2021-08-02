package com.vleonidov.lesson_17.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.vleonidov.lesson_17.R;
import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.databinding.PackageInstalledViewItemBinding;

import java.util.List;

import static com.vleonidov.lesson_17.presentation.view.adapter.PackageInstalledRecyclerAdapter.PackageInstalledViewHolder;

/**
 * Адаптер для отображения элементов списка.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledRecyclerAdapter extends Adapter<PackageInstalledViewHolder> {

    private final List<InstalledPackageModel> mInstalledPackageModelList;

    public PackageInstalledRecyclerAdapter(@NonNull List<InstalledPackageModel> installedPackageModelList) {
        mInstalledPackageModelList = installedPackageModelList;
    }

    @NonNull
    @Override
    public PackageInstalledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageInstalledViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.package_installed_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackageInstalledViewHolder holder, int position) {
        holder.bindView(mInstalledPackageModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mInstalledPackageModelList.size();
    }

    static class PackageInstalledViewHolder extends RecyclerView.ViewHolder {

        private final PackageInstalledViewItemBinding mBinding;

        PackageInstalledViewHolder(@NonNull View itemView) {
            super(itemView);

            mBinding = PackageInstalledViewItemBinding.bind(itemView);
        }

        void bindView(@NonNull InstalledPackageModel installedPackageModel) {
            mBinding.appNameTextView.setText(installedPackageModel.getAppName());
            mBinding.appPackageTextView.setText(installedPackageModel.getAppPackageName());
            mBinding.appIconImageView.setImageDrawable(installedPackageModel.getAppIcon());
        }
    }
}
