package sunmi.sunmidesign;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import sunmi.sunmiui.list.SunmiList;
import sunmi.sunmiui.list.SunmiListBean;

public class TitleListActivity extends Activity {
    private SunmiList sunmiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        sunmiList = (SunmiList) this.findViewById(R.id.sunmi_list);
        List<SunmiListBean> sunmiListBeanList = new ArrayList<>();
        for (int i=0;i<5;i++) {
            SunmiListBean sunmiListBean = new SunmiListBean("titile"+i,"content+"+i);
            sunmiListBeanList.add(sunmiListBean);
        }
        sunmiList.setTitleText("标题反馈的回复看可见是的");
        sunmiList.setListAdapter(sunmiListBeanList);
    }
}
