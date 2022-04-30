package com.krishna.attendance.Presentation.Activities.Attendances.Create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.R;

import java.util.List;

public class AttendanceCreateAdapter extends RecyclerView.Adapter<AttendanceCreateAdapter.ViewHolder>{

    private List<AttendanceModel> mList;
//    private View.OnClickListener mOnClickListener;
//    private View.OnClickListener mOptionClickListener;

    public AttendanceCreateAdapter(List<AttendanceModel> list) {
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceCreateAdapter.ViewHolder holder, int position) {
        final AttendanceModel model = mList.get(position);
        if (model != null) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(model);
          //  holder.btnOption.setTag(model);

            holder.name.setText(model.studentName);
            holder.crn.setText(model.studentCrn);
            // edit from here on
            holder.chkStaus.setTag(model);
            holder.chkStaus.setChecked(model.status == AttendanceStatusEnum.Present);


            /*if (mOnClickListener != null) {
                holder.itemView.setOnClickListener(mOnClickListener);
            }
            if (mOptionClickListener != null) {
                holder.btnOption.setOnClickListener(mOptionClickListener);
            }*/

            // check/uncheck
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO:: Implement multiple Attendance-Status type in upcoming versions
                    updateUiAfterClick(holder, model);
                }
            });

            holder.chkStaus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUiAfterClick(holder, model);
                }
            });

        } else {
            // if the data is null then don't show the item but retain the item size
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    private void updateUiAfterClick(AttendanceCreateAdapter.ViewHolder holder, AttendanceModel model){
        if(model.status == AttendanceStatusEnum.Absent){
            // was absent, so now make present
            model.status = AttendanceStatusEnum.Present;
        } else {
            model.status = AttendanceStatusEnum.Absent;
        }
        holder.chkStaus.setChecked(model.status == AttendanceStatusEnum.Present);
    }

    @NonNull
    @Override
    public AttendanceCreateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_attendance_create, parent, false);
        return new AttendanceCreateAdapter.ViewHolder(itemView);
    }

   /* public void setItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }*/

    public void setList(List<AttendanceModel> list) {
        mList = list;
    }

    public List<AttendanceModel> getList(){
        return mList;
    }

    /*public void setOptionClickListener(View.OnClickListener listener) {
        mOptionClickListener = listener;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgStatus;
        public ImageView image;
        public CheckBox chkStaus;
        public TextView crn;
        public TextView name;
        public ImageButton btnOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crn = itemView.findViewById(R.id.tvCrn);
            name = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.imgImage);
          //  imgStatus = itemView.findViewById(R.id.imgStatus);
            chkStaus = itemView.findViewById(R.id.chkStatus);
           // btnOption = itemView.findViewById(R.id.btnOption);
        }

    }

}
