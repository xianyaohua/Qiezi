package com.tsrj.qiezi.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

/**
 * 动画工具
 */

public class AnimateUtil {

    public void buttonAnim(final Button button, final ProgressListener pListener, final String textStart, final String textEnd){



        final String bText = textStart+"      ";
        button.setText(bText);

        final AlphaAnimation alpha0 = new AlphaAnimation(1.0f, 1.0f);
        alpha0.setDuration(300);

        button.setClickable(false);
        alpha0.setAnimationListener(new ProgressAnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);

                int i = pListener.index%4;

                    String str ="";
                    switch (i){
                        case 0:
                            str = "      ";
                            break;
                        case 1:
                            str = " .    ";
                            break;
                        case 2:
                            str = " . .  ";
                            break;
                        case 3:
                            str = " . . .";
                            break;
                    }

                    button.setText(textStart+str);


                pListener.index++;

                if(!pListener.ifStop){
                    button.startAnimation(alpha0);
                }else{
                    button.setText(textEnd);
                    button.setClickable(true);
                    pListener.ifStop = false;
                    pListener.index = 1;
                }
            }
        });
        button.startAnimation(alpha0);



    }

     class ProgressAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    public static class ProgressListener{
        public boolean ifStop = false;

        public int index = 1;

        public boolean isIfStop() {
            return ifStop;
        }

        public void setIfStop(boolean ifStop) {
            this.ifStop = ifStop;
        }
    }

    /**
     * 设置点赞动画
     **//*
    public static void setSupportAnim(ImageView icon_support) {
        Animation animation = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.support_enter);
        icon_support.startAnimation(animation);
        icon_support.setSelected(true);
        icon_support.setEnabled(false);
    }*/
}

