package sunmi.sunmidesign.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import sunmi.sunmidesign.R;

public class MyFragmentActivity extends FragmentActivity {
    private FragmentManager fm ;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, new Fl(),fm.getClass().getName());
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }
}
