package sunmi.sunmidesign;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sunmi.ui.refreshview.PullToRefreshSwipeLayout;


public class RefreshListActivity extends Activity {

    private PullToRefreshSwipeLayout mSwipeRefreshLayout;
    private ListView mListView;

    private RefreshAdapter mAdapter;
    private List<String> mSourceData;

    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshlist);
        mSwipeRefreshLayout = (PullToRefreshSwipeLayout) findViewById(R.id.swipe_refresh);
        mListView = (ListView) findViewById(R.id.list_view);

        mSourceData = initData();
        list.addAll(mSourceData);

        mAdapter = new RefreshAdapter(this, mSourceData);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        list.clear();
                        mSourceData.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });

        mSwipeRefreshLayout.setOnPullUpListener(new PullToRefreshSwipeLayout.OnPullUpListener() {
            @Override
            public void onPullUp() {
                if (list.size() > 15) {
                    mSwipeRefreshLayout.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "没有数据了" + list.size(), Toast.LENGTH_SHORT).show();
                            mSwipeRefreshLayout.setViewStatus(PullToRefreshSwipeLayout.NONE);
                        }
                    }, 2000);
                } else {
                    for (int i = 0; i < 10; i++) {
                        list.add("onLoad：" + i);
                    }
                    mSwipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setViewStatus(PullToRefreshSwipeLayout.NONE);
                            mSourceData.clear();
                            mSourceData.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 2000);
                }
            }
        });
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        list.add("阿妹");
        list.add("阿郎");
        list.add("陈奕迅");
        list.add("周杰伦");
        list.add("成龙");
        list.add("王力宏");
        return list;
    }

}
