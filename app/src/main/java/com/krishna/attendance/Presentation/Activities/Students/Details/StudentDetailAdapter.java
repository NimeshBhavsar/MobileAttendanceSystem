package com.krishna.attendance.Presentation.Activities.Students.Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Utilities.DateUtilityService;
import com.krishna.attendance.Presentation.Activities.Days.DayListAdapter;
import com.krishna.attendance.R;

import java.util.List;

public class StudentDetailAdapter extends RecyclerView.Adapter<StudentDetailAdapter.ViewHolder> {

    private List<AttendanceModel> mList;
    private Context mContext;

    public StudentDetailAdapter(Context context, List<AttendanceModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<AttendanceModel> getList() {
        return mList;
    }

    public void setList(List<AttendanceModel> list) {
        mList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailAdapter.ViewHolder holder, int position) {
        AttendanceModel model = mList.get(position);
        if(model!=null){
            holder.itemView.setVisibility(View.VISIBLE);

            holder.itemView.setTag(model);
            holder.date.setText(DateUtilityService.format_EEEdMMMyyyy_HHmm(model.date));
            holder.statusText.setText(model.status.toString());

            if(model.status == AttendanceStatusEnum.Present){
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGreen));
                holder.statusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_green_24dp));
            } else {
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.statusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_close_pink_24dp));
            }
        } else {
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public StudentDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_student_detail, parent, false);
        return new StudentDetailAdapter.ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView statusText;
        ImageView statusImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvDate);
            statusText = itemView.findViewById(R.id.tvStatus);
            statusImage = itemView.findViewById(R.id.imgStatus);
        }
    }
}
