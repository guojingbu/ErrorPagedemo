package com.yesway.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yesway.errorpagedemo.R;

/**
 * created by @author guojingbu on ${DATA}
 */

public class ErrorHintView extends RelativeLayout {


    private RelativeLayout mContainer;
    private ErrorHandler mErrorHandler = new ErrorHandler();
    private OperateListener mOperateListener;
    private LayoutParams layoutParams;
    private AnimationDrawable animationDrawable;

    public ErrorHintView(Context context) {
        super(context);
        init();
    }

    public ErrorHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorHintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        View.inflate(getContext(), R.layout.custom_error_hint_view, this);
        mContainer = (RelativeLayout) findViewById(R.id.container);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }


    class ErrorHandler {

        public ErrorHandler() {
        }

        public void operate(IStrategy iStrategy) {
            show();
            iStrategy.operate();
        }
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void close() {
        setVisibility(View.GONE);
    }

    /**
     * 显示加载失败UI
     */
    public void loadFailure(OperateListener Listener) {
        this.mOperateListener = Listener;
        mErrorHandler.operate(new LoadFailure());
    }

    View loadFailure = null;

    /**
     * 加载失败策略
     */
    class LoadFailure implements IStrategy {


        @Override
        public void operate() {
            if (loadFailure == null) {
                loadFailure = View.inflate(getContext(), R.layout.layout_load_failure, null);
            }
            View view = loadFailure.findViewById(R.id.load_retry);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOperateListener != null) {
                        mOperateListener.operate();
                    }
                }
            });

            mContainer.removeAllViews();
            mContainer.addView(loadFailure, layoutParams);
        }
    }

    /**
     * 显示无网络
     *
     * @param operateListener
     */
    public void netError(OperateListener operateListener) {
        this.mOperateListener = operateListener;
        mErrorHandler.operate(new NetWorkError());
    }

    View netError;

    /**
     * 无网络加载策略
     */
    class NetWorkError implements IStrategy {


        @Override
        public void operate() {
            if (netError == null) {
                netError = View.inflate(getContext(), R.layout.layout_load_wifi_failure, null);
                View view = netError.findViewById(R.id.wifi_retry);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOperateListener != null) {
                            mOperateListener.operate();
                        }

                    }
                });
            }
            mContainer.removeAllViews();
            mContainer.addView(netError, layoutParams);
        }
    }

    View noData;

    /**
     * 显示无数据
     */
    public void noData() {
        mErrorHandler.operate(new NoDataError());
    }

    /**
     * 无数据显示策略
     */
    class NoDataError implements IStrategy {

        @Override
        public void operate() {
            if (noData == null) {
                noData = View.inflate(getContext(),
                        R.layout.layout_load_noorder, null);
            }
            mContainer.removeAllViews();
            mContainer.addView(noData, layoutParams);
        }
    }

    View loadingdata;

    class LoadingData implements IStrategy {


        @Override
        public void operate() {
            if (loadingdata == null) {
                loadingdata = View.inflate(getContext(),
                        R.layout.layout_load_loading, null);
            }
            ImageView iv = (ImageView) loadingdata
                    .findViewById(R.id.loading_iv);
            mContainer.removeAllViews();
            mContainer.addView(loadingdata, layoutParams);
            showLoading(iv);
        }
    }

    /**
     * 转菊花
     */
    public void loadingData() {
        mErrorHandler.operate(new LoadingData());
    }

    /**
     * 显示动画loading
     */
    public void showLoading(final ImageView iv) {
        animationDrawable = (AnimationDrawable) iv.getBackground();
        animationDrawable.start();
    }

    /**
     * 隐藏动画loading
     */
    public void hideLoading() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        close();
    }

    public interface OperateListener {
        void operate();
    }
}
