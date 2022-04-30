package com.krishna.attendance.Presentation.Activities.Students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.R;

import java.text.DecimalFormat;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private Context mContext;
    private SubjectModel mSubjectModel;
    private List<StudentModel> mList;

    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOptionClickListener;

    private DecimalFormat mDecimalFormat;

    public StudentListAdapter(Context context, SubjectModel subjectModel, List<StudentModel> list) {
        mContext = context;
        mSubjectModel = subjectModel;
        mList = list;
        mDecimalFormat = new DecimalFormat("##0.0");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentModel model = mList.get(position);
        if (model != null) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(model);
            holder.btnOption.setTag(model);

            holder.name.setText(model.name);
            holder.crn.setText(model.crn);

            holder.presencePercent.setText(mContext.getResources().getString(R.string.presence_percent, mDecimalFormat.format(model.presencePercent)));

            if(mSubjectModel!=null && mSubjectModel.ended){
                holder.btnOption.setVisibility(View.GONE);
            } else {
                holder.btnOption.setVisibility(View.VISIBLE);
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
                .inflate(R.layout.row_student_list, parent, false);
        return new ViewHolder(itemView);
    }

    public void setItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setList(List<StudentModel> list) {
        mList = list;
    }

    public void setOptionClickListener(View.OnClickListener listener) {
        mOptionClickListener = listener;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        mSubjectModel = subjectModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView crn;
        public ImageButton btnOption;
        public ImageView image;
        public TextView presencePercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            crn = itemView.findViewById(R.id.tvCrn);
            btnOption = itemView.findViewById(R.id.btnOption);
            image = itemView.findViewById(R.id.imgImage);
            presencePercent = itemView.findViewById(R.id.tvPresencePercent);
        }

    }
}
