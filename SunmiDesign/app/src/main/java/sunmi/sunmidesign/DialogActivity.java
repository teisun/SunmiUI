package sunmi.sunmidesign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogActivity extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        tv = (TextView) findViewById(R.id.tv);
        String pos = getIntent().getStringExtra("pos");
        tv.setText("点击了第"+pos+"个 Item");
    }

    public void close(View v){
        finish();
    }
}
