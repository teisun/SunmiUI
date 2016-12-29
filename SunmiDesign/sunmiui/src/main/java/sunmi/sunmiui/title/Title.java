package sunmi.sunmiui.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.TitleView;
import sunmi.sunmiui.utils.Adaptation;

/**
 * Created by Administrator on 2016/6/16.
 */
public class Title extends TitleView {

    private View btnBack,reduce,add,menu,headBg;
    private ImageView imgBack,imgMenu,imgAdd,imgReduce;

    public Title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Title(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Title(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        View view = adapterView();
        btnBack = view.findViewById(R.id.back);
        headBg = view.findViewById(R.id.bg);
        menu = view.findViewById(R.id.rel_menu);
        add = view.findViewById(R.id.rel_add);
        reduce = view.findViewById(R.id.rel_reduce);
        imgBack = (ImageView) view.findViewById(R.id.back_ib);
        imgMenu = (ImageView)view.findViewById(R.id.btn_menu);
        imgReduce = (ImageView)view.findViewById(R.id.btn_reduce);
        imgAdd = (ImageView)view.findViewById(R.id.btn_add);
        return view;
    }

    /**
     * 设置按钮的隐藏 （不设置，默认全部显示）true为隐藏，false为不隐藏
     */
    public void setButtonVisibility(boolean isHideBack,boolean isHideAdd,boolean isHideReduce,boolean isHideMenu){
        if(isHideAdd) {
            add.setVisibility(GONE);
        }else{
            add.setVisibility(VISIBLE);
        }
        if(isHideMenu) {
            menu.setVisibility(GONE);
        }else{
            menu.setVisibility(VISIBLE);
        }
        if(isHideReduce) {
            reduce.setVisibility(GONE);
        }else{
            reduce.setVisibility(VISIBLE);
        }
        if(isHideBack) {
            btnBack.setVisibility(GONE);
        }else{
            btnBack.setVisibility(VISIBLE);
        }
    }

    public void setAllWhite(){
        imgReduce.setImageResource(R.drawable.ic_j_white);
        imgAdd.setImageResource(R.drawable.ic_add_white);
        imgMenu.setImageResource(R.drawable.ic_more_white);
        imgBack.setImageResource(R.drawable.ic_back_white);
    }

    public void setAllBlack(){
        imgReduce.setImageResource(R.drawable.ic_j_black);
        imgAdd.setImageResource(R.drawable.ic_add_black);
        imgMenu.setImageResource(R.drawable.ic_more_black);
        imgBack.setImageResource(R.drawable.ic_back_black);
    }

    public void setImageViewBackDrawable(int drawable) {
        imgBack.setImageResource(drawable);
    }

    public void setImageViewMenuDrawable(int drawable){
        imgMenu.setImageResource(drawable);
    }

    public void setImageViewAddDrawable(int drawable){
        imgAdd.setImageResource(drawable);
    }

    public void setImageVireReduceDrawable(int drawable){
        imgReduce.setImageResource(drawable);
    }

    public void setMenuClickListener(OnClickListener listener4Back,OnClickListener listener4Add,OnClickListener listener4Reduce,OnClickListener listener4Menu){
        menu.setOnClickListener(listener4Menu);
        reduce.setOnClickListener(listener4Reduce);
        add.setOnClickListener(listener4Add);
        btnBack.setOnClickListener(listener4Back);
    }

    public void setHeadBgColor(int color) {
        headBg.setBackgroundColor(color);
    }

    private View adapterView() {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(getContext(), R.layout.title_9_16, this);
            case Adaptation.SCREEN_3_4:
                return View.inflate(getContext(), R.layout.title_9_16, this);
            case Adaptation.SCREEN_4_3:
                return View.inflate(getContext(), R.layout.title_16_9, this);
            case Adaptation.SCREEN_16_9:
                return View.inflate(getContext(), R.layout.title_16_9, this);
            default:
                return View.inflate(getContext(), R.layout.title_9_16, this);
        }
    }


}
