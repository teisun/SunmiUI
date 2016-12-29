package sunmi.sunmiui.dialog;

import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class LoadingDialog extends BaseDialog {

    private static LoadingDialog instance;
    private TextView loadingMsg;
    private View loadingProgress;
    private LoadingDialog() {
    }


    public static LoadingDialog getInstance(){
        if(instance == null){
            instance = new LoadingDialog();
        }
        return instance;
    }

    @Override
    protected void init() {
        loadingProgress = dialog.findViewById(R.id.progress);
        loadingMsg = (TextView) dialog.findViewById(R.id.msg);
    }

    @Override
    protected void onDialogCancel() {
        dialog = null;
        instance = null;
    }

    @Override
    public void show() {
        super.show();
        if(dialog!=null&&dialog.getContext()!=null) {
            Animation operatingAnim = AnimationUtils.loadAnimation(dialog.getContext(), R.anim.loading_anim);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            loadingProgress.setAnimation(operatingAnim);
            loadingProgress.startAnimation(operatingAnim);
        }
    }

    public void setLoadingMsgSize(int size){
        loadingMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }
}
