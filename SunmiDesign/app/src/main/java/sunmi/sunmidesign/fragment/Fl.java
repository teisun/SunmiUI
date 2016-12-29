package sunmi.sunmidesign.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import sunmi.sunmidesign.R;
import sunmi.sunmiui.dialog.CodeDialog;
import sunmi.sunmiui.dialog.CodeInputSuccess;
import sunmi.sunmiui.dialog.DialogCreater;

/**
 * Created by longx on 2016/11/11.
 */

public class Fl extends Fragment {

    private Button b;
    private View mView;
    private CodeDialog codeDialog;
    String imgBase64 = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAWCAIAAAAJlYj7AAABoUlEQVRIiWNMSUlhIBFsPb/G2zAEwq49kdFsMYNUE/AAFgYGhtmdTiTpkXJbA9fyyJ5k7XhAavk+JmqZRQzY/Y2w0+nqIFeufRAG3GWYTkR3EDGeQAZyB289slcj22VwBk4HYaqgM2BB489MisOqLn3eIto5Yvc3J3hAoDsI02JcTqSiI1y59sG59EjUWNMlWtqAcwk4CBI8FMYXSekSPcoIugZPNqRK+sPpIKyugQjWy6DarcyQzrAIq8VkpD+WsPoWBgb0IEV2DXLqg4hIuWU92zWNbCuRDcQETKsaa/C4hgEjBRBfcuJKf/iTFHqiJpiKkY2DKPa8ewLTlWTnBnyJGj+AW7n7mwyapynJm4QLRvyuYWBgUPd8wnCQOq5hIK9grJf5gmzlze0yZLsGM65ZODe75m0mwTXaFtEMDAxXTyxlYPgLEYGE0O5vTvdyZIh3DSSvITdIIGzGlJQU4pt8yAGQp8WM261kAt8z9iwMhAoGXGDSNWgIPbJXkzt4i4GBgYEBEjZ/yXZQankNI65Gflh9C2YRxYDawocA6rbzAUYiwsFxiYN3AAAAAElFTkSuQmCC";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(R.layout.fragment_fl, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = (Button)mView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog = DialogCreater.createCodeDialog(getActivity());
                codeDialog.setData("请输入验证码", imgBase64, new CodeInputSuccess() {
                    @Override
                    public void codeInputSuccess(String code) {
                        Toast.makeText(getActivity(), "验证码是：" + code, Toast.LENGTH_LONG).show();
                        codeDialog.showLoad();
                        codeDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                codeDialog.show();
            }
        });
    }
}
