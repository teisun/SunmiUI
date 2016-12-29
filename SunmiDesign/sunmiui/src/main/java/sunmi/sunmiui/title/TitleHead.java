package sunmi.sunmiui.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * 项目名称：SunmiDesign
 * 类描述：一级标题（仅有文字无图标）
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/26 11:07
 * 修改人：longx
 * 修改时间：2016/10/26 11:07
 * 修改备注：
 */
public class TitleHead extends FrameLayout{

    private View mView;
    private TextView title;

    public TitleHead(Context context) {
        super(context);
        init();
    }

    public TitleHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mView = getView();
        title = (TextView) mView.findViewById(R.id.txt);
    }

    private View getView(){
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(getContext(), R.layout.title_head_9_16, this);
            case Adaptation.SCREEN_3_4:
                return View.inflate(getContext(), R.layout.title_head_9_16, this);
            case Adaptation.SCREEN_4_3:
                return View.inflate(getContext(), R.layout.title_head_16_9, this);
            case Adaptation.SCREEN_16_9:
                return View.inflate(getContext(), R.layout.title_head_16_9, this);
            default:
                return View.inflate(getContext(), R.layout.title_head_9_16, this);
        }
    }

    public void setTextColor(int color){
        title.setTextColor(color);
    }

    public void setBackground(int res){
        mView.setBackgroundColor(res);
    }
}
