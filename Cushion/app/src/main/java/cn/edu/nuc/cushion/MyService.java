package cn.edu.nuc.cushion;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.HardInfo;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.utils.DataServer;
import cn.edu.nuc.cushion.utils.TipHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyService extends Service {
    private DataServer dataServer = new DataServer();
    public static final int UPDATE = 1;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    showMessage();
                    break;
                default:
                    break;
            }
        }
    };;

    /**
     * 在服务创建的时候调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("here1");
    }

    /**
     * 在每次服务启动的时候调用
     * 希望服务一旦启动就立刻去执行某个动作
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("here2");
        timerTask(5000);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 在服务销毁的时候调用
     * 回收那些不再使用的资源
     */
    @Override
    public void onDestroy() {
        Logger.d("here3");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("here4");
        return null;
    }

    /**
     * 定时函数
     * 每隔一段时间执行一次
     *
     * @param timeInterval
     */
    public void timerTask(final long timeInterval) {
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    update();

                    getPurposeId();
                    getCurrentId();
                    checkArrival();
                    checkLeave();

                    Logger.d("定时方法  myservice1 " + currentId + " " + purposeId);

                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 实时更新硬件信息到服务器
     */
    public void update() {
        dataServer.getHardInfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                HardInfo.TemSecure temSecure = dataServer.parseHardJson(json);
                dataServer.updateCushionInfo(temSecure, 1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                    }
                });
            }
        });
    }

    private int currentId = -1; // 当前站点
    private int purposeId = -2; //目的站

    public void getCurrentId() {
        dataServer.getCurrentSite(1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Route route = new Gson().fromJson(json, Route.class);

                currentId = route.getSite_id();
            }
        });
    }

    public void getPurposeId() {
        dataServer.getCushionList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Cushion[] cushions = new Gson().fromJson(response.body().string(), Cushion[].class);
                Cushion c = cushions[0];
                purposeId = c.getSite_id();
            }
        });
    }

    public void checkArrival() {
        if (currentId == purposeId) {
            //开灯 振动
            Logger.d("到站了 myservice");
            dataServer.sendOnOff(4);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = UPDATE;
                    handler.sendMessage(message);//将message对象发送出去
                }
            }).start();
        }
    }

    public void checkLeave() {
        dataServer.getCushionList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Cushion[] cushions = new Gson().fromJson(response.body().string(), Cushion[].class);
                Cushion c = cushions[0];
                if (c.getIs_sitting().equals("否")) {
                    //关灯
                    //坐垫的目标站点设置为-1
                    Logger.d("leave myservice");
                    dataServer.sendOnOff(0);
                    c.setSite_id(-1);
                }
            }
        });

    }

    /**
     * 到站提醒,即手机震动
     */
    public void showMessage() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
        dialog.setTitle("温馨提示");
        dialog.setMessage("您已到站，请携带好随身物品下车!");
        dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TipHelper.cancel(getApplicationContext());
                dataServer.sendOnOff(0);
            }
        });
        dialog.show();
        TipHelper.vibrate(MyService.this, new long[]{800, 1000, 800, 1000, 800, 1000}, true);
    }
}
