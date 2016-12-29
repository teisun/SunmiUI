package sunmi.sunmiui.edit;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/16.
 */
public abstract class BaseEdit extends FrameLayout{

    protected EditText editText;
    protected Resources resources;

    public BaseEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public BaseEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public BaseEdit(Context context) {
        super(context);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void init(){
        resources = getResources();
        View view = createView();
        editText = (EditText) view.findViewById(R.id.edit);
    }

    protected abstract View createView();

    public void setHintText(String hintText){
        editText.setHint(hintText);
    }

    public void setTextColor(int color){
        editText.setTextColor(color);
    }

    public void setHintTextColor(int color){
        editText.setHintTextColor(color);
    }

    public void setTextSize(float size){
        editText.setTextSize(size);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Drawable drawable){
        editText.setBackground(drawable);
    }

    public void setWidth(int width){
        ViewGroup.LayoutParams layoutParams = editText.getLayoutParams();
        layoutParams.width = width;
        editText.setLayoutParams(layoutParams);
    }

    public void setHeight(int height){
        ViewGroup.LayoutParams layoutParams = editText.getLayoutParams();
        layoutParams.height = height;
        editText.setLayoutParams(layoutParams);
    }

    public String getText(){
        return editText.getText().toString();
    }

}
