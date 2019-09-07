package cn.edu.nuc.cushion.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
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
 * Created by Yangyulin on 2019/9/7.
 */
public class SiteQuickAdapter extends BaseQuickAdapter<Site, BaseViewHolder> {
    private HashMap<Integer, Site> mSiteHashMap;

    public SiteQuickAdapter(int layoutResId, @Nullable List<Site> data, HashMap<Integer, Site> idSiteMap) {
        super(layoutResId, data);
        mSiteHashMap = idSiteMap;
    }

    @Override
    protected void convert(BaseViewHolder helper, Site item) {
        TextView siteTv = helper.getView(R.id.siteName);
        siteTv.setText(item.getName());
    }
}
