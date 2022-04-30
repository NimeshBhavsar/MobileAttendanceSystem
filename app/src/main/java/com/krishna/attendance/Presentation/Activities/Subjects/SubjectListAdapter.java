package com.krishna.attendance.Presentation.Activities.Subjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.R;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    public List<SubjectModel> mList;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOptionClickListener;
    // private DecimalFormat mDecimalFormat;
    private Context mContext;

    public SubjectListAdapter(Context context, List<SubjectModel> list) {
        mContext = context;
        mList = list;
        //mDecimalFormat = new DecimalFormat("0");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectModel model = mList.get(position);
        if (model != null) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(model);
            holder.btnOption.setTag(model);

            holder.name.setText(model.name);
            holder.subject.setText(model.course);
            holder.studentsCount.setText(mContext.getString(R.string.students_count, String.valueOf(model.studentsCount)));
            holder.daysCount.setText(mContext.getString(R.string.days_count, String.valueOf(model.daysCount)));

            if(model.ended){
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorSmokeWhite));
                holder.btnOption.setVisibility(View.GONE);
                holder.ended.setVisibility(View.VISIBLE);
                holder.subject.append(" (Ended)");
            } else {
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.btnOption.setVisibility(View.VISIBLE);
                holder.ended.setVisibility(View.GONE);

            }

            if (mOnClickListener != null) {
                holder.itemView.setOnClickListener(mOnClickListener);
            }
            if (mOptionClickListener != null) {
                holder.btnOption.setOnClickListener(mOptionClickListener);
            }
        } else {
            // if the data is null then don't show the item but retain the item size
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_subject_list, parent, false);
        return new ViewHolder(itemView);
    }

    public void setItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setList(List<SubjectModel> list) {
        mList = list;
    }

    public void setOptionClickListener(View.OnClickListener listener) {
        mOptionClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView subject;
        public TextView studentsCount;
        public TextView daysCount;
        public ImageButton btnOption;
        public ImageView ended;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            subject = itemView.findViewById(R.id.tvSubject);
            studentsCount = itemView.findViewById(R.id.tvStudentsCount);
            daysCount = itemView.findViewById(R.id.tvDaysCount);
            btnOption = itemView.findViewById(R.id.btnOption);
            ended = itemView.findViewById(R.id.imgEnded);
        }

    }
}
