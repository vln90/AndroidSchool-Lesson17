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

public class PackageInstalledActivity extends AppCompatActivity implements IPackageInstalledView {

    private RecyclerView mRecyclerView;
    private View mProgressFrameLayout;

    private PackageInstalledPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        providePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
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

    @Override
    public void showProgress() {
        mProgressFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showData(@NonNull List<InstalledPackageModel> modelList) {
        PackageInstalledRecyclerAdapter adapter =
                new PackageInstalledRecyclerAdapter(modelList);

        mRecyclerView.setAdapter(adapter);
    }
}
