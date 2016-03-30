package com.user.hilo.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.user.hilo.R;
import com.user.hilo.core.BaseToolbarActivity;
import com.user.hilo.interfaces.OnNoDoubleClickListener;
import com.user.hilo.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnCheckedChanged;

/**
 * take photos manager after published
 */
public class PublishActivity extends BaseToolbarActivity {

    public static final String ARG_TAKEN_PHOTO_URI = "arg_taken_photo_uri";

    @Bind(R.id.tbFrients)
    ToggleButton mTbFrients;
    @Bind(R.id.tbDirect)
    ToggleButton mTbDirect;
    @Bind(R.id.ivPhoto)
    ImageView mIvPhoto;
    @Bind(R.id.etDiscription)
    EditText mEtDiscription;

    private int photoSize;
    private Uri photoUri;
    private boolean propagatingToggleStatu;

    public static void openWithPhotoUri(Activity startingActivity, Uri photoUri) {
        Intent intent = new Intent(startingActivity, PublishActivity.class);
        intent.putExtra(ARG_TAKEN_PHOTO_URI, photoUri);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        photoSize = getResources().getDimensionPixelOffset(R.dimen.publish_photo_thumbnail_size);
        setupToolbar();
        if (savedInstanceState == null) {
            photoUri = getIntent().getParcelableExtra(ARG_TAKEN_PHOTO_URI);
        } else {
            photoUri = savedInstanceState.getParcelable(ARG_TAKEN_PHOTO_URI);
        }
        updateStatusBarColor();

        mIvPhoto.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mIvPhoto.getViewTreeObserver().removeOnPreDrawListener(this);
                loadThumbnailPhoto();
                return true;
            }
        });

    }

    private void setupToolbar() {
        toolbar.setBackgroundColor(0xffffff);
        toolbarTitle.setText("SHARE TO");
        toolbarTitle.setTextColor(Color.parseColor("#333333"));
        toolbar.setNavigationOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClickListener(View v) {
                View peekDecorView = getWindow().peekDecorView();
                if (peekDecorView != null) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
                }
                finish();
                overridePendingTransition(0, R.anim.activity_swipeback_ac_right_out);
                System.gc();
            }
        });
    }

    private void loadThumbnailPhoto() {
        mIvPhoto.setScaleX(0.f);
        mIvPhoto.setScaleY(0.f);
        Picasso.with(this)
                .load(photoUri)
                .centerCrop()
                .resize(photoSize, photoSize)
                .into(mIvPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        mIvPhoto.animate()
                                .scaleX(1.f).scaleY(1.f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(1000)
                                .setStartDelay(250)
                                .start();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatusBarColor() {
        if (UIUtils.isAndroid5()) {
            getWindow().setStatusBarColor(0xff888888);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_publish) {
            bringMainActivityToSingleTop();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void bringMainActivityToSingleTop() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(MainActivity.ACTION_SHOW_LOADING_ITEM);
        intent.setData(photoUri);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_TAKEN_PHOTO_URI, photoUri);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnCheckedChanged(R.id.tbFrients)
    public void onFrientsCheckedChange(boolean checked) {
        // 防止多次点击
        if (!propagatingToggleStatu) {
            propagatingToggleStatu = true;
            mTbDirect.setChecked(!checked);
            propagatingToggleStatu = false;
        }
    }

    @OnCheckedChanged(R.id.tbDirect)
    public void onDirentCheckedChange(boolean checked) {
        if (!propagatingToggleStatu) {
            propagatingToggleStatu = true;
            mTbFrients.setChecked(!checked);
            propagatingToggleStatu = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_swipeback_ac_right_out);
    }
}
