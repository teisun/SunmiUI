package sunmi.sunmiui.dialog;

/**
 * @author Administrator on 2016/6/14.
 */
public class HintDialog extends BaseDialog {

    private static HintDialog instance;

    private HintDialog() {

    }

    public static HintDialog getInstance() {
        if (instance == null) {
            instance = new HintDialog();
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
