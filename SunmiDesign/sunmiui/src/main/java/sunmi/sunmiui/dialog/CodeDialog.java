package sunmi.sunmiui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.BitmapUtil;

/**
 * @author longx on 2016/11/7.
 */
public class CodeDialog extends BaseDialog {

    private static CodeDialog instance;
    private TextView title, error;
    private ImageView code, clear, load;
    private EditText editText;

    // 是否正在请求
    private boolean isReq;

    public static CodeDialog getInstance() {
        if (instance == null) {
            instance = new CodeDialog();
        }
        return instance;
    }

    @Override
    protected void init() {
        title = (TextView) dialog.findViewById(R.id.txt);
        code = (ImageView) dialog.findViewById(R.id.code);
        editText = (EditText) dialog.findViewById(R.id.editText);
        clear = (ImageView) dialog.findViewById(R.id.clear);
        load = (ImageView) dialog.findViewById(R.id.load);
        error = (TextView) dialog.findViewById(R.id.txt_error);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        error.setVisibility(View.GONE);
        load.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
    }

    public void setData(String titleString, String imgBase64, final CodeInputSuccess listener, View.OnClickListener incodeOnClickListener) {
        isReq = false;
        error.setVisibility(View.GONE);
        load.clearAnimation();
        load.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
        title.setText(titleString);
        code.setImageBitmap(BitmapUtil.stringtoBitmap(imgBase64));
        code.setOnClickListener(incodeOnClickListener);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !isReq) {
                    load.clearAnimation();
                    load.setVisibility(View.GONE);
                    clear.setVisibility(View.VISIBLE);
                }
                if (listener != null && s.length() == 4 && !isReq) {
                    listener.codeInputSuccess(s.toString());
                }
                if (s.length() == 0 && !isReq) {
                    load.clearAnimation();
                    load.setVisibility(View.GONE);
                    clear.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setImgCode(String imgBase64) {
        isReq = false;
        code.setImageBitmap(BitmapUtil.stringtoBitmap(imgBase64));
        load.clearAnimation();
        load.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
    }

    public void showError(String msg) {
        error.setText(msg);
        error.setVisibility(View.VISIBLE);
        load.clearAnimation();
        load.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
        editText.setText("");
    }

    public void hideError() {
        error.setVisibility(View.GONE);
    }

    public void hideLoad() {
        isReq = false;
        load.clearAnimation();
        load.setVisibility(View.GONE);
    }

    public void showLoad() {
        isReq = true;
        load.setVisibility(View.VISIBLE);
        clear.setVisibility(View.GONE);
        Animation operatingAnim = AnimationUtils.loadAnimation(dialog.getContext(), R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        load.setAnimation(operatingAnim);
        load.startAnimation(operatingAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onDialogCancel() {
        load.clearAnimation();
    }

}
