package cn.edu.nuc.cushion.utils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import cn.edu.nuc.cushion.bean.HardInfo;
import cn.edu.nuc.cushion.bean.Site;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class DataServer {
    private OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 注册
     * @param username
     * @param password
     * @param callback
     */
    public void register(String username, String password, Callback callback) {
        String url = "";
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();//构建body表单   RequestBody对象存放待提交的参数
        Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();//想要发起http请求,就需要创建Request对象  url:设置目标的网络地址
        okHttpClient.newCall(request).enqueue(callback);//异步 发送请求并获取服务器返回的数据   execute:同步
    }

    /**
     * 登录,得到特定的某个用户
     * @param callback 回调
     */
    public void getUser(String username, Callback callback) {
        String url = "";
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 读取硬件信息
     * @param callback
     */
    public void getHardInfo(Callback callback) {
        String url = "http://192.168.0.1/cgi-bin/node.cgi";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 解析硬件json数据
     * @param json
     * @return
     */
    public HardInfo.TemSecure parseHardJson(String json) {
        HardInfo[] hardInfoArray = new Gson().fromJson(json, HardInfo[].class);
        double temperature = 0;
        String isSit = "否";
        for (HardInfo hardInfo : hardInfoArray) {
            //温度mac
            if (hardInfo.getMacAddr().equals("AB635305004B1200")) {
                temperature = (double) hardInfo.getFuncList().get(0).getData();
            }
            //安防mac
            if (hardInfo.getMacAddr().equals("92625305004B1200")) {
                if ((double) hardInfo.getFuncList().get(0).getData() == 1) {
                    isSit = "是";
                } else {
                    isSit = "否";
                }
            }
        }

        return new HardInfo.TemSecure(temperature, isSit);
    }

    /**
     * @param param    灯亮灭的参数
     */
    public void sendOnOff(int param) {
        String url = "http://192.168.0.1/cgi-bin/send_node.cgi? =type=11%26id=3%26data=" + String.valueOf(param);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();//想要发起http请求,就需要创建Request对象  url:设置目标的网络地址
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 查询当前站点
     * @param driverId driverid司机id,默认是1
     * @param callback
     */
    public void getCurrentSite(int driverId, Callback callback) {
        String url = "http://192.168.0.102:8080/front/getRoute/?id=" + String.valueOf(driverId);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 司机到达一个站点，设置下一站为当前站点
     * @param driverId 司机id  默认填1  因为目前只有一个司机
     * @param curSite  当前站点的id
     * @param callback
     */
    public void setCurrentSite(int driverId, int curSite, Callback callback) {
        String url = "http://192.168.0.102:8080/front/editRoute/";
        RequestBody requestBody = new FormBody.Builder()
                .add("id", String.valueOf(driverId))
                .add("site_id", String.valueOf(curSite))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 乘客设置自己要去的站点，即修改目的站
     * @param id       userid
     * @param site
     * @param callback
     */
    public void changePurposeSiteId(final int id, final int site, final Callback callback) {
        getSite(site, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Site s = new Gson().fromJson(response.body().string(), Site.class);

                String url = "http://192.168.0.102:8080/front/editCushionSite/";

                RequestBody requestBody = new FormBody.Builder()
                        .add("id", String.valueOf(id))
                        .add("sitename", s.getName())
                        .add("site_id", String.valueOf(site))
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(callback);
            }
        });
    }

    /**
     * 获取站点列表
     * 用Site[] 解析
     * @param callback
     */
    public void getSiteList(Callback callback) {
        String url = "http://192.168.0.102:8080/front/getSiteList/";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 获取具体站点
     * @param siteId
     * @param callback
     */
    public void getSite(int siteId, Callback callback) {
        String url = "http://192.168.0.102:8080/front/getSiteById/?id=" + String.valueOf(siteId);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 获取坐垫列表
     * @param callback
     */
    public void getCushionList(Callback callback) {
        String url = "http://192.168.0.102:8080/front/getCushionList";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 实时更新坐垫表信息
     * @param temSecure
     * @param id
     * @param callback
     */
    public void updateCushionInfo(HardInfo.TemSecure temSecure, int id, Callback callback) {
        String url = "http://192.168.0.102:8080/front/editCushion/";
        double temperature = temSecure.getTemperature();
        String isSit = temSecure.isSit();

        RequestBody requestBody = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("is_sitting", isSit)
                .add("temperature", String.valueOf(temperature))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}