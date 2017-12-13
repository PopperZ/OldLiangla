package com.brightcns.liangla.weight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.brightcns.liangla.R;


/**
 * Created by 巩贺 on 2017/3/23.
 */
public class CustomProgressDialog extends Dialog {


        private Context context = null;

        private static CustomProgressDialog customProgressDialog = null;



        public CustomProgressDialog(Context context){

            super(context);

            this.context = context;

        }

        public CustomProgressDialog(Context context, int theme) {

            super(context, theme);

        }

        public static CustomProgressDialog createDialog(Context context){

            customProgressDialog = new CustomProgressDialog(context, R.style.Custom_Progress);

            customProgressDialog.setContentView(R.layout.progress_custom );

            customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;



            return customProgressDialog;

        }

        public void onWindowFocusChanged(boolean hasFocus){



            if (customProgressDialog == null){

                return;

            }

            ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.spinnerImageView);

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();

            animationDrawable.start();

        }


        public CustomProgressDialog setTitile(String strTitle){

            return customProgressDialog;

        }
        /**
         *       setMessage 提示内容
         */

        public CustomProgressDialog setMessage(String strMessage){

            TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.message);

            if (tvMsg != null){

                tvMsg.setText(strMessage);

            }
            return customProgressDialog;
        }

    }


