package cn.edu.nuc.cushion.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.HardInfo;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.login.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyService extends Service {
    private DataServer dataServer = new DataServer();
    private int flag = 1;

    /**
     * 在服务创建的时候调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
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
        timerTask(3000);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 在服务销毁的时候调用
     * 回收那些不再使用的资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 定时函数
     * 每隔一段时间执行一次
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
                    Logger.d("定时方法  myservice  当前站:" + currentId + "  目的站:" + purposeId);
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

    public int currentId = -1; // 当前站点
    public int purposeId = -2; //目的站

    public void getCurrentId() {
        dataServer.getCurrentSite(1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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
                e.printStackTrace();
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
            if(!LoginActivity.identity){
                TipHelper.vibrate(MyService.this, 500);//到站提醒,即手机震动

            }
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
                if (c.getIs_sitting().equals("否") || currentId != purposeId) {
                    //关灯
                    //坐垫的目标站点设置为-1
                    Logger.d("leave myservice");
                    dataServer.sendOnOff(0);

                    TipHelper.cancel(MyService.this);

                }
            }
        });
    }
}
