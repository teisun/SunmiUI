package sunmi.sunmiui.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * Created by bps .
 */
public class SunmiListAdapter extends BaseAdapter {
    private Context mContext;
    private List<SunmiListBean> list;

    public SunmiListAdapter(Context context, List<SunmiListBean> list) {
        mContext = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getAdapterView();
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
        tvTitle.setText(list.get(position).title);
        tvContent.setText(list.get(position).content);
        return convertView;
    }

    public View getAdapterView() {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(mContext, R.layout.item_list_v1_9_16, null);
            case Adaptation.SCREEN_3_4:
                return View.inflate(mContext, R.layout.item_list_v1_9_16, null);
            case Adaptation.SCREEN_4_3:
                return View.inflate(mContext, R.layout.item_list_t1_16_9, null);
            case Adaptation.SCREEN_16_9:
                return View.inflate(mContext, R.layout.item_list_t1_16_9, null);
            default:
                return View.inflate(mContext, R.layout.item_list_v1_9_16, null);
        }
    }
}
