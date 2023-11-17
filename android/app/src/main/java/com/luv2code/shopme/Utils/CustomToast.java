package com.luv2code.shopme.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luv2code.shopme.R;

public class CustomToast extends Toast{
    public static int SUCCESS = 1;
    public static int WARNING = 2;
    public static int ERROR = 3;
    public static int CONFUSING = 4;

    private static long SHORT = 4000;
    private static long LONG = 7000;
    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, String message, int duration, int type) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_type);
        ImageView img = (ImageView) layout.findViewById(R.id.toast_icon);
        l1.setText(message);

        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.success_shape);
            img.setImageResource(R.drawable.ic_baseline_check_24);
        } else if (type == 2) {
            linearLayout.setBackgroundResource(R.drawable.warning_shape);
            img.setImageResource(R.drawable.ic_baseline_back_hand_24);
        } else if (type == 3) {
            linearLayout.setBackgroundResource(R.drawable.error_shape);
            img.setImageResource(R.drawable.ic_baseline_close_24);
        } else if (type == 4) {
            linearLayout.setBackgroundResource(R.drawable.confusing_shape);
            img.setImageResource(R.drawable.ic_baseline_rotate_right_24);
        }
        toast.setView(layout);
        return toast;
    }

    public static Toast toastCenterTransparent(Context context, String message, int duration, int type) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);

        View layout = LayoutInflater.from(context).inflate(R.layout.toast_cart_layout, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        ImageView icon = (ImageView) layout.findViewById(R.id.toast_icon);
        l1.setText(message);

        if (type == 1) {
            icon.setImageResource(R.drawable.ic_baseline_check_24);
        }
        else {
            icon.setImageResource(R.drawable.ic_error_outline_24);
        }

        toast.setView(layout);
        return toast;
    }

}
