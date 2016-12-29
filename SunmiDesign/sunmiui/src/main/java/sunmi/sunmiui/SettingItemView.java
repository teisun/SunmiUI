package sunmi.sunmiui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 47673 on 2016/7/8.
 */
public abstract class SettingItemView extends FrameLayout{
    protected TextView topTextView,bottomTextView;
    protected View bg;
    protected LinearLayout linearLayout;

    public SettingItemView(Context context) {
        super(context);
        init();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init(){
        View view = createView();
        topTextView = (TextView) view.findViewById(R.id.text_top);
        bottomTextView = (TextView) view.findViewById(R.id.text_bottom);
        bg = view.findViewById(R.id.bg);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
    }

    /**
     * 设置TextView显示的文字
     * @param topStr 上TextView要显示的文字，为空时上TextView则影藏
     * @param bottomStr 下TextView要显示的文字，为空时下TextView则影藏
     */
    public void setText(String topStr,String bottomStr){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        linearLayout.setLayoutParams(lp);
        if(TextUtils.isEmpty(topStr) && !TextUtils.isEmpty(bottomStr)){
            topTextView.setVisibility(View.GONE);
            bottomTextView.setText(bottomStr);
        }else if(!TextUtils.isEmpty(topStr) && TextUtils.isEmpty(bottomStr)){
            bottomTextView.setVisibility(View.GONE);
            topTextView.setText(topStr);
        }else if(!TextUtils.isEmpty(topStr) && !TextUtils.isEmpty(bottomStr)){
            topTextView.setText(topStr);
            bottomTextView.setText(bottomStr);
        }
    }

    /**
     * 设置框背景
     * @param drawable 背景
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Drawable drawable){
        bg.setBackground(drawable);
    }

    public void setTextColor(int topColor,int bottomColor){
        topTextView.setTextColor(topColor);
        bottomTextView.setTextColor(bottomColor);
    }

    public void setTextSize(float topSize,float bottomSize){
        topTextView.setTextSize(topSize);
        bottomTextView.setTextSize(bottomSize);
    }

    protected abstract View createView();
    public void setWidth(int width){
        ViewGroup.LayoutParams layoutParams = bg.getLayoutParams();
        layoutParams.width = width;
        bg.setLayoutParams(layoutParams);
    }

    public void setHeight(int height){
        ViewGroup.LayoutParams layoutParams = bg.getLayoutParams();
        layoutParams.height = height;
        bg.setLayoutParams(layoutParams);
    }

    public void setOnclickListener(OnClickListener onclickListener){
        bg.setOnClickListener(onclickListener);
    }

    public void setClickable(boolean clickable){
        bg.setClickable(clickable);
    }
}
