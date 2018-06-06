package com.yesway.errorpagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yesway.widget.ErrorHintView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int VIEW_CONTENT = 1;
    /**
     * 显示断网
     **/
    public static int VIEW_WIFIFAILUER = 2;
    /**
     * 显示加载数据失败
     **/
    public static int VIEW_LOADFAILURE = 3;
    public static int VIEW_LOADING = 4;
    private ErrorHintView mErrorHintView;
    private TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorHintView = findViewById(R.id.hintView);
        mContentView = findViewById(R.id.content);
        findViewById(R.id.bt_data).setOnClickListener(this);
        findViewById(R.id.bt_empty).setOnClickListener(this);
        findViewById(R.id.bt_loading).setOnClickListener(this);
        findViewById(R.id.bt_neterror).setOnClickListener(this);
    }

    /**
     * 显示正在加载界面
     *
     * @param i
     */
    private void showLoading(int i) {
        mErrorHintView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);

        switch (i) {
            case 1:
                mErrorHintView.hideLoading();
                mContentView.setVisibility(View.VISIBLE);
                break;

            case 2:
                mErrorHintView.hideLoading();
                mErrorHintView.netError(new ErrorHintView.OperateListener() {
                    @Override
                    public void operate() {
                        showLoading(VIEW_LOADING);
                    }
                });
                break;

            case 3:
                mErrorHintView.hideLoading();
                mErrorHintView.loadFailure(new ErrorHintView.OperateListener() {
                    @Override
                    public void operate() {
                        showLoading(VIEW_LOADING);
                    }
                });
                break;

            case 4:
                mErrorHintView.loadingData();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_data:
                showLoading(VIEW_CONTENT);
                break;
            case R.id.bt_empty:
                showLoading(VIEW_LOADFAILURE);
                break;
            case R.id.bt_loading:
                showLoading(VIEW_LOADING);
                break;
            case R.id.bt_neterror:
                showLoading(VIEW_WIFIFAILUER);
                break;

            default:
                break;
        }
    }
}
