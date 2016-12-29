package sunmi.sunmidesign.transitions;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sunmi.sunmidesign.R;

public class TransitionHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_home);
    }

    public void simpleSharedElementTransition(View view) {
        Intent intent = new Intent(this, SimpleSharedTransitionActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.simple_shared), "test");
        startActivity(intent, options.toBundle());

    }

    public void customSharedElementTransition(View view) {
        Intent intent = new Intent(this, CustomSharedTransitionActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.custom_shared), "custom");
        startActivity(intent, options.toBundle());
    }

    public void imgSharedElementTransition(View view) {
        Intent intent = new Intent(this, ImageSharedTransitionActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.img_shared), "img");
        ActivityOptions options2 = ActivityOptions.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityOptions options3 = ActivityOptions.makeSceneTransitionAnimation(this);
        ActivityOptions options4 = ActivityOptions.makeThumbnailScaleUpAnimation(view, BitmapFactory.decodeResource(getResources(), R.mipmap.pic), view.getWidth() / 2, view.getHeight() / 2);
        startActivity(intent, options.toBundle());
//        overridePendingTransition(0, 0);
    }

    public void Palette(View view) {
        //在子线程可以使用同步操作
//        ImageView mImageView = (ImageView) findViewById(R.id.iv);
        final TextView mTextView = (TextView) findViewById(R.id.test);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);

        Palette.generateAsync(bmp, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch a = palette.getVibrantSwatch();//有活力
                Palette.Swatch b = palette.getDarkVibrantSwatch();//有活力 暗色
                Palette.Swatch c = palette.getLightVibrantSwatch();//有活力 亮色
                Palette.Swatch d = palette.getMutedSwatch();//柔和
                Palette.Swatch e = palette.getDarkMutedSwatch();//柔和 暗色
                Palette.Swatch f = palette.getLightMutedSwatch();//柔和 亮色 我们从以上颜色中可以获取到如下颜色值：
                int color1 = a.getBodyTextColor();//内容颜色
                int color2 = a.getTitleTextColor();//标题颜色
                int color3 = a.getRgb();//rgb颜色 </span>
                if(null != a) {
//                    mTextView.setBackgroundColor(swatch.getRgb());
                    mTextView.setTextColor(a.getRgb());
                }
            }
        });
    }
}
