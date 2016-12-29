package sunmi.sunmiui.dialog;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/6/29.
 */
public abstract class BaseEditDialog extends BaseDialog {

    protected EditText editText;

    @Override
    protected void onDialogCancel() {
        editText.setText("");
    }

    /**
     * 获得输入框中的text
     * @return
     */
    public String getText(){
        String text = editText.getText().toString();
        return text;
    }

    /**
     * 设置输入类型
     * @param type
     */
    public void setInputType(int type){
        editText.setInputType(type);
    }

    public void showSoftInput(){
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

}
