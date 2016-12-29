package sunmi.sunmidesign;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import sunmi.sunmidesign.fragment.MyFragmentActivity;
import sunmi.sunmidesign.transitions.TransitionHomeActivity;
import sunmi.sunmiui.button.ButtonRectangular;
import sunmi.sunmiui.button.ButtonRoundCorner;
import sunmi.sunmiui.button.ButtonSwitch;
import sunmi.sunmiui.dialog.CodeDialog;
import sunmi.sunmiui.dialog.CodeInputSuccess;
import sunmi.sunmiui.dialog.HintOneBtnDialog;
import sunmi.sunmiui.dialog.TextHintDialog;
import sunmi.sunmiui.title.EnterItem;
import sunmi.sunmiui.title.SwitchItem;
import sunmi.sunmiui.title.Title;
import sunmi.sunmiui.dialog.DialogCreater;
import sunmi.sunmiui.dialog.EditPwdDialog;
import sunmi.sunmiui.dialog.EditTextDialog;
import sunmi.sunmiui.dialog.HintDialog;
import sunmi.sunmiui.dialog.ListDialog;
import sunmi.sunmiui.dialog.LoadingDialog;
import sunmi.sunmiui.title.TitleHead;
import sunmi.sunmiui.utils.Adaptation;
import sunmi.sunmiui.window.VersionView;

public class MainActivity extends Activity {
    TextHintDialog textHintDialog;
    HintDialog dialog;
    EditTextDialog editTextDialog;
    EditPwdDialog editPwdDialog;
    ListDialog listDialog;
    LoadingDialog loadingDialog;
    SwitchItem switchButtonView;
    Title title;
    CodeDialog codeDialog;
    ButtonSwitch buttonSwitch;
    ButtonRectangular buttonRectangular;
    ButtonRoundCorner buttonRoundCorner;
    EnterItem enterItem;
    TitleHead titleHead;
    HintOneBtnDialog hintOneBtnDialog;
    VersionView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adaptation.init(this);
        setContentView(R.layout.activity_main);
        mView = new VersionView(this);
        title = (Title) this.findViewById(R.id.title);
        switchButtonView = (SwitchItem) this.findViewById(R.id.switch_button_view);
        switchButtonView.setText("", "texttttt");
        titleHead = (TitleHead) this.findViewById(R.id.title_head);
        titleHead.setTextColor(Color.parseColor("#ffffff"));
        titleHead.setBackground(Color.parseColor("#2878ef"));
        enterItem = (EnterItem) this.findViewById(R.id.enter_item);
        title.setAllWhite();
        title.setButtonVisibility(true, false, false, false);
        title.setMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "back click", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "add click", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "reduce click", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "menu click", Toast.LENGTH_SHORT).show();
            }
        });
        buttonSwitch = (ButtonSwitch) findViewById(R.id.buttonswitch);

        buttonSwitch.setOnCheckedChangeListener(checkedChangeListener);
        Log.e("buttonSwitch", buttonSwitch.isChecked() + "——----");
        buttonRectangular = (ButtonRectangular) this.findViewById(R.id.buttonBM);
        buttonRoundCorner = (ButtonRoundCorner) this.findViewById(R.id.buttonRC);

        final boolean isCheck = false;

        enterItem.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了EnterIntem", Toast.LENGTH_LONG).show();
            }
        });
        switchButtonView.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了EnterIntem", Toast.LENGTH_LONG).show();
                switchButtonView.setChecked(!switchButtonView.isChecked());
            }
        });
//        buttonRoundCorner.setBackgroundColor(getResources().getColor(R.color.white), getResources().getColor(R.color.black));
    }

    public void showEditPwd(View view) {
        editPwdDialog = DialogCreater.createEditPwdDialog(this, "取消", "退出登录", "请输入密码", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPwdDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPwdDialog.showError("密码错误,请重输!");
//                editPwdDialog.dismiss();
            }
        });
        editPwdDialog.show();
    }

    public void showEditText(View view) {
        editTextDialog = DialogCreater.createEditTextDialog(this, "取消", "确定", "输入店铺名", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDialog.dismiss();
            }
        });
        editTextDialog.show();
    }

    public void showHint(View view) {
        dialog = DialogCreater.createHintDialog(this, R.drawable.warning, "lefttleftleft", "tttatatat", "我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        }, null,false);
        dialog.show();
    }

    public void showListDialog(View view) {
        final String[] items = new String[]{"item1item1item1item1item1item1item1item1item1item1item1item1item1item1", "item2", "item3", "item4", "item5", "item6"};
        listDialog = DialogCreater.createListDialog(this, "设置为", "取消", items);
        listDialog.setItemClickListener(new ListDialog.ItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(MainActivity.this, "item ： pos:" + position + ", name:" + items[position], Toast.LENGTH_LONG).show();
            }
        });
        listDialog.show();
    }

    public void showTextHint(View view) {
        textHintDialog = DialogCreater.createTextHintDialog(this, "", "lefttleftleft", "tttatatat", "我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textHintDialog.dismiss();
            }
        }, null,false);
        textHintDialog.show();
    }

    public void showLoadingDialog(View view) {
        loadingDialog = DialogCreater.createLoadingDialog(this, "正在校验更新包，请稍后...");
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    public void showError(View view) {
        Intent intent = new Intent(this, ErrorActivity.class);
        startActivity(intent);
    }

    public void buttonBM(View view) {
//        buttonRectangular.setEnabled(!buttonRectangular.isEnabled());
    }

    public void buttonRC(View view) {
        Toast.makeText(MainActivity.this, "buttonRC click", Toast.LENGTH_LONG).show();
    }

    public void showTab(View view) {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }

    public void openMyFragmentActivity(View view) {
        Intent intent = new Intent(this, MyFragmentActivity.class);
        startActivity(intent);
    }

    public void showTitleList(View view) {
        Intent intent = new Intent(this, TitleListActivity.class);
        startActivity(intent);
    }

    public void showHintOneBtnDialog(View view) {
        hintOneBtnDialog = DialogCreater.createHintOneBtnDialog(this, "提示文字", "提示文字提示文字提示文字提示文字提示文字提示文字", "按钮", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintOneBtnDialog.dismiss();
            }
        });
        hintOneBtnDialog.show();
    }

    String imgBase64 = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAWCAIAAAAJlYj7AAABoUlEQVRIiWNMSUlhIBFsPb/G2zAEwq49kdFsMYNUE/AAFgYGhtmdTiTpkXJbA9fyyJ5k7XhAavk+JmqZRQzY/Y2w0+nqIFeufRAG3GWYTkR3EDGeQAZyB289slcj22VwBk4HYaqgM2BB489MisOqLn3eIto5Yvc3J3hAoDsI02JcTqSiI1y59sG59EjUWNMlWtqAcwk4CBI8FMYXSekSPcoIugZPNqRK+sPpIKyugQjWy6DarcyQzrAIq8VkpD+WsPoWBgb0IEV2DXLqg4hIuWU92zWNbCuRDcQETKsaa/C4hgEjBRBfcuJKf/iTFHqiJpiKkY2DKPa8ewLTlWTnBnyJGj+AW7n7mwyapynJm4QLRvyuYWBgUPd8wnCQOq5hIK9grJf5gmzlze0yZLsGM65ZODe75m0mwTXaFtEMDAxXTyxlYPgLEYGE0O5vTvdyZIh3DSSvITdIIGzGlJQU4pt8yAGQp8WM261kAt8z9iwMhAoGXGDSNWgIPbJXkzt4i4GBgYEBEjZ/yXZQankNI65Gflh9C2YRxYDawocA6rbzAUYiwsFxiYN3AAAAAElFTkSuQmCC";
    public void showCodeDialog(View view){

        codeDialog = DialogCreater.createCodeDialog(this);
        codeDialog.setData("请输入验证码", imgBase64, new CodeInputSuccess() {
            @Override
            public void codeInputSuccess(String code) {
                Toast.makeText(MainActivity.this, "验证码是：" + code, Toast.LENGTH_LONG).show();
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

    public void showBanner(View view) {
        Intent intent = new Intent(this, BannerActivity.class);
        startActivity(intent);
    }

    public void openDownUpActivity(View view) {
        Intent intent = new Intent(this, DownUpActivity.class);
        startActivity(intent);
    }



    public void showRefreshListView(View view) {
        Intent intent = new Intent(this, RefreshListActivity.class);
        startActivity(intent);
    }

    public void showVersionType(View view) {
        mView.showInfo("Test");
    }

    public void hideVersionType(View view) {
        mView.hide();
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.e("buttonSwitch", isChecked + "+++++");
        }
    };

    public void showFlexbleView(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FlexibleActivity.class);
        startActivity(intent);
    }

    public void openTransitionHomeActivity(View view) {
        startActivity(new Intent(this,TransitionHomeActivity.class));
    }

    public void showWheelView(View view) {
        startActivity(new Intent(this,WheelActivity.class));
    }
}
