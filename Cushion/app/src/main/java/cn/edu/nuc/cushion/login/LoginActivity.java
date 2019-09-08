package cn.edu.nuc.cushion.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.edu.nuc.cushion.DriverActivity;
import cn.edu.nuc.cushion.PassengerActivity;
import cn.edu.nuc.cushion.R;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText usernameEt;
    private EditText passwordEt;
    private Button goBtn;
    private CardView cardView;
    private FloatingActionButton fab;
    public static boolean identity;

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
                if(username.equals("18406587382") && password.equals("18406587382")){
                    identity = true;
                    Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
                    startActivity(intent);
                }else if(username.equals("15525011526") && password.equals("15525011526")){
                    identity = false;
                    Intent intent = new Intent(LoginActivity.this, PassengerActivity.class);
                    startActivity(intent);
                }
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

    @Override
    //在活动准备好和用户进行交互的时候调用，此时的活动一定位于返回栈的栈顶，并且处于运行状态
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);//可见
    }
}
