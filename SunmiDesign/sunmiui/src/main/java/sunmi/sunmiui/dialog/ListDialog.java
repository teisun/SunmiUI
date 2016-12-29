package sunmi.sunmiui.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;
import sunmi.sunmiui.utils.LogUtil;

/**
 * Created by Administrator on 2016/6/14.
 */
public class ListDialog extends BaseDialog {

    private static ListDialog instance;

    private LinearLayout list;

    private ScrollView scrollView;

    private ItemClickListener itemClickListener;

    private ListDialog() {
    }

    public static ListDialog getInstance(){
        if(instance == null){
            instance = new ListDialog();
        }
        return instance;
    }

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {
        scrollView = (ScrollView) dialog.findViewById(R.id.scrollView);
        list = (LinearLayout) dialog.findViewById(R.id.list);
        View cancel =  dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    @Override
    protected void onDialogCancel() {
        dialog = null;
        instance = null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setData(String[] items){
        list.removeAllViews();
        float itemHeight = getHeight();
        LogUtil.d("ListDialog",itemHeight+"");
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) scrollView.getLayoutParams();
        int height = 0;
        for(int i=0;i<items.length;i++){
            String itemStr = items[i];
            View view = inflatView();
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(itemStr);
            view.setTag(i);
            view.setOnClickListener(new ItemClick());
            list.addView(view);
            if(i<=3){
                height+=itemHeight;
            }

        }
        layoutParams.height = height + 10;
        scrollView.setLayoutParams(layoutParams);
    }

    class ItemClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            if(itemClickListener!=null){
                itemClickListener.OnItemClick(pos);
            }
        }
    }

    public interface ItemClickListener{
        void OnItemClick(int position);
    }

    private  float getHeight() {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return resources.getDimension(R.dimen.itemHeight);
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return resources.getDimension(R.dimen.itemHeightT1);
        }
        return 0;
    }

    private View inflatView(){
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return View.inflate(dialog.getContext(), R.layout.item_text_9_16, null);
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return View.inflate(dialog.getContext(), R.layout.item_text_16_9, null);
        }
        return null;
    }
}
