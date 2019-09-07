package cn.edu.nuc.cushion.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Yangyulin on 2019/9/3.
 */
public class TipHelper {
    /**
     * @param context
     * @param milliseconds  震动的时长，单位是毫秒
     */
    public static void vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]   时长的单位是毫秒
     * @param context
     * @param pattern  自定义震动模式
     * @param isRepeat  是否反复震动
     */
    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 关闭振动
     * @param context
     */
    public static void cancel(final Context context){
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
