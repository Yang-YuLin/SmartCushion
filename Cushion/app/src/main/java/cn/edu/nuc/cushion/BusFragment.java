package cn.edu.nuc.cushion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nuc.cushion.adapter.PushAdapter;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.Push;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class BusFragment extends Fragment{
    private ImageView bus = null;
    private List<Push> pushlist = new ArrayList<>();
    private TextView currentSite = null;
    private TextView purposeSite = null;
    private DataServer dataServer = new DataServer();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus,container,false);
        bus = view.findViewById(R.id.bus);
        currentSite = view.findViewById(R.id.currentSite);
        purposeSite = view.findViewById(R.id.purposeSite);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getContext(),DecoderActivity.class);
            startActivity(intent);
            }
        });
        initPushs();
        RecyclerView recyclerView = view.findViewById(R.id.push_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        PushAdapter adapter = new PushAdapter(pushlist);
        recyclerView.setAdapter(adapter);
        dataServer.getCurrentSite(1, new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Route route = new Gson().fromJson(json, Route.class);

                currentSite.setText("当前站:" + route.getSite_id() + "道门");
            }
        });

        dataServer.getCushionList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Cushion[] cushions = new Gson().fromJson(json,Cushion[].class);
                if(cushions[0].getSite_id()!=-1){
                    purposeSite.setText("目的站:" + cushions[0].getSite_id() + "道门");
                }
            }
        });
        return view;
    }

    private void initPushs(){
        Push push1 = new Push("8月31日太原公交路线大整改"," 2019年9月1日", R.drawable.route);
        pushlist.add(push1);
        Push push2 = new Push("为什么京东搜索的东西出现在你的抖音广告里"," 2019年8月30日", R.drawable.image);
        pushlist.add(push2);
        Push push3 = new Push("午夜公交里的神秘人"," 2019年8月28日", R.drawable.person);
        pushlist.add(push3);
        Push push4 = new Push("太原公交沿线美食推荐"," 2019年8月25日", R.drawable.food);
        pushlist.add(push4);
    }
}
