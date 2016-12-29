package sunmi.sunmiui.viewgraoup;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/27 11:45
 * 修改人：longx
 * 修改时间：2016/10/27 11:45
 * 修改备注：
 */
public class TabViewPager extends FrameLayout {

    private View mView;
    private ViewPager viewPager;
    private int textSize;
    private PagerSlidingTabStrip tab;
    private RelativeLayout rel_tab;

    public TabViewPager(Context context) {
        super(context);
        init();
    }

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mView = adapterView();
        viewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        tab = (PagerSlidingTabStrip) mView.findViewById(R.id.tab);
        rel_tab = (RelativeLayout)mView.findViewById(R.id.rel_tab);
        tab.setTextSize(textSize);
        tab.setBackgroundColor(Color.parseColor("#2878f0"));
    }

    public void setSelectedTabTextColorResource(int resId ){
        tab.setSelectedTabTextColorResource(resId);
    }

    public void setSelectedTabTextColor(int textColor){
        tab.setSelectedTabTextColor(textColor);
    }

    public void setBackgroundResource(int resId ){
        tab.setBackgroundResource(resId);
        rel_tab.setBackgroundResource(resId);

    }

    public void setBackgroundColor(int textColor){
        tab.setBackgroundColor(textColor);
        rel_tab.setBackgroundColor(textColor);
    }

    public void setIndicatorColorResource(int resId ){
        tab.setIndicatorColorResource(resId);
    }

    public void setIndicatorColor(int textColor){
        tab.setIndicatorColor(textColor);
    }

    public void setTextColorResource(int resId ){
        tab.setTextColorResource(resId);
    }

    public void setTextColor(int textColor){
        tab.setTextColor(textColor);
    }

    public void setData(List<String> tabStringList, List<View> viewList) throws Exception{
        String error = null;
        if(viewList==null||tabStringList==null){
            error ="参数都不能为null";
        }
        if((viewList!=null&&viewList.size()==0)||(tabStringList!=null&&tabStringList.size()==0)){
            error ="参数的size必须大于0";
        }
        if(viewList!=null&&tabStringList!=null &&viewList.size()==0 &&tabStringList.size()==0 &&viewList.size()!=tabStringList.size()){
            error ="参数的size必须相等";
        }
        if(!TextUtils.isEmpty(error)) {
            try {
                throw new Exception(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        viewPager.setAdapter(new ViewPagerAdapter(tabStringList, viewList));
        tab.setViewPager(viewPager);
    }

    private View adapterView() {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                textSize = 13;
                return View.inflate(getContext(), R.layout.view_tab_view_pager_9_16, this);
            case Adaptation.SCREEN_3_4:
                textSize = 13;
                return View.inflate(getContext(), R.layout.view_tab_view_pager_9_16, this);
            case Adaptation.SCREEN_4_3:
                textSize = 16;
                return View.inflate(getContext(), R.layout.view_tab_view_pager_16_9, this);
            case Adaptation.SCREEN_16_9:
                textSize = 16;
                return View.inflate(getContext(), R.layout.view_tab_view_pager_16_9, this);
            default:
                textSize = 13;
                return View.inflate(getContext(), R.layout.view_tab_view_pager_9_16, this);
        }
    }

}
