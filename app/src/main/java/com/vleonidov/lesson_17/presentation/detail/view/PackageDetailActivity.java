package com.vleonidov.lesson_17.presentation.detail.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vleonidov.lesson_17.R;
import com.vleonidov.lesson_17.data.provider.PackageInstalledProvider;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.databinding.ActivityDetailBinding;
import com.vleonidov.lesson_17.presentation.detail.viewmodel.PackageDetailViewModel;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;
import com.vleonidov.lesson_17.utils.SchedulersProvider;

/**
 * @author Леонидов Василий on 8/3/21
 */
public class PackageDetailActivity extends FragmentActivity {
    private static final String TAG = "PackageDetailActivity";

    public static final String BUNDLE_PACKAGE_NAME_KEY = "BUNDLE_PACKAGE_NAME_KEY";

    private PackageDetailViewModel mViewModel;
    private ActivityDetailBinding mBinding;

    public static Intent newIntent(@NonNull Context context, @NonNull String packageName) {
        Intent intent = new Intent(context, PackageDetailActivity.class);
        intent.putExtra(BUNDLE_PACKAGE_NAME_KEY, packageName);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mBinding.setLifecycleOwner(this);

        createViewModel();

        if (savedInstanceState == null) {
            mViewModel.loadDataAsyncRx(getIntent().getStringExtra(BUNDLE_PACKAGE_NAME_KEY));
        }
    }

    private void createViewModel() {
        PackageInstalledProvider packageInstalledProvider = new PackageInstalledProvider(this);
        IPackageInstalledRepository packageInstalledRepository =
                new PackageInstalledRepository(packageInstalledProvider);
        ISchedulersProvider schedulersProvider = new SchedulersProvider();

        Log.d(TAG, "current instance activity = " + this);

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PackageDetailViewModel(packageInstalledRepository, schedulersProvider);
            }
        }).get(PackageDetailViewModel.class);

        mBinding.setViewModel(mViewModel);
        Log.d(TAG, "ViewModel instance = " + mViewModel.toString());
    }
}
