package com.vleonidov.lesson_17.presentation.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vleonidov.lesson_17.R;
import com.vleonidov.lesson_17.data.model.InstalledPackageModel;
import com.vleonidov.lesson_17.data.repository.PackageInstalledRepository;
import com.vleonidov.lesson_17.presentation.presenter.PackageInstalledPresenter;

import java.util.List;

/**
 * Главное активити приложения. Умеет показывать список установленных приложений на телефоне.
 *
 * @author Леонидов Василий on 2019-10-31
 */
public class PackageInstalledActivity extends AppCompatActivity implements IPackageInstalledView {

    private RecyclerView mRecyclerView;
    private View mProgressFrameLayout;

    private PackageInstalledPresenter mMainPresenter;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        providePresenter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsync();
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
        mProgressFrameLayout.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showData(@NonNull List<InstalledPackageModel> modelList) {
        PackageInstalledRecyclerAdapter adapter =
                new PackageInstalledRecyclerAdapter(modelList);

        mRecyclerView.setAdapter(adapter);
    }

    private void providePresenter() {
        PackageInstalledRepository packageInstalledRepository =
                new PackageInstalledRepository(this);

        mMainPresenter = new PackageInstalledPresenter(this, packageInstalledRepository);
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);

        mProgressFrameLayout = findViewById(R.id.progress_frame_layout);
    }
}
