package cn.edu.nuc.cushion.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Site;

/**
 * Created by Yangyulin on 2019/9/7.
 */
public class SiteQuickAdapter extends BaseQuickAdapter<Site, BaseViewHolder> {

    private Context mContext;

    public SiteQuickAdapter(int layoutResId, @Nullable List<Site> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Site item) {
        TextView siteTv = helper.getView(R.id.siteName);
        siteTv.setText(item.getName());
    }
}
