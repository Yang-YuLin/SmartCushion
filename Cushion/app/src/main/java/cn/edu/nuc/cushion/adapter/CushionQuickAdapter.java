package cn.edu.nuc.cushion.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import java.util.HashMap;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Cushion;
import cn.edu.nuc.cushion.bean.Site;

/**
 * Created by Yangyulin on 2019/9/6.
 */
public class CushionQuickAdapter extends BaseQuickAdapter<Cushion, BaseViewHolder> {
    private HashMap<Integer, Site> mSiteHashMap;

    public CushionQuickAdapter(int layoutResId, @Nullable List<Cushion> data, HashMap<Integer, Site> idSiteMap) {
        super(layoutResId, data);
        mSiteHashMap = idSiteMap;
    }

    @Override
    protected void convert(BaseViewHolder helper, Cushion item) {
        Logger.d("cushion q adapter" + item);

        TextView idTv =  helper.getView(R.id.cushion_id);
        idTv.setText(String.valueOf(item.getId()));

        TextView siteTv = helper.getView(R.id.cushion_site);
        siteTv.setText(item.getSitename());

        TextView temTv = helper.getView(R.id.cushion_temperature);
        temTv.setText(String.valueOf(item.getTemperature()));

        TextView sitTv = helper.getView(R.id.cushion_sit);
        sitTv.setText(item.getIs_sitting());
    }
}
