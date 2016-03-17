package com.user.hilo.activity.model.iml;

import android.os.Handler;
import android.text.TextUtils;

import com.user.hilo.activity.interfaces.OnLoginFinishedListener;
import com.user.hilo.activity.model.LoginModel;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginModelIml implements LoginModel {

    public static final int VALIDATE_SUCCESS = 1;
    public static final int SERVER_ERROR = 2;

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener onLoginFinishedListener) {
        if (onLoginFinishedListener == null)
            throw new NullPointerException("must be implements OnLoginFinishedListener");

        boolean error = false;
        if (TextUtils.isEmpty(username)) {
            onLoginFinishedListener.onUsernameError();
            error = true;
        }
        if (TextUtils.isEmpty(password)) {
            onLoginFinishedListener.onPasswordError();
            error = true;
        }
        if (!error) {
            onLoginFinishedListener.validateStatus(VALIDATE_SUCCESS);

            // Mock login. I'm creating a handler to delay the answer a couple of seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // login success
                    onLoginFinishedListener.onSuccess();
                    // login error
                    // onLoginFinishedListener.validateStatus(SERVER_ERROR);
                }
            }, 2 * 1000);
        }
    }

    @Override
    public void onDestroy() {

    }
}
