package cn.edu.nuc.cushion.passenger;

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

import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.adapter.NewsAdapter;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.News;
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
    private List<News> newslist = new ArrayList<>();
    private TextView currentSite = null;
    private TextView purposeSite = null;
    private DataServer dataServer = new DataServer();
    private NewsAdapter adapter;

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
        adapter = new NewsAdapter(newslist,getContext());
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
        dataServer.requestNews(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                News[] news = new Gson().fromJson(json,News[].class);
                for(News new1:news){
                    newslist.add(new1);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
