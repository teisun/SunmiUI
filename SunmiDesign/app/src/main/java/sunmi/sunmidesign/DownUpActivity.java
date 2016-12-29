package sunmi.sunmidesign;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import sunmi.sunmidesign.adapter.StringListAdapter;
import sunmi.sunmidesign.view.CustomListView;
import sunmi.sunmidesign.view.MyListView;
import sunmi.sunmiui.viewgraoup.TabViewPager;

/**
 * Created by longx on 2016/12/5.
 */

public class DownUpActivity extends Activity implements CustomListView.OnSlideListener, View.OnClickListener, FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {

    private RelativeLayout rel;
    private TranslateAnimation mShowAction, mShowAction1;
    private TranslateAnimation mHiddenAction, mHiddenAction1;
    private MyListView myViewl;
    private List<String> mock;
    private boolean isShow = true;
    private FloatingActionsMenu mFloatingActionsMenu;
    private View mView, head,vvv;
    private AlphaAnimation alphaAnimation, alphaAnimation1;
    private int headHight = 668899774;   //标题栏的默认高度（得到的是跟标题栏一样高度和位置的控件的高度，改控件为隐藏透明状态），给一个永远得不到的默认值
    private ObjectAnimator animatorHeadShow,animatorHeadHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_up);
        initView();
        initData();
        myViewl.setListViewAdapter(new StringListAdapter(mock, this));
        initHideAnimation();
        initShowAnimation();
        initHideAnimation1();
        initShowAnimation1();
        initAlphaAnimationShow();
        initAlphaAnimationHide();
        myViewl.setOnSlideListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v:
                mFloatingActionsMenu.collapse();
                break;
        }

    }

    /**
     * 隐藏下面的菜单
     */
    private void hide() {
        rel.startAnimation(mHiddenAction);
        rel.setVisibility(View.INVISIBLE);

        mFloatingActionsMenu.startAnimation(mHiddenAction1);
        mFloatingActionsMenu.setVisibility(View.INVISIBLE);

        animatorHeadHide = ObjectAnimator.ofFloat(vvv, "translationY", 0, -headHight);
        animatorHeadHide.setDuration(666);
        animatorHeadHide.start();


        animatorHeadHide.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("lllxxx",animation.getAnimatedValue()+"===");
                linearParams.height = headHight+(int)((float)animation.getAnimatedValue());
                head.setLayoutParams(linearParams);
            }
        });

    }

    /**
     * 显示下面菜单
     */
    private void show() {
        rel.startAnimation(mShowAction);
        rel.setVisibility(View.VISIBLE);

        mFloatingActionsMenu.startAnimation(mShowAction1);
        mFloatingActionsMenu.setVisibility(View.VISIBLE);

        animatorHeadShow = ObjectAnimator.ofFloat(vvv, "translationY", -headHight, 0);
        animatorHeadShow.setDuration(666);
        animatorHeadShow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("lllxxx",animation.getAnimatedValue()+"===");
                linearParams.height = headHight+(int)((float)animation.getAnimatedValue());
                head.setLayoutParams(linearParams);
            }
        });
        animatorHeadShow.start();
    }

    /**
     * 初始化UI
     */
    private LinearLayout.LayoutParams linearParams;
    private void initView() {
        vvv = this.findViewById(R.id.vvv);
        head = this.findViewById(R.id.head);
        rel = (RelativeLayout) this.findViewById(R.id.rel);
        myViewl = new MyListView(this);
        mFloatingActionsMenu = (FloatingActionsMenu) this.findViewById(R.id.floatingActionsMenu);
        mView = this.findViewById(R.id.v);
        mView.setOnClickListener(this);
        mFloatingActionsMenu.setOnFloatingActionsMenuUpdateListener(this);
        TabViewPager tabViewPager = (TabViewPager) this.findViewById(R.id.tab_view);
        List<String> s = new ArrayList<>();
        s.add("推荐");
        s.add("反馈");
        List<View> v = new ArrayList<>();
        v.add(myViewl);
        TextView t = new TextView(this);
        t.setText(lxy);
        v.add(t);
        try {
            tabViewPager.setData(s, v);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewTreeObserver viewTreeObserver = head.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(headHight==668899774) {
                    headHight = head.getMeasuredHeight();
                }
                return true;
            }
        });
        linearParams = (LinearLayout.LayoutParams) head.getLayoutParams();
    }

    /**
     * 初始化listview的数据
     */
    private void initData() {
        mock = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            mock.add("龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘龘" + i);
        }
    }

    /**
     * 初始化向下隐藏的动画（悬浮按钮）
     */
    private void initHideAnimation1() {
        mHiddenAction1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, 0, -1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction1.setDuration(1000);
    }

    /**
     * 初始化向上显示的动画（悬浮按钮）
     */
    private void initShowAnimation1() {
        mShowAction1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction1.setDuration(1000);
    }


    /**
     * 初始化向下隐藏的动画（最下面菜单的）
     */
    private void initHideAnimation() {
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, 0, -1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(1000);
    }

    /**
     * 初始化向上显示的动画（最下面菜单的）
     */
    private void initShowAnimation() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(1000);
    }

    /**
     * 渐变显示动画
     */
    private void initAlphaAnimationShow() {
        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(200);
    }

    /**
     * 渐变隐藏动画
     */
    private void initAlphaAnimationHide() {
        alphaAnimation1 = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation1.setDuration(200);
    }

    @Override
    public void up() {
        if (isShow) {
            isShow = false;
            hide();
        }
    }

    @Override
    public void down() {
        if (!isShow) {
            isShow = true;
            show();
        }
    }

    @Override
    public void onMenuExpanded() {
        mView.startAnimation(alphaAnimation);
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMenuCollapsed() {
        mView.startAnimation(alphaAnimation1);
        mView.setVisibility(View.GONE);
    }

    private static final String lxy = "品质稳定\n" +
            "强大的供应链\n" +
            "\n" +
            "\n" +
            "数据安全可靠\n" +
            "商用场景定制的SUNMIUI\n" +
            "\n" +
            "\n" +
            "适配于软件商/O2O平台\n" +
            "Android系统,适配度高\n" +
            "\n" +
            "\n" +
            "提供更多商业合作\n" +
            "专属的App Store分发渠道\n" +
            "\n" +
            "\n" +
            "管理终端商户更高效\n" +
            "配套多种软件工具\n" +
            "\n" +
            "\n" +
            "专业咨询服务团队\n" +
            "高效软硬件技术团队";
}
