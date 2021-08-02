package com.vleonidov.lesson_17.presentation.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.provider.PackageInstalledProvider;
import com.vleonidov.lesson_17.data.repository.IPackageInstalledRepository;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.databinding.ActivityMainBinding;
import com.vleonidov.lesson_17.presentation.presenter.PackageInstalledPresenter;
import com.vleonidov.lesson_17.presentation.view.adapter.PackageInstalledRecyclerAdapter;
import com.vleonidov.lesson_17.utils.ISchedulersProvider;
import com.vleonidov.lesson_17.utils.SchedulersProvider;

import java.util.List;

/**
 * Главное активити приложения. Умеет показывать список установленных приложений на телефоне.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledActivity extends AppCompatActivity implements IPackageInstalledView {

    private PackageInstalledPresenter mMainPresenter;

    private ActivityMainBinding mBinding;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        providePresenter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsyncRx(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgress() {
        mBinding.progressBar.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showData(@NonNull List<InstalledPackageModel> modelList) {
        PackageInstalledRecyclerAdapter adapter =
                new PackageInstalledRecyclerAdapter(modelList);

        mBinding.recyclerView.setAdapter(adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showError(@NonNull Throwable throwable) {
        Snackbar.make(mBinding.getRoot(), throwable.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void providePresenter() {
        PackageInstalledProvider packageInstalledProvider = new PackageInstalledProvider(this);
        IPackageInstalledRepository packageInstalledRepository =
                new PackageInstalledRepository(packageInstalledProvider);
        ISchedulersProvider schedulersProvider = new SchedulersProvider();

        mMainPresenter = new PackageInstalledPresenter(this, packageInstalledRepository, schedulersProvider);
    }
}
