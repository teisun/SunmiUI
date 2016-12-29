package sunmi.sunmiui.edit;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class Edit extends BaseEdit implements TextWatcher,View.OnClickListener{

    private EditText edittext;
    private RelativeLayout clear;

    public Edit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Edit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Edit(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.edit_text_9_16, this);
        edittext = (EditText) view.findViewById(R.id.edit);
        edittext.addTextChangedListener(this);
        clear = (RelativeLayout) view.findViewById(R.id.rel_clear);
        clear.setOnClickListener(this);
        clear.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()>0){
            clear.setVisibility(View.VISIBLE);
        }else{
            clear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        edittext.setText("");
    }
}
