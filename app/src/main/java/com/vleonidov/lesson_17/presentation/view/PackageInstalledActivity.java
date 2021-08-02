package com.vleonidov.lesson_17.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.provider.PackageInstalledProvider;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.databinding.ActivityMainBinding;
import com.vleonidov.lesson_17.presentation.view.adapter.PackageInstalledRecyclerAdapter;
import com.vleonidov.lesson_17.presentation.viewmodel.PackageInstalledViewModel;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;
import com.vleonidov.lesson_17.utils.SchedulersProvider;

import java.util.List;

/**
 * Главное активити приложения. Умеет показывать список установленных приложений на телефоне.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledActivity extends FragmentActivity {
    private static final String TAG = "PackageInstalledActivit";

    private PackageInstalledViewModel mViewModel;
    private ActivityMainBinding mBinding;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        createViewModel();
        observeLiveData();

        if (savedInstanceState == null) {
            mViewModel.loadDataAsync();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        super.onStart();
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
                return (T) new PackageInstalledViewModel(packageInstalledRepository, schedulersProvider);
            }
        }).get(PackageInstalledViewModel.class);

        Log.d(TAG, "ViewModel instance = " + mViewModel.toString());
    }

    private void observeLiveData() {
        mViewModel.getProgressLiveData().observe(this, this::showProgress);
        mViewModel.getInstallPackagesLiveData().observe(this, this::showData);
        mViewModel.getErrorLiveData().observe(this, this::showError);
    }

    private void showProgress(boolean isVisible) {
        Log.i(TAG, "showProgress called with param = " + isVisible);

        mBinding.progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showData(@NonNull List<InstalledPackageModel> modelList) {
        Log.d(TAG, "showData called modelList size = " + modelList.size());

        PackageInstalledRecyclerAdapter adapter =
                new PackageInstalledRecyclerAdapter(modelList);

        mBinding.recyclerView.setAdapter(adapter);
    }

    private void showError(@NonNull Throwable throwable) {
        Log.e(TAG, "showError called with error = " + throwable.toString());

        Snackbar.make(mBinding.getRoot(), throwable.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
