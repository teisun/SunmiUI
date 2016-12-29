package sunmi.sunmiui.edit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class EditPwd extends BaseEdit implements TextWatcher{

    private ImageView eye;
    private View eyeRegion;
    private boolean pwdVisible = false;
    private Drawable eyeNo;
    private Drawable eyeYes;
    private RelativeLayout clear;
    private EditText editText;

    public EditPwd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditPwd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditPwd(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        eyeNo = resources.getDrawable(R.drawable.eye_no);
        eyeYes = resources.getDrawable(R.drawable.eye_yes);
        View view = View.inflate(getContext(), R.layout.edit_pwd_9_16, this);
        clear = (RelativeLayout) view.findViewById(R.id.rel_clear);
        editText = (EditText) view.findViewById(R.id.edit);
        eye = (ImageView) view.findViewById(R.id.eye);
        eyeRegion = view.findViewById(R.id.eye_region);
        editText.addTextChangedListener(this);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        clear.setVisibility(View.GONE);
        eyeRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwdVisible){
                    pwdVisible = false;
                    eye.setImageDrawable(eyeNo);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    pwdVisible = true;
                    eye.setImageDrawable(eyeYes);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                editText.setSelection(editText.getText().length());
            }
        });
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
}
