package cn.edu.nuc.cushion;

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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.nuc.cushion.adapter.SiteQuickAdapter;
import cn.edu.nuc.cushion.bean.Route;
import cn.edu.nuc.cushion.bean.Site;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/7.
 */
public class SiteFragment extends Fragment {
    private List<Site> mSiteList = new ArrayList<>();//要展示的数据源
    private RecyclerView mRecyclerView = null;
    private FloatingActionButton change_site;
    private DataServer dataServer = new DataServer();
    private SiteQuickAdapter adapter = null;
    private int flag = 1;
    private int curSite;
    private int startSie;
    private int endSite;
    private HashMap<Integer, Site> idSiteMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        change_site = view.findViewById(R.id.change_site);
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


                        changeIcon(curSite - 1, false);

                        startSie = route.getStart();
                        endSite = route.getEnd();
                        if (curSite == endSite) {
                            flag = -1;
                        }
                        if (curSite == startSie) {
                            flag = 1;
                        }
                        curSite += flag;


                        changeIcon(curSite - 1, true);


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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new SiteQuickAdapter(R.layout.item_site_driver, mSiteList);

        mRecyclerView.setAdapter(adapter);

        initSite();

        return view;
    }

    public void initSite() {

        dataServer.getSiteList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Site[] sites = new Gson().fromJson(json, Site[].class);

                for (int i = 0; i < sites.length; i++) {
                    Site site = sites[i];
                    mSiteList.add(site);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        dataServer.getCurrentSite(1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Route route = new Gson().fromJson(json, Route.class);

                curSite = route.getSite_id();
                changeIcon(curSite - 1, true);
            }
        });

    }

    private void changeIcon(final int position, final boolean isRed) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView imageViewOld = (ImageView) adapter.getViewByPosition(mRecyclerView, position, R.id.circle);
                imageViewOld.setImageResource(isRed ? R.drawable.circle_yellow : R.drawable.circle);
            }
        });
    }
}
