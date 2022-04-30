package com.krishna.attendance.Presentation.Activities.Attendances;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.R;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.ViewHolder> {

    public List<AttendanceModel> mList;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOptionClickListener;
    private Context mContext;

    public AttendanceListAdapter(Context context, List<AttendanceModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceListAdapter.ViewHolder holder, int position) {
        AttendanceModel model = mList.get(position);
        if (model != null) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(model);
            //holder.btnOption.setTag(model);

            holder.name.setText(model.studentName);
            holder.crn.setText(model.studentCrn);
            // edit from here on
            // TODO:: need to edit the below lines when multiple attendance types are implemented
            if(model.status == AttendanceStatusEnum.Present){
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGreen));
                holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_green_24dp));//.setVisibility(View.VISIBLE);
            } else {
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_close_pink_24dp));//.setVisibility(View.INVISIBLE);
            }
            if (mOnClickListener != null) {
                holder.itemView.setOnClickListener(mOnClickListener);
            }
           /* if (mOptionClickListener != null) {
                holder.btnOption.setOnClickListener(mOptionClickListener);
            }*/
        } else {
            // if the data is null then don't show the item but retain the item size
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public AttendanceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_attendance_list, parent, false);
        return new AttendanceListAdapter.ViewHolder(itemView);
    }

    public void setItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setList(List<AttendanceModel> list) {
        mList = list;
    }

    public void setOptionClickListener(View.OnClickListener listener) {
        mOptionClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgStatus;
        public ImageView image;
        public TextView crn;
        public TextView name;
       // public ImageButton btnOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crn = itemView.findViewById(R.id.tvCrn);
            name = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.imgImage);
            imgStatus = itemView.findViewById(R.id.imgStatus);
           // btnOption = itemView.findViewById(R.id.btnOption);
        }

    }

}
