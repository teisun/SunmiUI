package sunmi.sunmidesign.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sunmi.sunmidesign.R;

/**
 * Created by longx on 2016/12/5.
 */

public class StringListAdapter  extends BaseAdapter {

    private List<String> datas;
    private Context mContext;

    public StringListAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holderView = null;
        if (convertView == null) {
            holderView = new ViewHolder ();
            convertView = View.inflate(mContext, R.layout.item_string_list, null);
            holderView.t = (TextView) convertView.findViewById(R.id.t);
            convertView.setTag(holderView);
        }else{
            holderView =(ViewHolder) convertView.getTag();
        }
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation mHiddenAction = new  TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(1000);
        animationSet.addAnimation(mHiddenAction);
        holderView.t.startAnimation(animationSet);


        holderView.t.setText(datas.get(position));
        return convertView;
    }

    public class ViewHolder  {
        TextView t;
    }
}
