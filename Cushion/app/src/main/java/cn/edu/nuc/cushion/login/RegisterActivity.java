package cn.edu.nuc.cushion.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import cn.edu.nuc.cushion.driver.DriverActivity;
import cn.edu.nuc.cushion.passenger.PassengerActivity;
import cn.edu.nuc.cushion.R;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class RegisterActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CardView cardView;
    private EditText usernameEt;
    private EditText passwordEt;
    private RadioButton passenger;
    private RadioButton driver;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ShowEnterAnimation();//进入注册界面的动画
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();//点击注册界面里的悬浮按钮显示动画到登录界面
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEt.getText().toString();
                final String password = passwordEt.getText().toString();

                if(username.equals("18406587382") && password.equals("18406587382") && driver.isChecked()){
                    LoginActivity.identity = true;
                    Intent intent = new Intent(RegisterActivity.this, DriverActivity.class);
                    startActivity(intent);
                }else if(username.equals("15525011526") && password.equals("15525011526") && passenger.isChecked()){
                    LoginActivity.identity = false;
                    Intent intent = new Intent(RegisterActivity.this, PassengerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cardView = findViewById(R.id.cardView);
        usernameEt = findViewById(R.id.username);
        passwordEt = findViewById(R.id.password);
        nextBtn = findViewById(R.id.goBtn);
        passenger = findViewById(R.id.passenger);
        driver = findViewById(R.id.driver);
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cardView.setVisibility(View.GONE);//隐藏
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth()/2,0, fab.getWidth() / 2, cardView.getHeight());
        mAnimator.setDuration(500);//设置持续时间
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth()/2,0, cardView.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    //点返回按钮回退到登录界面  同点击注册界面里的悬浮按钮显示动画到登录界面
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
