package com.krishna.attendance.Presentation.Activities.Students;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Presentation.Activities.Students.Create.StudentCreateFragment;
import com.krishna.attendance.Presentation.Activities.Students.Details.StudentDetailActivity;
import com.krishna.attendance.Presentation.Activities.Subjects.Create.SubjectCreateFragment;
import com.krishna.attendance.R;

import java.util.List;

public class StudentListFragment extends Fragment {

    private StudentListViewModel mViewModel;

    private long mSubjectId;
    private SubjectModel mSubjectModel;

    private RecyclerView mRecyclerView;
    private StudentListAdapter mAdapter;

    public StudentListFragment() {
    }

    public StudentListFragment(long subjectId) {
        mSubjectId = subjectId;
    }

    public static StudentListFragment newInstance(long subjectId) {
        return new StudentListFragment(subjectId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list, container, false);
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
        mViewModel = new ViewModelProvider(this).get(StudentListViewModel.class);
        //ViewModelProviders.of(this).get(StudentListViewModel.class);
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
        mViewModel.getStudentListLive(mSubjectId).observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> models) {
                if (models != null) {
                    if (models.size() > 0 && models.get(models.size() - 1) != null) {
                        models.add(null);
                    }
                    if (mAdapter == null) {
                        mAdapter = new StudentListAdapter(getContext(), mSubjectModel, models);
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
                StudentModel model = (StudentModel) view.getTag();
                if (model != null) {
                    Intent intent = new Intent(getActivity(), StudentDetailActivity.class);
                    intent.putExtra(StudentDetailActivity.ARG_SUBJECT_ID, mSubjectId);
                    intent.putExtra(StudentDetailActivity.ARG_STUDENT_ID, model.id);
                    startActivity(intent);
                }
            }
        });

        mAdapter.setOptionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StudentModel model = (StudentModel) view.getTag();
                if (model != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Options")
                            .setIcon(R.drawable.ic_menu_24dp)
                            .setItems(new String[]{"Edit", "Delete"},
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i) {
                                                case 0:
                                                    StudentCreateFragment fragment = StudentCreateFragment.newInstance(mSubjectId, model.id);
                                                    fragment.show(getParentFragmentManager(), SubjectCreateFragment.DIALOG_TAG);
                                                    break;
                                                case 1:
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle(R.string.delete_que)
                                                            .setIcon(R.drawable.ic_warning_color_24dp)
                                                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    ResponseModel response = mViewModel.deleteStudent(mSubjectId, model.id);
                                                                    if (ResponseModel.shouldShowMessage(response)) {
                                                                        Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
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
