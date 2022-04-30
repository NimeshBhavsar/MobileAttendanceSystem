package com.krishna.attendance.Presentation.Activities.Days;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Core.Utilities.DateUtilityService;
import com.krishna.attendance.R;

import java.text.DecimalFormat;
import java.util.List;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {

    DecimalFormat mDecimalFormat;
    private SubjectModel mSubjectModel;
    private List<DayModel> mList;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOptionClickListener;
    private Context mContext;

    public DayListAdapter(Context context, SubjectModel subjectModel, List<DayModel> list) {
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
    public void onBindViewHolder(@NonNull DayListAdapter.ViewHolder holder, int position) {
        DayModel model = mList.get(position);
        if (model != null) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(model);
            holder.btnOption.setTag(model);

            holder.date.setText(model.date == null ? "" : DateUtilityService.format_EEEdMMMyyyy_HHmm(model.date));
            if (model.verified) {
                holder.verified.setVisibility(View.VISIBLE);
                holder.btnOption.setVisibility(View.GONE);
            } else {
                if(mSubjectModel!=null && mSubjectModel.ended){
                    holder.btnOption.setVisibility(View.GONE);
                } else {
                    holder.btnOption.setVisibility(View.VISIBLE);
                }
                holder.verified.setVisibility(View.GONE);
            }



            holder.presencePercent.setText(mContext.getString(R.string.presence_percent, mDecimalFormat.format(model.presentPercent)));


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
    public DayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_day_list, parent, false);
        return new DayListAdapter.ViewHolder(itemView);
    }

    public void setItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setList(List<DayModel> list) {
        mList = list;
    }

    public void setOptionClickListener(View.OnClickListener listener) {
        mOptionClickListener = listener;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        mSubjectModel = subjectModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public ImageButton btnOption;
        public ImageView verified;
        public TextView presencePercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvDate);
            btnOption = itemView.findViewById(R.id.btnOption);
            verified = itemView.findViewById(R.id.imgVerified);
            presencePercent = itemView.findViewById(R.id.tvPresencePercent);
        }

    }
}
