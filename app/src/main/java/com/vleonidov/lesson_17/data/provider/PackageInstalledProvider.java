package com.vleonidov.lesson_17.data.provider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.vleonidov.lesson_17.R;
import com.vleonidov.lesson_17.data.model.InstalledPackageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Провайдер данных об установленных пакетах в системе.
 *
 * @author Леонидов Василий on 8/2/21
 */
public class PackageInstalledProvider {

    private final Context mContext;
    private final PackageManager mPackageManager;

    /**
     * Конструктор провайдера данных об установленных пакетах в системе.
     *
     * @param context контекст.
     */
    public PackageInstalledProvider(@NonNull Context context) {
        mContext = context;
        mPackageManager = context.getPackageManager();
    }

    /**
     * Метод для получения списка установленных в системе пакетов.
     *
     * @param isSystem {@code true} если необходимо добавлять системные пакеты, {@code false} иначе.
     * @return список моделей {@link InstalledPackageModel} описывающих установленные в системе пакеты.
     */
    public List<InstalledPackageModel> getData(boolean isSystem) {
        List<InstalledPackageModel> installedPackageModels = new ArrayList<>();

        for (String packageName : getInstalledPackages(isSystem)) {
            getAppSize(packageName);

            InstalledPackageModel installedPackageModel = new InstalledPackageModel(
                    getAppName(packageName), packageName, getAppIcon(packageName));

            installedPackageModels.add(installedPackageModel);
        }

        return installedPackageModels;
    }

    private List<String> getInstalledPackages(boolean isSystem) {
        List<String> apkPackageName = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = mPackageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {

            if (isSystem || !isSystemPackage(resolveInfo)) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                apkPackageName.add(activityInfo.applicationInfo.packageName);
            }
        }

        return apkPackageName;
    }

    private String getAppName(@NonNull String packageName) {
        String appName = "";
        ApplicationInfo applicationInfo;

        try {
            applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            appName = (String) mPackageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    private Drawable getAppIcon(@NonNull String packageName) {
        Drawable drawable;
        try {
            drawable = mPackageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
        }

        return drawable;
    }

    //данный метод не очень просто реализовать. здесь он нужен только для того, чтобы увеличить время загрузки и понаслаждаться работой презентера
    //по переключению видов.
    private int getAppSize(@NonNull String packageName) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private boolean isSystemPackage(@NonNull ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
