package com.user.hilo.model;

import android.os.Handler;
import android.text.TextUtils;

import com.user.hilo.interfaces.OnLoginFinishedListener;
import com.user.hilo.model.i.ILoginModel;

/**
 * Created by Administrator on 2016/3/16.
 */
public class LoginModel implements ILoginModel {

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
            }, 2 * 100);
        }
    }

    @Override
    public void onDestroy() {

    }
}
