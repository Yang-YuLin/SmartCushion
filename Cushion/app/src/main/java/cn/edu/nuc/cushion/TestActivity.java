package cn.edu.nuc.cushion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import cn.edu.nuc.cushion.bean.HardInfo;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class TestActivity extends AppCompatActivity {
    private Button btGet;
    private Button btSendOn;
    private Button btSendOff;
    DataServer dataServer = new DataServer();
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btGet = findViewById(R.id.bt_get);
        btSendOn = findViewById(R.id.bt_send_on);
        btSendOff = findViewById(R.id.bt_send_off);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataServer.getHardInfo(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Logger.e(e.getMessage());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        HardInfo.TemSecure temSecure = dataServer.parseHardJson(json);
                        Logger.i(temSecure.toString());
                    }
                });
            }
        });
    }
}
