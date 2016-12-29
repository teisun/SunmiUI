package sunmi.sunmiui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sunmi.sunmiui.R;
import sunmi.sunmiui.utils.Adaptation;

/**
 * 两个按钮，一个icon，一个title的dialog，如果第四个参数（textRight）传null，则为单按钮的弹框，第七个参数（right）也应传null
 *
 * @author tomcat
 */
public class DialogCreater {

    public static HintDialog createHintDialog(Context context, int iconId, String textLeft, String textRight, String message, View.OnClickListener left, View.OnClickListener right,boolean isSystem) {
        HintDialog dialogProxy = HintDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setHintDialogContentView(context);
        ImageView img = (ImageView) dialog.findViewById(R.id.img);
        img.setImageResource(iconId);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.content);
        TextView btnLeft = (TextView) dialog.findViewById(R.id.left);
        TextView btnRight = (TextView) dialog.findViewById(R.id.right);
        tvMsg.setText(message);
        btnLeft.setText(textLeft);
        btnLeft.setOnClickListener(left);
        if(isSystem){
            dialogProxy.setSystemDialog(dialog);
        }else {
            dialogProxy.setDialog(dialog);
        }
        View line = dialog.findViewById(R.id.line2);

        if (TextUtils.isEmpty(textRight)) {
            btnRight.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            if (Adaptation.proportion == Adaptation.SCREEN_9_16) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_bottom);
            } else if (Adaptation.proportion == Adaptation.SCREEN_16_9) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_bottom_t1);
            }

            btnLeft.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        } else {
            btnRight.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            if (Adaptation.proportion == Adaptation.SCREEN_9_16) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_left_bottom);
            } else if (Adaptation.proportion == Adaptation.SCREEN_16_9) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_left_bottom_t1);
            }
            btnRight.setText(textRight);
            btnRight.setOnClickListener(right);
        }
        return dialogProxy;
    }

    public static TextHintDialog createTextHintDialog(Context context, String title, String textLeft, String textRight, String message, View.OnClickListener left, View.OnClickListener right,boolean isSystem) {
        TextHintDialog dialogProxy = TextHintDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setTextHintDialogContentView(context);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.dialog_msg);
        TextView btnLeft = (TextView) dialog.findViewById(R.id.left);
        TextView btnRight = (TextView) dialog.findViewById(R.id.right);
        if(TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        tvMsg.setText(message);
        btnLeft.setText(textLeft);
        btnLeft.setOnClickListener(left);
        if(isSystem){
            dialogProxy.setSystemDialog(dialog);
        }else {
            dialogProxy.setDialog(dialog);
        }
        View line = dialog.findViewById(R.id.line_view);

        if (TextUtils.isEmpty(textRight)) {
            btnRight.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            if (Adaptation.proportion == Adaptation.SCREEN_9_16) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_bottom);
            } else if (Adaptation.proportion == Adaptation.SCREEN_16_9) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_bottom_t1);
            }
            btnLeft.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        } else {
            btnRight.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            if (Adaptation.proportion == Adaptation.SCREEN_9_16) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_left_bottom);
            } else if (Adaptation.proportion == Adaptation.SCREEN_16_9) {
                btnLeft.setBackgroundResource(R.drawable.ripple_bg_left_bottom_t1);
            }
            btnRight.setText(textRight);
            btnRight.setOnClickListener(right);
        }
        return dialogProxy;
    }


    public static EditTextDialog createEditTextDialog(Context context, String textLeft, String textRight, String title, View.OnClickListener left, View.OnClickListener right) {
        EditTextDialog dialogProxy = EditTextDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setEditTextDialogContentView(context);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.title);
        TextView btnLeft = (TextView) dialog.findViewById(R.id.left);
        TextView btnRight = (TextView) dialog.findViewById(R.id.right);
        tvMsg.setText(title);
        btnLeft.setText(textLeft);
        btnRight.setText(textRight);
        btnLeft.setOnClickListener(left);
        btnRight.setOnClickListener(right);
        dialog.findViewById(R.id.clear).setVisibility(View.INVISIBLE);
        dialogProxy.setDialog(dialog);
        return dialogProxy;
    }

    public static EditPwdDialog createEditPwdDialog(Context context, String textLeft, String textRight, String title, View.OnClickListener left, View.OnClickListener right) {
        EditPwdDialog dialogProxy = EditPwdDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setEditPwdDialogContentView(context);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.title);
        TextView btnLeft = (TextView) dialog.findViewById(R.id.left);
        TextView btnRight = (TextView) dialog.findViewById(R.id.right);
        tvMsg.setText(title);
        btnLeft.setText(textLeft);
        btnRight.setText(textRight);
        btnLeft.setOnClickListener(left);
        btnRight.setOnClickListener(right);
        dialogProxy.setDialog(dialog);
        return dialogProxy;
    }

    public static ListDialog createListDialog(Context context, String title, String cancel, String[] items) {
        ListDialog dialogProxy = ListDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setListDialogContentView(context);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.title);
        tvMsg.setText(title);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        tvCancel.setText(cancel);
        dialogProxy.setDialog(dialog);
        dialogProxy.setData(items);
        return dialogProxy;
    }

    public static LoadingDialog createLoadingDialog(Context context, String msg) {
        LoadingDialog dialogProxy = LoadingDialog.getInstance();
        if (dialogProxy.hasDialog()) {
            return dialogProxy;
        }
        Dialog dialog = setLoadingDialogContentView(context);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.msg);
        tvMsg.setText(msg);
        dialogProxy.setDialog(dialog);
        return dialogProxy;
    }

    public static HintOneBtnDialog createHintOneBtnDialog(Context context, String head, String msg, String cancel, View.OnClickListener cancelClickListener) {
        HintOneBtnDialog hintOneBtnDialog = HintOneBtnDialog.getInstance();
        if (hintOneBtnDialog.hasDialog()) {
            return hintOneBtnDialog;
        }
        Dialog dialog = setHintOneBtnDialogContentView(context);
        TextView head1 = (TextView) dialog.findViewById(R.id.head);
        TextView msg1 = (TextView) dialog.findViewById(R.id.msg);
        TextView btn = (TextView) dialog.findViewById(R.id.btnTxt);
        if (TextUtils.isEmpty(head)) {
            head1.setVisibility(View.GONE);
        } else {
            head1.setVisibility(View.VISIBLE);
            head1.setText(head);
        }
        if (TextUtils.isEmpty(msg)) {
            msg1.setVisibility(View.GONE);
        } else {
            msg1.setVisibility(View.VISIBLE);
            msg1.setText(msg);
        }
        if (TextUtils.isEmpty(cancel)) {
            btn.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.VISIBLE);
            btn.setText(cancel);
        }
        btn.setOnClickListener(cancelClickListener);
        hintOneBtnDialog.setDialog(dialog);
        return hintOneBtnDialog;
    }


    private static Dialog setHintOneBtnDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                String model = Build.MODEL;
                if(model.contains("m1")||model.contains("M1")){
                    return getDialog(context, R.layout.dialog_hint_one_btn_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_hint_one_btn_9_16);
                }
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_hint_one_btn_16_9);
            default:
                return getDialog(context, R.layout.dialog_hint_one_btn_16_9);
        }
        return null;
    }


	public static CodeDialog createCodeDialog(Context context){
		final CodeDialog codeDialog = CodeDialog.getInstance();

		if(codeDialog.hasDialog()){
			return codeDialog;
		}
		Dialog dialog = setCodeDialogContentView(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
		codeDialog.setDialog(dialog);
		return codeDialog;
	}

	private static Dialog setCodeDialogContentView(Context context){
		switch (Adaptation.proportion){
			case Adaptation.SCREEN_9_16:
                String model = Build.MODEL;
                if (model.contains("M1") || model.contains("m1")) {
                    return getDialog(context, R.layout.dialog_code_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_code_9_16);
                }
			case Adaptation.SCREEN_3_4:
				return getDialog(context, R.layout.dialog_code_9_16);
			case Adaptation.SCREEN_4_3:
				return getDialog(context, R.layout.dialog_code_16_9);
			case Adaptation.SCREEN_16_9:
				return getDialog(context, R.layout.dialog_code_16_9);
			default:
				return getDialog(context, R.layout.dialog_code_9_16);
		}
	}

    private static Dialog getDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context, R.style.defaultDialogStyle);
        dialog.setContentView(layoutId);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER; //居中
        dialog.setCanceledOnTouchOutside(true); //点击空白不取消
        dialog.setCancelable(false); //点击返回按钮不取消
        return dialog;
    }


    private static Dialog setEditPwdDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                String model = Build.MODEL;
                if(model.contains("M1")||model.equals("m1")){
                    return getDialog(context, R.layout.dialog_edit_pwd_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_edit_pwd_9_16);
                }
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_edit_pwd_16_9);
        }
        return null;
    }

    private static Dialog setEditTextDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                String model = Build.MODEL;
                if(model.contains("m1")||model.contains("M1")){
                    return getDialog(context, R.layout.dialog_edit_text_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_edit_text_9_16);
                }
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_edit_text_16_9);
        }
        return null;
    }

    private static Dialog setListDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return getDialog(context, R.layout.dialog_item_select_9_16);
            case Adaptation.SCREEN_3_4:
                break;
            case Adaptation.SCREEN_4_3:
                break;
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_item_select_16_9);
        }
        return null;
    }

    private static Dialog setLoadingDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                return getDialog(context, R.layout.dialog_loading_9_16);
            case Adaptation.SCREEN_3_4:
                return getDialog(context, R.layout.dialog_loading_9_16);
            case Adaptation.SCREEN_4_3:
                return getDialog(context, R.layout.dialog_loading_16_9);
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_loading_16_9);
            default:
                return getDialog(context, R.layout.dialog_loading_9_16);
        }
    }

    private static Dialog setHintDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                String model = Build.MODEL;
                if(model.contains("M1")||model.contains("m1")){
                    return getDialog(context, R.layout.dialog_hint_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_hint_9_16);
                }
            case Adaptation.SCREEN_3_4:
                return getDialog(context, R.layout.dialog_hint_9_16);
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_hint_16_9);
            case Adaptation.SCREEN_4_3:
                return getDialog(context, R.layout.dialog_hint_16_9);
            default:
                return getDialog(context, R.layout.dialog_hint_9_16);
        }
    }

    private static Dialog setTextHintDialogContentView(Context context) {
        switch (Adaptation.proportion) {
            case Adaptation.SCREEN_9_16:
                String modle = Build.MODEL;
                if(modle.contains("m1")||modle.contains("M1")){
                    return getDialog(context, R.layout.dialog_text_hint_9_16_m1);
                }else {
                    return getDialog(context, R.layout.dialog_text_hint_9_16);
                }
            case Adaptation.SCREEN_3_4:
                return getDialog(context, R.layout.dialog_text_hint_9_16);
            case Adaptation.SCREEN_4_3:
                return getDialog(context, R.layout.dialog_text_hint_4_3);
            case Adaptation.SCREEN_16_9:
                return getDialog(context, R.layout.dialog_text_hint_4_3);
            default:
                return getDialog(context, R.layout.dialog_text_hint_9_16);
        }
    }

}
