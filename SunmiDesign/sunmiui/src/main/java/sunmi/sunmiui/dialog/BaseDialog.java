package sunmi.sunmiui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.WindowManager;

/**
 * @author Administrator on 2016/6/14.
 */
abstract class BaseDialog implements DialogInterface {

    private static final String TAG = "sunmiui";
    protected Dialog dialog;
    protected Resources resources;
    protected OnCancelListener onCancelListener;

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        this.resources = dialog.getContext().getResources();
        this.dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (onCancelListener != null) onCancelListener.onCancel(BaseDialog.this);
                BaseDialog.this.dialog = null;
                onDialogCancel();
            }
        });
        init();
    }

    public void setSystemDialog(Dialog dialog) {
        this.dialog = dialog;
        this.dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        this.resources = dialog.getContext().getResources();
        this.dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (onCancelListener != null) onCancelListener.onCancel(BaseDialog.this);
                BaseDialog.this.dialog = null;
                onDialogCancel();
            }
        });
        init();
    }


    public void show() {
        if (dialog != null && !dialog.isShowing()){
            try{
                dialog.show();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG, "show dialog fail!");
            }

        }
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
    }

    @Override
    public void cancel() {
        if (dialog != null) {
            Log.i("BaseDialog", "dialog dismiss");
            dialog.cancel();
        }
    }

    public void onDestory(){
        if(dialog!=null){
            dialog = null;
        }
    }

    @Override
    public void dismiss() {
        cancel();
    }

    public boolean hasDialog() {
        return dialog != null;
    }

    /**
     * 点击空白是否取消
     */
    public void setCanceledOnTouchOutside(boolean b) {
        dialog.setCanceledOnTouchOutside(b);
    }

    /**
     * 点击返回按钮是否取消
     */
    public void setCancelable(boolean b) {
        dialog.setCancelable(b);
    }

    protected abstract void init();

    protected abstract void onDialogCancel();

}
