package cn.edu.nuc.cushion.passenger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.Arrays;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.adapter.RecordQuickAdapter;
import cn.edu.nuc.cushion.bean.Record;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Yangyulin on 2019/9/10.
 */
public class HistoryFragment extends Fragment {
    private List<Record> mRecordList = new ArrayList<>();//要展示的数据源
    private RecyclerView mRecyclerView = null;
    private DataServer dataServer = new DataServer();
    private RecordQuickAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecordQuickAdapter(R.layout.item_record, mRecordList);
        mRecyclerView.setAdapter(adapter);

        initRecord();
        return view;
    }

    public void initRecord(){
        dataServer.getRecordList(1,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Record[] records = new Gson().fromJson(json,Record[].class);
                mRecordList.addAll(Arrays.asList(records));
                Logger.d("record debug " + mRecordList.size() + mRecordList.get(0));
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
