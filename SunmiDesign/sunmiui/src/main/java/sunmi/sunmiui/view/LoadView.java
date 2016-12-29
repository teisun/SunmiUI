package sunmi.sunmiui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/26 17:58
 * 修改人：longx
 * 修改时间：2016/10/26 17:58
 * 修改备注：
 */
public class LoadView extends FrameLayout {

    private View mView;
    private TextView mText;
    private ImageView img;

    public LoadView(Context context) {
        super(context);
        init();
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mView = getView();
        mText = (TextView)mView.findViewById(R.id.txt);
        img = (ImageView)mView.findViewById(R.id.img);
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        img.setAnimation(operatingAnim);
        img.startAnimation(operatingAnim);
    }

    private View getView(){
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(getContext(), R.layout.view_load_9_16, this);
            case Adaptation.SCREEN_3_4:
                return View.inflate(getContext(), R.layout.view_load_9_16, this);
            case Adaptation.SCREEN_4_3:
                return View.inflate(getContext(), R.layout.view_load_16_9, this);
            case Adaptation.SCREEN_16_9:
                return View.inflate(getContext(), R.layout.view_load_16_9, this);
            default:
                return View.inflate(getContext(), R.layout.view_load_9_16, this);
        }
    }

    public void setText(CharSequence text){
        mText.setText(text);
    }
}
