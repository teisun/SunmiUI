package sunmi.sunmidesign;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import sunmi.sunmiui.view.ErrorView;
import sunmi.sunmiui.view.LoadView;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/26 17:10
 * 修改人：longx
 * 修改时间：2016/10/26 17:10
 * 修改备注：
 */
public class ErrorActivity extends Activity {

    private ErrorView errorView;
    private LoadView loadView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        errorView = (ErrorView) this.findViewById(R.id.error);
        loadView = (LoadView) this.findViewById(R.id.load);
        loadView.setVisibility(View.GONE);
    }

    public void showNoNet(View view) {
        errorView.setVisibility(View.VISIBLE);
        errorView.setErrorTypr(ErrorView.ErroType.NET_ERROR);
        loadView.setVisibility(View.GONE);
    }


    public void showServerError(View view) {
        errorView.setVisibility(View.VISIBLE);
        errorView.setErrorTypr(ErrorView.ErroType.SERVER_ERROR);
        loadView.setVisibility(View.GONE);
    }


    public void showNoOrder(View view) {
        errorView.setVisibility(View.VISIBLE);
        loadView.setVisibility(View.GONE);
        errorView.setErrorTypr(ErrorView.ErroType.NO_ORDER);
    }

    public void showLoad(View view) {
        loadView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }


}
