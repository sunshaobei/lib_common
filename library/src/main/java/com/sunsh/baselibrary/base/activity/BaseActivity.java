package com.sunsh.baselibrary.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.SwipeBackActivity;
import com.sunsh.baselibrary.sweetdialog.SweetAlertDialog;
import com.sunsh.baselibrary.utils.KeyboardUtil;
import com.sunsh.baselibrary.utils.PermissionUtils;
import com.sunsh.baselibrary.utils.StatusBarUtil;
import com.sunsh.baselibrary.widgets.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends SwipeBackActivity {

    private SweetAlertDialog sweetAlertDialog;
    private Unbinder unbinder;

    private final int REQUEST = 200;
    private OnPermissionResult onPermissionResult;
    private String[] permissons;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.darkMode(this, Color.TRANSPARENT, 0.2f, isStatusBarTextDackColor());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (overrideSoftInput() != -1) {
            setFitsSystemWindows(true);
            getWindow().setSoftInputMode(overrideSoftInput());
        }
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));

    }

    @Override
    public void setContentView(View view) {
        FrameLayout frameLayout = view.findViewById(R.id.fragment_container);
        loadingView = new LoadingView(this);
        loadingView.setVisibility(View.GONE);
        if (frameLayout == null) {
            frameLayout = new FrameLayout(this);
            frameLayout.addView(view);
            frameLayout.addView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            super.setContentView(frameLayout);
        } else {
            frameLayout.addView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            super.setContentView(view);
        }
        unbinder = ButterKnife.bind(this);
    }

    protected <T extends ViewDataBinding> T DataBindingContentView(@LayoutRes int layoutResID) {
        T binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        setContentView(binding.getRoot());
        return binding;
    }

    protected void setFitsSystemWindows(boolean b) {
        findViewById(R.id.swipe_layout).setFitsSystemWindows(b);
    }

    public boolean isStatusBarTextDackColor() {
        return true;
    }

    protected int overrideSoftInput() {
        return -1;
    }


    public void showLoadingDialog(String str) {
        if (sweetAlertDialog == null) {
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.setTitleText(str).show();
        } else {
            sweetAlertDialog.setTitleText(str).changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            if (!sweetAlertDialog.isShowing())
                sweetAlertDialog.show();
        }

    }

    /**
     * 关闭loading
     */
    public void dismissLoadingDialog() {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismissWithAnimation();
        }
    }

    public void successDialog(String str) {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.setTitleText(str).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            new Handler().postDelayed(sweetAlertDialog::dismissWithAnimation, 1000);
        }
    }

    public void errorDialog(String str) {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismissWithAnimation();
        }
    }

    public void warnDialog(String str) {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismissWithAnimation();
        }
    }

    public void checkHasSelfPermissions(OnPermissionResult onPermissionResult, String... permissions) {
        this.onPermissionResult = onPermissionResult;
        this.permissons = permissions;
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            onPermissionResult.permissionAllow();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST);
        }
    }

    public interface OnPermissionResult {
        void permissionAllow();

        void permissionForbid();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    onPermissionResult.permissionAllow();
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(this, this.permissons)) {
                        String permission = PermissionUtils.getDontAskAgainPermission(this, this.permissons);
                        if (!permission.equals("")) {
                            String title = PermissionUtils.PermissionEnum.statusOf(permission).getDenidStr();
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("提示")
                                    .setMessage(title)
                                    .setPositiveButton("去设置", (dialog1, which) -> {
                                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, REQUEST);
                                    }).setNegativeButton("取消", (dialog12, which) -> {

                            });
                            builder.create().show();
                        }
                    } else {
                        String title = PermissionUtils.PermissionEnum.statusOf(PermissionUtils.getShouldShowRequestPermission(this, this.permissons)).getDenidStr();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示")
                                .setMessage(title)
                                .setPositiveButton("开启", (dialog1, which) -> {
                                    ActivityCompat.requestPermissions(BaseActivity.this, permissions, REQUEST);
                                }).setNegativeButton("仍然禁止", (dialog12, which) -> {

                        });
                        builder.create().show();
                        onPermissionResult.permissionForbid();
                    }
                }
                break;
            default:
                break;
        }
    }


    public void showLoadingView() {
        showLoadingView(Color.WHITE);
    }
    public void showTranslucentLoadingView() {
        showLoadingView(ContextCompat.getColor(this,R.color.thrity_transparency));
    }

    public void showLoadingView(int color) {
        loadingView.showLoading(color);
    }

    public void dismissLoadingView() {
        loadingView.dismissLoading();
    }

    public LoadingView getLoadingView() {
        return loadingView;
    }


    /**
     * 是否正在loading
     *
     * @return true or false
     */
    protected boolean isShowing() {
        if (sweetAlertDialog != null)
            return sweetAlertDialog.isShowing();
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }


    @Override
    public void finish() {
        closeKeyboard();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void closeKeyboard() {
        KeyboardUtil.closeKeyboard(this);
    }

    public void openKeyboard(EditText editText) {
        KeyboardUtil.openKeyboard(this, editText);
    }

    // init


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        closeKeyboard();
        super.onPause();
    }
}
