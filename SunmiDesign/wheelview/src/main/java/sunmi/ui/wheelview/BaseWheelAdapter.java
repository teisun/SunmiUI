
package sunmi.ui.wheelview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

/**
 * 滚轮抽象数据适配器
 *
 * @author longtao.li
 */
public abstract class BaseWheelAdapter<T> extends BaseAdapter {

    private static final String TAG = "BaseWheelAdapter";

    protected List<T> mList = null;

    protected boolean mLoop = IWheelView.LOOP;

    protected int mCurrentPositon = -1;

    protected abstract View bindView(int position, View convertView, ViewGroup parent);

    /**
     * 设置当前刻度
     *
     * @param position
     */
    public final void setCurrentPosition(int position) {
        mCurrentPositon = position;
    }

    @Override
    public final int getCount() {
        if (mLoop) {
            return Integer.MAX_VALUE;
        }
        return !isEmpty(mList) ? (mList.size()) : 0;
    }

    @Override
    public final long getItemId(int position) {
        return !isEmpty(mList) ? position % mList.size() : position;
    }

    @Override
    public final T getItem(int position) {
        return !isEmpty(mList) ? mList.get(position % mList.size()) : null;
    }


    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView position:"+position);
        if (mLoop) {
            position = position % mList.size();
        }
        View view;
        if (position == -1) {
            view = bindView(0, convertView, parent);
        } else {
            view = bindView(position, convertView, parent);
        }
        if (!mLoop) {
            if (position == -1) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }


    public final BaseWheelAdapter setLoop(boolean loop) {
        if (loop != mLoop) {
            mLoop = loop;
            super.notifyDataSetChanged();
        }
        return this;
    }


    public final BaseWheelAdapter setData(List<T> list) {
        mList = list;
        super.notifyDataSetChanged();
        return this;
    }

    public final List<T> getData(){
        return mList;
    }


    /**
     * 判断集合是否为空
     *
     * @param c
     * @param <V>
     * @return
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    /**
     * 获得滚轮数据总数
     *
     * @return
     */
    public int getWheelCount() {
        return !isEmpty(mList) ? mList.size() : 0;
    }
}
