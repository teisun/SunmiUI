package sunmi.sunmiui.dialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import sunmi.sunmiui.R;

/**
 * Created by Administrator on 2016/6/14.
 */
public class EditTextDialog extends BaseEditDialog {

    private static EditTextDialog instance;

    private View clear;

    private EditTextDialog() {
    }


    public static EditTextDialog getInstance(){
        if(instance == null){
            instance = new EditTextDialog();
        }
        return instance;
    }

    @Override
    protected void init() {
        editText = (EditText) dialog.findViewById(R.id.edit);
        clear = dialog.findViewById(R.id.clear);
        editText.addTextChangedListener(textWatcher);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    @Override
    protected void onDialogCancel() {
        editText.setText("");
        instance = null;
    }

    TextWatcher textWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()>0){
                clear.setVisibility(View.VISIBLE);
            }else{
                clear.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * 设置输入框的文字
     * @param content
     */
    public void setText(String content){
        editText.setText(content);
    }


}
