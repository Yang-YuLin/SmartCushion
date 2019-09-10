package cn.edu.nuc.cushion.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.Record;
import cn.edu.nuc.cushion.bean.Site;
import cn.edu.nuc.cushion.utils.DataServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by Yangyulin on 2019/9/10.
 */
public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {
    private DataServer dataServer = new DataServer();

    public RecordAdapter(int layoutResId, @Nullable List<Record> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Record item) {
        final TextView currentSite = helper.getView(R.id.currentSite);

        dataServer.getSiteById(item.getSite_id(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Site site = new Gson().fromJson(json,Site.class);
                currentSite.setText(site.getName());
            }
        });
        TextView time = helper.getView(R.id.time);
        time.setText(item.getTime());
    }
}
