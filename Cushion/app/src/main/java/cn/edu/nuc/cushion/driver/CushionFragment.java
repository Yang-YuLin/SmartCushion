package cn.edu.nuc.cushion.driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.adapter.CushionQuickAdapter;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.HardInfo;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.bean.Site;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/7.
 */
public class CushionFragment extends Fragment {
    private List<Cushion> mCushionList = new ArrayList<>();//要展示的数据源
    private RecyclerView mRecyclerView = null;
    private DataServer dataServer = new DataServer();
    private CushionQuickAdapter adapter = null;
    private int flag = 1;
    private int curSite;
    private int startSie;
    private int endSite;
    private HashMap<Integer, Site> idSiteMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cushion,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        timerTask(1000);
        return view;
    }

    public void initCushion(){
        dataServer.getCushionList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                Cushion[] cushions = new Gson().fromJson(json,Cushion[].class);

                List<Cushion> mCushionList1 = new ArrayList<>();
                for (int i = 0; i < cushions.length; i++) {
                    Cushion cushion = cushions[i];
                    mCushionList1.add(cushion);
                }
                mCushionList = mCushionList1;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CushionQuickAdapter(R.layout.item_cushion, mCushionList, idSiteMap);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    public void timerTask(final long timeInterval) {
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    initCushion();
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
}
