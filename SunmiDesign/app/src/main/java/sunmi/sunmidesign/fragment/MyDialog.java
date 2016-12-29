package sunmi.sunmidesign.fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import sunmi.sunmidesign.R;

/**
 * Created by longx on 2016/11/11.
 */

public class MyDialog extends Dialog {

    private Context mContext;
    private static MyDialog mDialog = null;

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

    public static MyDialog creatDialog(Context context, final View.OnClickListener onClickListener) {
        mDialog = new MyDialog(context);
        mDialog.setContentView(R.layout.dialog_ed);
        mDialog.findViewById(R.id.button2).setOnClickListener(onClickListener);
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(false);
        return mDialog;
    }

}
