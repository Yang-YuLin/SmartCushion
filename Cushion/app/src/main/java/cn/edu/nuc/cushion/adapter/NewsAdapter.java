package cn.edu.nuc.cushion.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import cn.edu.nuc.cushion.passenger.ContentActivity;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView push_image;
        TextView title_tv;
        TextView time_tv;
        View pushView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pushView = itemView;

            time_tv = itemView.findViewById(R.id.pushTime);
            title_tv = itemView.findViewById(R.id.pushTitle);
            push_image = itemView.findViewById(R.id.pushImage);

        }
    }

    public NewsAdapter(List<News> list, Context context){
        mNewsList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.push_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.pushView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();//获取到用户点击的位置值
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra("news",mNewsList.get(position));
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News news = mNewsList.get(i);
        if(i==0){
            Glide.with(mContext).load(R.drawable.zero).into(viewHolder.push_image);
        }else if(i==1){
            Glide.with(mContext).load(R.drawable.one).into(viewHolder.push_image);
        }else if(i==2){
            Glide.with(mContext).load(R.drawable.two).into(viewHolder.push_image);
        }else if(i==3){
            Glide.with(mContext).load(R.drawable.three).into(viewHolder.push_image);
        }else if(i==4){
            Glide.with(mContext).load(R.drawable.four).into(viewHolder.push_image);
        }else{
            Glide.with(mContext).load(R.drawable.five).into(viewHolder.push_image);
        }

        viewHolder.title_tv.setText(news.getTitle());
        viewHolder.time_tv.setText(news.getDate());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
