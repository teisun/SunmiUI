package sunmi.sunmidesign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import sunmi.ui.wheelview.BaseWheelAdapter;
import sunmi.ui.wheelview.WheelData;
import sunmi.ui.wheelview.WheelItemLayout;
import sunmi.ui.wheelview.WheelListView;


public class WheelActivity extends Activity {

    private static final String TAG = "WheelActivity";
    List<WheelData> list = new ArrayList<WheelData>();
    WheelData wheelData;//选中的
    WheelListView mWheelView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_wheel);

        initWheel();
    }


    private void initWheel() {
        mWheelView = (WheelListView) findViewById(R.id.wheelview);
        mWheelView.setWheelSize(5);
        MyAdapter myAdapter = new MyAdapter();
        myAdapter.setData(createDatas());
        mWheelView.setAdapter(myAdapter);

        mWheelView.setClipToPadding(false);
        mWheelView.setClipChildren(false);
        mWheelView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = 0; i < mWheelView.getChildCount(); i++) {
                    mWheelView.getChildAt(i).invalidate();
                }
            }
        });
        mWheelView.setOnWheelItemSelectedListener(new WheelListView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                Log.d(TAG, "onItemSelected:"+position);
            }
        });
        mWheelView.setSelection(Integer.MAX_VALUE/4+1);
    }


    private List<WheelData> createDatas() {
        WheelData item0 = new WheelData();
        item0.setColor("F7F709");
        item0.setName("1");
        item0.setId(R.drawable.overcast);
        item0.setId_s(R.drawable.overcast_s);
        list.add(item0);
        WheelData item1 = new WheelData();
        item1.setColor("FF6600");
        item1.setName("2");
        item1.setId(R.drawable.overcast);
        item1.setId_s(R.drawable.overcast_s);
        list.add(item1);
        WheelData item2 = new WheelData();
        item2.setColor("FBFCCF");
        item2.setName("3");
        item2.setId(R.drawable.overcast);
        item2.setId_s(R.drawable.overcast_s);
        list.add(item2);
        WheelData item3 = new WheelData();
        item3.setColor("BEC10B");
        item3.setName("4");
        item3.setId(R.drawable.overcast);
        item3.setId_s(R.drawable.overcast_s);
        list.add(item3);
        WheelData item4 = new WheelData();
        item4.setColor("141463");
        item4.setName("5");
        item4.setId(R.drawable.overcast);
        item4.setId_s(R.drawable.overcast_s);
        list.add(item4);
        return list;
    }

    class MyAdapter extends BaseWheelAdapter<WheelData> {
        @Override
        protected View bindView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                WheelItemLayout view = (WheelItemLayout) View.inflate(getApplicationContext(), R.layout.item_wheel, null);
                view.setParentHeight(parent.getHeight());
                convertView = view;
            }
            Log.d(TAG, "bindView position:"+position);
            WheelData wheelData = mList.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv);
            TextView tv = (TextView) convertView.findViewById(R.id.text);
            if(position == mCurrentPositon){
                imageView.setImageResource(wheelData.getId_s());
            }else {
                imageView.setImageResource(wheelData.getId());
            }
            tv.setVisibility(View.GONE);
//            tv.setText(wheelData.getName());

            return convertView;
        }
    }



    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
