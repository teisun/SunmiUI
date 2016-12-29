package sunmi.sunmiui.dialog;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/27 10:43
 * 修改人：longx
 * 修改时间：2016/10/27 10:43
 * 修改备注：
 */
public class HintOneBtnDialog extends BaseDialog {

    private static HintOneBtnDialog instance;

    public static HintOneBtnDialog getInstance(){
        if(instance==null){
            instance = new HintOneBtnDialog();
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
