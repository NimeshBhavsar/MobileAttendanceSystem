package com.krishna.attendance.Presentation.Activities.Students.Details;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Presentation.Activities.Students.Create.StudentCreateFragment;
import com.krishna.attendance.R;

import java.text.DecimalFormat;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity implements StudentCreateFragment.OnFragmentInteractionListener {

    public static final String ARG_SUBJECT_ID = "arg_subject_id";
    public static final String ARG_STUDENT_ID = "arg_student_id";

    private long mSubjectId;
    private long mStudentId;

    private StudentDetailViewModel mViewModel;
    private StudentDetailAdapter mAdapter;
    private StudentModel mStudentModel;
    private SubjectModel mSubjectModel;

    private RecyclerView mRecyclerView;
    private ImageView imgImage;
    private TextView tvSubject;
    private TextView tvGroup;
    private TextView tvCrn;
    private TextView tvPresence;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSubjectId = bundle.getLong(ARG_SUBJECT_ID);
            mStudentId = bundle.getLong(ARG_STUDENT_ID);
        }

        initToolbar();

        initRecyclerView();

        initControls();

        initViewModel();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // mRecyclerView.addItemDecoration( new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
    }

    private void initControls() {
        tvSubject = findViewById(R.id.tvSubject);
        tvGroup = findViewById(R.id.tvGroup);
        tvCrn = findViewById(R.id.tvCrn);
        tvPresence = findViewById(R.id.tvPresencePercent);
        imgImage = findViewById(R.id.imgImage);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(StudentDetailViewModel.class);

        mViewModel.getSubject(mSubjectId).observe(this, new Observer<SubjectModel>() {
            @Override
            public void onChanged(SubjectModel subjectModel) {
                mSubjectModel = subjectModel;
                if(subjectModel!=null){
                    mToolbar.setTitle(subjectModel.course);
                    mToolbar.setSubtitle(subjectModel.name);
                    mToolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.Text_SubTitle);

                    tvSubject.setText(subjectModel.course);
                    tvGroup.setText(subjectModel.name);
                }
            }
        });

        mViewModel.getStudent(mSubjectId, mStudentId).observe(this, new Observer<StudentModel>() {
            @Override
            public void onChanged(StudentModel studentModel) {
                mStudentModel = studentModel;
                if(studentModel!=null){
                    // populate
                    tvCrn.setText(studentModel.crn);
                    mCollapsingToolbarLayout.setTitle(studentModel.name);

                    tvPresence.setText( "Presence: "+
                            studentModel.presenceDays + " / " +
                            studentModel.classDays + " ("+
                            new DecimalFormat("##0.0").format(studentModel.presencePercent) +
                            "%)");
                }
            }
        });
        mViewModel.getAttendanceList(mSubjectId, mStudentId).observe(this, new Observer<List<AttendanceModel>>() {
            @Override
            public void onChanged(List<AttendanceModel> models) {
                if(mAdapter==null){
                    if(models.size()>0 && models.get(models.size()-1) !=null){
                        models.add(null);
                    }
                    mAdapter = new StudentDetailAdapter(getApplicationContext(), models);
                    // initItemClickListeners();
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setList(models);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentAction(ResponseModel response) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_edit:
                if(mSubjectModel!=null && mSubjectModel.ended){
                    Toast.makeText(getApplicationContext(), "You can't edit students of ended subject session.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                StudentCreateFragment fragment = StudentCreateFragment.newInstance(mSubjectId, mStudentId);
                fragment.show(getSupportFragmentManager(), StudentCreateFragment.DIALOG_TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
