package cn.edu.nuc.cushion.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Site;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class SelectAdapter extends BaseQuickAdapter<Site,BaseViewHolder> {

    public SelectAdapter(int layoutResId, @Nullable List<Site> data) {
        super(layoutResId, data);
    }

    public SelectAdapter(@Nullable List<Site> data) {
        super(data);
    }

    public SelectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Site item) {
        ImageView imageView = helper.getView(R.id.img);
        TextView textView = helper.getView(R.id.tweetText);
        textView.setText(item.getName());
    }
}
