package sunmi.sunmiui.dialog;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class EditPwdDialog extends BaseEditDialog {

    public static final String TAG = "EditPwdDialog";

    private static EditPwdDialog instance;

    private ImageView eye;
    private View eyeRegion;
    private TextView error;
    private Drawable eyeNo;
    private Drawable eyeYes;
    private boolean pwdVisible = false;
    private RelativeLayout clear;

    private EditPwdDialog() {
    }


    public static EditPwdDialog getInstance(){
        if(instance == null){
            instance = new EditPwdDialog();
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {
        eyeNo = resources.getDrawable(R.drawable.eye_no, null);
        eyeYes = resources.getDrawable(R.drawable.eye_yes, null);
        error = (TextView) dialog.findViewById(R.id.error);
        editText = (EditText) dialog.findViewById(R.id.edit);
        eye = (ImageView) dialog.findViewById(R.id.eye);
        eyeRegion = dialog.findViewById(R.id.eye_region);
        clear = (RelativeLayout) dialog.findViewById(R.id.rel_clear);
        clear.setVisibility(View.GONE);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
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
                Log.d(TAG, "onClick:"+pwdVisible);
            }
        });

        editText.addTextChangedListener(textWatcher);
    }


    TextWatcher textWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                error.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()>0){
                clear.setVisibility(View.VISIBLE);
            }else{
                clear.setVisibility(View.GONE);
            }
        }
    };

    /**
     * show error
     * @param errorText
     */
    public void showError(String errorText){
        error.setText(errorText);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDialogCancel() {
        editText.setText("");
        instance = null;
    }

}
