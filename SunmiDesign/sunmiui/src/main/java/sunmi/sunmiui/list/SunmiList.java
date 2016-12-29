package sunmi.sunmiui.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * Created by bps .
 */
public class SunmiList extends LinearLayout {

    private ListView mListView;
    private TextView mTvTitle;

    public SunmiList(Context context) {
        super(context);
        initView();
    }

    public SunmiList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SunmiList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView( ) {
        View view = getAdapterView();
        this.addView(view);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mListView = (ListView) view.findViewById(R.id.list_view);
    }

    /**
     * 适配
     * @return
     */
    private View getAdapterView( ) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(getContext(), R.layout.list_v1_9_16, null);
            case Adaptation.SCREEN_3_4:
                return View.inflate(getContext(), R.layout.list_v1_9_16, null);
            case Adaptation.SCREEN_4_3:
                return View.inflate(getContext(), R.layout.list_t1_16_9, null);
            case Adaptation.SCREEN_16_9:
                return View.inflate(getContext(), R.layout.list_t1_16_9, null);
            default:
                return View.inflate(getContext(), R.layout.list_v1_9_16, null);
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置条目
     * @param list
     */
    public void setListAdapter(List<SunmiListBean> list) {
        mListView.setAdapter(new SunmiListAdapter(getContext(), list));
    }

    /**
     * 条目点击
     * @param listener
     */
    public void onItemClick(AdapterView.OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }

}
