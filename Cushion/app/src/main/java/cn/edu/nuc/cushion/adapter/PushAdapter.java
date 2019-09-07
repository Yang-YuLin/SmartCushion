package cn.edu.nuc.cushion.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.nuc.cushion.PushText;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Push;


public class PushAdapter extends RecyclerView.Adapter<PushAdapter.ViewHolder> {

    private List<Push> mPushlist;
    private String url;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ///////
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

    public PushAdapter(List<Push> list){
        mPushlist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.push_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.pushView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup.getContext(),PushText.class);
                viewGroup.getContext().startActivity(intent);
                Toast.makeText(view.getContext(),"you clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Push push = mPushlist.get(i);

        viewHolder.push_image.setImageResource(push.getImageId());
        viewHolder.title_tv.setText(push.getTitle());
        viewHolder.time_tv.setText(push.getTime());
    }

    @Override
    public int getItemCount() {
        return mPushlist.size();
    }



}
