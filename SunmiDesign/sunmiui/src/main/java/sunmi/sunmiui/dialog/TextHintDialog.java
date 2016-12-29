package sunmi.sunmiui.dialog;

/**
 * Created by 47673 on 2016/9/6.
 */
public class TextHintDialog extends BaseDialog {
    private static TextHintDialog instance;

    private TextHintDialog() {
    }


    public static TextHintDialog getInstance() {
        if (instance == null) {
            instance = new TextHintDialog();
        }
        return instance;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onDialogCancel() {
        dialog = null;
        instance = null;
    }
}
