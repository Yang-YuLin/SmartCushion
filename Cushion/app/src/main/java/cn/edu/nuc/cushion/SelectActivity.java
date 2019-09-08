package cn.edu.nuc.cushion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cn.edu.nuc.cushion.adapter.SelectAdapter;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.bean.Site;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelectActivity extends AppCompatActivity {
    private DataServer dataServer = new DataServer();
    private List<Site> mSiteList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private SelectAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectAdapter(R.layout.item_site, mSiteList);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        dataServer.getSiteList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Site[] sites = new Gson().fromJson(json, Site[].class);
                for (Site site : sites) {
                    mSiteList.add(site);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, final View view, final int position) {
                dataServer.changePurposeSiteId(1, position + 1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        final Site cushion = new Gson().fromJson(json,Site.class);
                        com.orhanobut.logger.Logger.d("解析"+cushion);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SelectActivity.this,"您已选择" + (position+1) + "道门作为目的站",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
           }
        });
    }
}
