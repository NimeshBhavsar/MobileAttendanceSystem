package com.krishna.attendance.Presentation.Activities.SubjectDetails;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.krishna.attendance.Core.Enums.ResponseStatusEnum;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Presentation.Activities.Attendances.Create.AttendanceCreateFragment;
import com.krishna.attendance.Presentation.Activities.Students.Create.StudentCreateFragment;
import com.krishna.attendance.Presentation.Activities.SubjectDetails.ui.main.SectionsPagerAdapter;
import com.krishna.attendance.Presentation.Activities.Subjects.Create.SubjectCreateFragment;
import com.krishna.attendance.R;

public class SubjectDetailActivity extends AppCompatActivity
        implements StudentCreateFragment.OnFragmentInteractionListener,
        SubjectCreateFragment.OnFragmentInteractionListener,
        AttendanceCreateFragment.OnFragmentInteractionListener {

    public static final String ARG_SUBJECT_ID = "arg_subject_id";

    private long mSubjectId;
    private ViewPager mViewPager;
    private SubjectDetailViewModel mViewModel;
    private Toolbar mToolbar;
    private FloatingActionButton fabStudentAdd;
    private FloatingActionButton fabAttendanceAdd;
    private FloatingActionButton fabEnded;

    private SubjectModel mSubjectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        // get subjectId from bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSubjectId = bundle.getLong(ARG_SUBJECT_ID);
        }


        initToolbar();

        initViewPager();

        iinitControls();

        initViewModel();

        initClickListeners();

    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mSubjectId);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

    }

    private void iinitControls() {
        fabStudentAdd = findViewById(R.id.fabStudentAdd);
        fabAttendanceAdd = findViewById(R.id.fabAttendanceAdd);
        fabEnded = findViewById(R.id.fabEnded);
    }

    private void initViewModel() {
        mViewModel = new SubjectDetailViewModel();
        mViewModel.getSubject(mSubjectId).observe(this, new Observer<SubjectModel>() {
            @Override
            public void onChanged(SubjectModel subjectModel) {
                if (subjectModel != null) {
                    if (subjectModel.ended) {
                        fabAttendanceAdd.setVisibility(View.GONE);
                        fabStudentAdd.setVisibility(View.GONE);
                        fabEnded.setVisibility(View.VISIBLE);
                    } else {
                        fabEnded.setVisibility(View.GONE);
                    }
                    mSubjectModel = subjectModel;
                    mToolbar.setTitle(subjectModel.course);
                    mToolbar.setSubtitle(subjectModel.name);
                    mToolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.Text_SubTitle);
                }
            }
        });
    }

    private void initClickListeners() {

        fabAttendanceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceAddClicked();
            }
        });

        fabStudentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentAddClicked();
            }
        });

        // fabEnded is shown only if the session is ended
        fabEnded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubjectDetailActivity.this, "Subject session already ended!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attendanceAddClicked() {
        if (mSubjectModel != null && mSubjectModel.ended) {
            Toast.makeText(getApplicationContext(), "You can't add attendance in ended subject sessions.", Toast.LENGTH_SHORT).show();
            return;
        }
        //mViewPager.setCurrentItem(1, true);
        AttendanceCreateFragment fragment = AttendanceCreateFragment.newInstance(mSubjectId, 0);
        fragment.show(getSupportFragmentManager(), AttendanceCreateFragment.DIALOG_TAG);
    }

    private void studentAddClicked() {
        if (mSubjectModel != null && mSubjectModel.ended) {
            Toast.makeText(getApplicationContext(), "You can't add students in ended subject sessions.", Toast.LENGTH_SHORT).show();
            return;
        }
        //mViewPager.setCurrentItem(0, true);
        StudentCreateFragment fragment = StudentCreateFragment.newInstance(mSubjectId, 0);
        fragment.show(getSupportFragmentManager(), StudentCreateFragment.DIALOG_TAG);
    }

    private void subjectEditClicked(){
        if (mSubjectModel != null && mSubjectModel.ended) {
            Toast.makeText(getApplicationContext(), "You can't edit subject whose session has ended.", Toast.LENGTH_SHORT).show();
            return;
        }
        SubjectCreateFragment fragment = SubjectCreateFragment.newInstance(mSubjectId);
        fragment.show(getSupportFragmentManager(), SubjectCreateFragment.DIALOG_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu
        getMenuInflater().inflate(R.menu.menu_subject_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentAction(ResponseModel response) {
        if (response != null) {
            if (response.status == ResponseStatusEnum.Success) {
                if (response.data.getClass() == DayModel.class) {
                    mViewPager.setCurrentItem(1);
                }else if(response.data.getClass() == StudentModel.class){
                    mViewPager.setCurrentItem(0);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_edit:
                subjectEditClicked();
                return true;
            case R.id.menu_add_attendance:
                attendanceAddClicked();
                return true;
            case R.id.menu_add_student:
                studentAddClicked();
                return true;
            case R.id.menu_end_session:
                if (mSubjectModel != null && mSubjectModel.ended) {
                    Toast.makeText(getApplicationContext(), "This subject session has already been ended.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectDetailActivity.this)
                        .setIcon(R.drawable.ic_warning_color_24dp)
                        .setTitle("End Session?")
                        .setMessage("Are you sure to end this subject session? " +
                                "You won't be able to make any changes to this subject after it's ended.")
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.menu_end_session, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ResponseModel response = mViewModel.endSession(mSubjectId);
                                if (ResponseModel.shouldShowMessage(response)) {
                                    Toast.makeText(getApplicationContext(), response.message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}