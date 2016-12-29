package sunmi.sunmidesign;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sunmi.ui.flexible.FlexibleListView;
import sunmi.ui.flexible.FlexibleScrollView;


public class FlexibleActivity extends Activity {

    FlexibleListView listView;
    FlexibleScrollView scrollView;
    Bitmap bmp;
    List<Item> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_6);
        listView = (FlexibleListView) findViewById(R.id.flexibleListView);
        //        listView.setTopFlexible(false);
        scrollView = (FlexibleScrollView) findViewById(R.id.scrollView);
        scrollView.setResistance(0);//设置阻力
        init();
    }

    private void init() {
        for(int i=0; i<40; i++){
            Item item = new Item();
            list.add(item);
        }
        listView.setAdapter(new SunmiAdapter());
    }

    class SunmiAdapter extends BaseAdapter{

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
            if(convertView == null){
                convertView = View.inflate(getBaseContext(), R.layout.item_list, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            iv.setImageBitmap(bmp);
            return convertView;
        }
    }

    class Item{

        String text = "I can flexible!";

    }
}
