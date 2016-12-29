package sunmi.sunmiui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import sunmi.sunmiui.R;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/26 15:36
 * 修改人：longx
 * 修改时间：2016/10/26 15:36
 * 修改备注：
 */
public class ErrorView extends FrameLayout {

    private View mView;
    private ImageView imgError;
    private TextView textMsg, textT;

    public ErrorView(Context context) {
        super(context);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mView = View.inflate(getContext(), R.layout.view_error, this);
        imgError = (ImageView) mView.findViewById(R.id.img_error);
        textMsg = (TextView) mView.findViewById(R.id.text_error);
        textT = (TextView) mView.findViewById(R.id.text_on);
    }

    public void setErrorTypr(ErroType erroType) {
        switch (erroType) {
            case NET_ERROR:
                set(R.drawable.img_no_net,getContext().getString(R.string.net_error),getContext().getString(R.string.click_screen_try));
                break;
            case SERVER_ERROR:
                set(R.drawable.img_server_error,getContext().getString(R.string.server_error),getContext().getString(R.string.click_screen_try));
                break;
            case NO_ORDER:
                set(R.drawable.img_no_order,getContext().getString(R.string.no_order),"");
                break;
        }
    }

    private void set(int drawable,CharSequence errorMsg,CharSequence prompt){
        imgError.setImageResource(drawable);
        textMsg.setText(errorMsg);
        textT.setText(prompt);
    }

    public void setMsg(CharSequence errorMsg){
        textMsg.setText(errorMsg);
    }

    public void setImgError(int drawable){
        imgError.setImageResource(drawable);
    }

    public void setClickText(CharSequence prompt){
        textT.setText(prompt);
    }

    public enum ErroType {
        NET_ERROR, SERVER_ERROR, NO_ORDER;
    }

}
