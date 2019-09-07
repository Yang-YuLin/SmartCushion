package cn.edu.nuc.cushion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.edu.nuc.cushion.adapter.CushionQuickAdapter;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.bean.Site;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/2
 */
public class CushionActivity extends AppCompatActivity {
    private List<Cushion> mCushionList = new ArrayList<>();//要展示的数据源
    private RecyclerView mRecyclerView = null;
    private FloatingActionButton change_site;
    private DataServer dataServer = new DataServer();
    private CushionQuickAdapter adapter = null;
    private int flag = 1;
    private int curSite;
    private int startSie;
    private int endSite;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private HashMap<Integer, Site> idSiteMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cushion);

        mRecyclerView = findViewById(R.id.recycler_view);
        change_site = findViewById(R.id.change_site);
        change_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataServer.getCurrentSite(1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Route route = new Gson().fromJson(json, Route.class);
                        Logger.d("here" + route);

                        curSite = route.getSite_id();
                        startSie = route.getStart();
                        endSite = route.getEnd();
                        if (curSite == endSite) {
                            flag = -1;
                        }
                        if (curSite == startSie) {
                            flag = 1;
                        }
                        curSite  += flag;

                        Logger.d("222" + curSite);

                        dataServer.setCurrentSite(1, curSite, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Logger.d(response.body().string());
                            }
                        });
                    }
                });

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new CushionQuickAdapter(R.layout.item_cushion, mCushionList, idSiteMap);
        mRecyclerView.setAdapter(adapter);

        initCushion();

    }

    private void initCushion(){
        dataServer.getCushionList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Cushion[] cushions = new Gson().fromJson(json,Cushion[].class);

                for (int i = 0; i < cushions.length; i++) {
                    final Cushion cushion = cushions[i];
                    mCushionList.add(cushion);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}
