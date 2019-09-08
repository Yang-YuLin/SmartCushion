package cn.edu.nuc.cushion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.edu.nuc.cushion.login.LoginActivity;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class MineFragment extends Fragment {

    private TextView telNumTv;
    private TextView nickNameTv;
    private CardView youhui;
    private CardView setting;
    private CardView account;
    private SharedPreferences sharedPreferences;
    private String telNum;
    private String nickName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);

        sharedPreferences = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);

        telNumTv = view.findViewById(R.id.telNum);
        nickNameTv = view.findViewById(R.id.nickName);
        if(LoginActivity.identity == true){
            telNumTv.setText("    账号: "+"18406587382");
            nickNameTv.setText("    昵称: "+"这是我的昵称哦");
        }else{
            telNumTv.setText("    账号: "+"15525011526");
            nickNameTv.setText("    昵称: "+"这是我的昵称哦");
        }

        youhui = view.findViewById(R.id.youhui);
        setting = view.findViewById(R.id.setting);
        account = view.findViewById(R.id.account);

        youhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),YouhuiActivity.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("温馨提示");
                dialog.setMessage("App由中北大学16070941班杨玉林同学完成,未经允许不得进行任何商业活动\n\n如果有任何建议,请发送信息至邮箱cn.yangyulin@gmail.com");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
