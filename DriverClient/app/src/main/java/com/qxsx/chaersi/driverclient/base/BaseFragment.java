package com.qxsx.chaersi.driverclient.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.qxsx.chaersi.driverclient.utils.OnClickUtils;

/**
 * Created by Chaersi on 16/7/1.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private ProgressDialog progressDialog;

    @Override
    public void onClick(View v) {
        if (OnClickUtils.isFastDoubleClick()) {
            return;
        }
        onClickListener(v);
    }

    public abstract void onClickListener(View v);

    private Toast toast;
    public void showTips(String tips) {
        if (toast != null) {
            toast.setText(tips);
            toast.show();
        } else {
            toast = Toast.makeText(getActivity().getApplicationContext(), tips, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 加载进度条
     * @param content
     */
    public void showProgressDialog(String content) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage(content);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
