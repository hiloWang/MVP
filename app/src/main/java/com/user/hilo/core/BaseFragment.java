package com.user.hilo.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.user.hilo.utils.ToastUtils;

/**
 * Created by hilo on 16/2/25.
 */
public abstract class BaseFragment extends Fragment {

    protected View self;
    /**
     * 临时的Bundle对象，用于存储用户的数据，如果用户保存了数据，那么最终将该对象put到arguments的bundle对象中
     */
    private Bundle savedStateBundle;
    private static final String ARGS_BUNDLE_KEY = "internalSavedViewState8954201239547";

    /**
     * Fragment 要有一个默认的构造函数，Fragment在重新创建/还原的时候会调用默认的构造函数，会在重新创建时将状态保存到一个包（Bundle）对象，
     * 当还原时，将参数包还原到新建的Fragment。该Fragment执行的后续回调能够访问这些参数，可以将碎片还原到上一个状态；
     */
    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must be implement BaseFragment of callbacks method.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.self == null) {
            this.self = inflater.inflate(this.getLayoutId(), container, false);
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        this.initViews(this.self, savedInstanceState);
        this.initData();
        this.initListeners();
        return this.self;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            initData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState2ArgumentsBundle();
    }

    @Override
    public void onPause() {
        beforePause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        afterResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveState2ArgumentsBundle();
    }

    @Override
    public void onDestroy() {
        beforeDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    protected void afterResume() {
    }

    protected void beforePause() {
    }

    protected void beforeDestroy() {
    }

    /**
     * Fill in layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * Initialize the view in the layout
     *
     * @param self               self
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(View self, Bundle savedInstanceState);

    /**
     * Initialize the View of the listener
     */
    protected abstract void initListeners();

    /**
     * Initialize the Activity data
     */
    protected abstract void initData();

    /**
     * 恢复数据回调
     *
     * @param savedInstanceState 存储在Arguments里的onSaveState Bundle对象
     */
    protected abstract void onRestoreState(Bundle savedInstanceState);

    /**
     * 保存数据回调
     *
     * @param savedInstanceState
     */
    protected abstract void onSavedState(Bundle savedInstanceState);

    /**
     * 保存数据
     */
    private void saveState2ArgumentsBundle() {
        // 多次屏幕旋转时view会为null，不为空时才存储数据
        if (getView() != null)
            savedStateBundle = getSavedStateBundle();
        if (savedStateBundle != null) {
            Bundle argsBundle = getArguments();
            argsBundle.putBundle(ARGS_BUNDLE_KEY, savedStateBundle);
        }
    }

    /**
     * 用于判断用户是否保存数据到临时的bundle对象中，如果返回时临时的Bundle对象不为null，那么会将该bundle存储到Fragment的Arguments的Bundle中;
     *
     * @return 临时bundle
     */
    private Bundle getSavedStateBundle() {
        Bundle initSavedStateBundleData = new Bundle();
        onSavedState(initSavedStateBundleData);
        return initSavedStateBundleData;
    }

    /**
     * 恢复数据
     *
     * @return
     */
    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedStateBundle = b.getBundle(ARGS_BUNDLE_KEY);
        if (savedStateBundle != null) {
            onRestoreState(savedStateBundle);
            return true;
        }
        return false;
    }

    /**
     * Find the view by id
     *
     * @param id  id
     * @param <V> V
     * @return V
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int id) {
        return (V) this.self.findViewById(id);
    }

    public void showToast(String msg) {
        this.showToast(msg, Toast.LENGTH_SHORT);
    }

    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this.getActivity(), msg, duration);
        } else {
            ToastUtils.show(this.getActivity(), msg, ToastUtils.LENGTH_SHORT);
        }
    }

    public void showToast(int resId) {
        this.showToast(resId, Toast.LENGTH_SHORT);
    }


    public void showToast(int resId, int duration) {
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this.getActivity(), resId, duration);
        } else {
            ToastUtils.show(this.getActivity(), resId, ToastUtils.LENGTH_SHORT);
        }
    }

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    protected Callbacks mCallbacks = sDummyCallbacks;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(Activity activity, int position, String text);

        int getSelectedFragment();

        Activity getActivityCallBacks();
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Activity activity, int position, String text) {
        }

        @Override
        public int getSelectedFragment() {
            return 0;
        }

        @Override
        public Activity getActivityCallBacks() {
            return null;
        }

    };
}
