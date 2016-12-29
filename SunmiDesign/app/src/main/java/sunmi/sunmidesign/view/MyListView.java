package sunmi.sunmidesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.List;

import sunmi.sunmidesign.R;

/**
 * Created by longx on 2016/11/16.
 */

public class MyListView extends LinearLayout {

    private View mView;
    private CustomListView myListView;
    private List<String> mock;

    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mView = View.inflate(getContext(), R.layout.layout_list, this);
        myListView = (CustomListView) mView.findViewById(R.id.list);
    }

    public void setListViewAdapter(ListAdapter adapter){
        if(adapter!=null) {
            myListView.setAdapter(adapter);
        }
    }

    public void setOnSlideListener(CustomListView.OnSlideListener onSlideListener){
        myListView.setOnSlideListener(onSlideListener);
    }



}
