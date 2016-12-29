package sunmi.sunmidesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import java.util.List;

import sunmi.ui.banner.BannerLayout;
import sunmi.ui.banner.FadingEdgeView;


public class BannerActivity extends Activity {

    public static final String TAG = "BannerActivity";
    BannerLayout bannerLayout ;
    HorizontalScrollView hsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_act);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        bannerLayout = (BannerLayout) findViewById(R.id.banner);
        bannerLayout.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
//        bannerLayout.setSize(1);
//        bannerLayout.setSize(2);
//        bannerLayout.setSize(3);
        bannerLayout.setSize(4);
        bannerLayout.setBannerDisplayDuration(2000);
        bannerLayout.start();
        List<ImageView> imageViews = bannerLayout.getAllImageView();
        imageViews.get(0).setImageResource(R.drawable.image_6);
        imageViews.get(1).setImageResource(R.drawable.image_7);
        imageViews.get(2).setImageResource(R.drawable.image_8);
        imageViews.get(3).setImageResource(R.drawable.image_9);
        bannerLayout.setOnItemClickListener(new BannerLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                intent.setClass(BannerActivity.this, DialogActivity.class);
                intent.putExtra("pos", ""+position);
                startActivityForResult(intent, 0);
            }
        });

        bannerLayout.setOnPageShowListener(new BannerLayout.OnPageShowListener() {
            @Override
            public void onPageShow(int position) {
                Log.d(TAG, "onPageShow pos:"+position +" getShowingPosition():"+bannerLayout.getShowingPosition());
            }
        });

        FadingEdgeView view = (FadingEdgeView) findViewById(R.id.edge_view);
        view.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bannerLayout.stop();
        bannerLayout.start();
    }

    public void reset(View view){
//        bannerLayout.stop();
//        bannerLayout.start();
    }
}
