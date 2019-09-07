package cn.edu.nuc.cushion.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.io.IOException;

import cn.edu.nuc.cushion.CushionActivity;
import cn.edu.nuc.cushion.MainActivity;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Admin;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText usernameEt;
    private EditText passwordEt;
    private Button goBtn;
    private CardView cardView;
    private FloatingActionButton fab;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Logger.addLogAdapter(new AndroidLogAdapter());
        initView();
        setListener();
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cardView = findViewById(R.id.cardView);
        usernameEt = findViewById(R.id.username);
        passwordEt = findViewById(R.id.password);
        goBtn = findViewById(R.id.goBtn);
    }

    private void setListener() {
        goBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);


                final String username = usernameEt.getText().toString();
                final String password = passwordEt.getText().toString();

                new DataServer().getUser(username, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    //response就是服务器返回的数据
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String jsonData = response.body().string();//得到返回的具体内容
                            Admin admin = new Gson().fromJson(jsonData, Admin.class);//使用GSON解析JSON数据
                            if (password.equals(admin.getPassword())) {
                                //在本地存用户手机号
                                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();//表示只有当前的应用程序才可以对这个SharedPreferences文件进行读写
                                editor.putString("username",username);
                                editor.putString("password",password);
                                editor.apply();//提交数据,完成数据存储的任务
                                if (username == "root") {
                                    Intent intent = new Intent(LoginActivity.this, CushionActivity.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "账号或密码错误,请重新填写", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //此activity退出
                getWindow().setExitTransition(null);
                //此activity进入
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

//    @Override
    //在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了
//    protected void onRestart() {
//        super.onRestart();
//        fab.setVisibility(View.GONE);//隐藏
//    }

    @Override
    //在活动准备好和用户进行交互的时候调用，此时的活动一定位于返回栈的栈顶，并且处于运行状态
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);//可见
    }
}
