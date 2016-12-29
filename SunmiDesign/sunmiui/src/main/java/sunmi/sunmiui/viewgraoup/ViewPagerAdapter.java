package sunmi.sunmiui.viewgraoup;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/27 12:37
 * 修改人：longx
 * 修改时间：2016/10/27 12:37
 * 修改备注：
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<String> stringList;
    private List<View> views;

    public ViewPagerAdapter(List<String> stringList, List<View> views) {
        this.stringList = stringList;
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 插入一个View到viewpager中
     *
     * @param view
     * @return
     */
    public boolean insert(View view) {
        if (this.views == null) {
            this.views = new ArrayList<View>();
        }

        return this.views.add(view);
    }
}
