package cn.edu.nuc.cushion.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Site;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class SelectQuickAdapter extends BaseQuickAdapter<Site,BaseViewHolder> {

    public SelectQuickAdapter(int layoutResId, @Nullable List<Site> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Site item) {
        ImageView imageView = helper.getView(R.id.img);
        TextView textView = helper.getView(R.id.tweetText);
        textView.setText(item.getName());

        if(item.getName().equals("一道门")){
            Glide.with(mContext).load(R.drawable.onedoor).into(imageView);
        }else if(item.getName().equals("二道门")){
            Glide.with(mContext).load(R.drawable.twodoor).into(imageView);
        }else if(item.getName().equals("三道门")){
            Glide.with(mContext).load(R.drawable.threedoor).into(imageView);
        }else if(item.getName().equals("四道门")){
            Glide.with(mContext).load(R.drawable.fourdoor).into(imageView);
        }else{
            Glide.with(mContext).load(R.drawable.fivedoor).into(imageView);
        }
    }
}
