package com.krishna.attendance.Presentation.Activities.Days;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Presentation.Activities.Attendances.AttendanceListActivity;
import com.krishna.attendance.Presentation.Activities.Attendances.Create.AttendanceCreateFragment;
import com.krishna.attendance.R;

import java.util.List;

public class DayListFragment extends Fragment {

    private DayListViewModel mViewModel;
    private DayListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private SubjectModel mSubjectModel;

    private long mSubjectId;

    public DayListFragment() {
    }

    public DayListFragment(long subjectId) {
        mSubjectId = subjectId;
    }

    public static DayListFragment newInstance(long subjectId) {
        return new DayListFragment(subjectId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // earlier viewmodel was initialized here; now in 'initViewModel', later do injection
        // mViewModel = ViewModelProviders.of(this).get(DayListViewModel.class);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);

        initControls();

        initViewModel();
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void initControls() {

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(DayListViewModel.class);
        mViewModel.getSubject(mSubjectId).observe(getViewLifecycleOwner(), new Observer<SubjectModel>() {
            @Override
            public void onChanged(SubjectModel subjectModel) {
                mSubjectModel = subjectModel;
                if (subjectModel != null && mAdapter != null) {
                    mAdapter.setSubjectModel(subjectModel);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        //ViewModelProviders.of(this).get(DayListViewModel.class);
        mViewModel.getDayList(mSubjectId).observe(getViewLifecycleOwner(), new Observer<List<DayModel>>() {
            @Override
            public void onChanged(List<DayModel> models) {
                if (models != null) {
                    if (models.size() > 0 && models.get(models.size() - 1) != null) {
                        models.add(null);
                    }
                    if (mAdapter == null) {
                        mAdapter = new DayListAdapter(getContext(), mSubjectModel, models);
                        initItemClickListeners();
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.setList(models);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

        });
    }

    private void initItemClickListeners() {
        mAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get to next activity
                DayModel model = (DayModel) view.getTag();
                if (model != null) {
                    Intent intent = new Intent(getActivity(), AttendanceListActivity.class);
                    intent.putExtra(AttendanceListActivity.ARG_DAY_ID, model.id);
                    intent.putExtra(AttendanceListActivity.ARG_SUBJECT_ID, model.subjectId);
                    startActivity(intent);
                }
            }
        });

        mAdapter.setOptionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DayModel model = (DayModel) view.getTag();
                if (model != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.ic_menu_24dp)
                            .setTitle("Options")
                            .setItems(new String[]{"Edit", "Delete"},
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i) {
                                                case 0:
                                                    AttendanceCreateFragment fragment = AttendanceCreateFragment.newInstance(mSubjectId, model.id);
                                                    fragment.show(getParentFragmentManager(), AttendanceCreateFragment.DIALOG_TAG);
                                                    break;
                                                case 1:
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle(R.string.delete_que)
                                                            .setIcon(R.drawable.ic_warning_color_24dp)
                                                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    ResponseModel response = mViewModel.deleteDay(model.id);
                                                                    if (ResponseModel.shouldShowMessage(response)) {
                                                                        Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            })
                                                            .setNegativeButton(R.string.no, null)
                                                            .show();
                                                    break;
                                            }
                                        }
                                    });
                    builder.show();

                }
            }
        });
    }
}
