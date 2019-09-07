package cn.edu.nuc.cushion.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import java.util.List;
import cn.edu.nuc.cushion.R;
import cn.edu.nuc.cushion.bean.Cushion;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class CushionAdapter extends RecyclerView.Adapter<CushionAdapter.MyViewHolder> {
    private List<Cushion> mCushionList = null;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View cushionView;
        private ImageView cushion;
        private TextView id;
        private TextView cushion_id;
        private TextView site;
        private TextView cushion_site;
        private ImageView temperature;
        private TextView cushion_temperature;
        private ImageView sit;
        private TextView cushion_sit;

        public MyViewHolder(View itemView) {
            super(itemView);
            cushionView = itemView;

            cushion = itemView.findViewById(R.id.cushion);
            id = itemView.findViewById(R.id.id);
            cushion_id = itemView.findViewById(R.id.cushion_id);
            site = itemView.findViewById(R.id.site);
            cushion_site = itemView.findViewById(R.id.cushion_site);
            temperature = itemView.findViewById(R.id.temperature);
            cushion_temperature = itemView.findViewById(R.id.cushion_temperature);
            sit = itemView.findViewById(R.id.sit);
            cushion_sit = itemView.findViewById(R.id.cushion_sit);
        }
    }

    public CushionAdapter(List<Cushion> cushionList) {
        mCushionList = cushionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cushion,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        myViewHolder.cushionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();//获取到用户点击的位置值
                Cushion cushion = mCushionList.get(position);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cushion cushion = mCushionList.get(position);
        Logger.d(cushion);
        if (cushion != null) {
            try {
                int id = cushion.getId();
                String sitename = cushion.getSitename();// null
                String tem = String.valueOf(cushion.getTemperature());
                String sit = cushion.getIs_sitting();

                holder.cushion_id.setText(cushion.getId());
                holder.cushion_site.setText("testtesttest");
                holder.cushion_temperature.setText(String.valueOf(cushion.getTemperature()));
                holder.cushion_sit.setText(cushion.getIs_sitting());

                Logger.e(String.valueOf(sitename == null));

            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("cushion adapter" + e.getMessage());
            }
        } else {
            Logger.e("11111 cushion is null");
        }


    }

    @Override
    public int getItemCount() {
        return mCushionList.size();
    }
}
