package com.krishna.attendance.Presentation.Activities.Attendances;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Core.Utilities.DateUtilityService;
import com.krishna.attendance.Presentation.Activities.Attendances.Create.AttendanceCreateFragment;
import com.krishna.attendance.R;

import java.text.DecimalFormat;
import java.util.List;

public class AttendanceListActivity extends AppCompatActivity implements AttendanceCreateFragment.OnFragmentInteractionListener {

    public static final String ARG_DAY_ID = "arg_day_id";
    public static final String ARG_SUBJECT_ID = "arg_subject_id";

    private AttendanceListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private AttendanceListAdapter mAdapter;

    private long mDayId;
    private long mSubjectId;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFabEdit;
    private FloatingActionButton mFabVerify;
    private FloatingActionButton mFabVerified;
    private TextView tvSubject;
    private TextView tvGroup;
    private TextView tvPresence;

    private DayModel mDayModel;
    private SubjectModel mSubjectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDayId = bundle.getLong(ARG_DAY_ID, 0);
            mSubjectId = bundle.getLong(ARG_SUBJECT_ID, 0);
        }

        initToolbar();

        initRecyclerView();

        initControls();

        initViewModel();

        initClickListeners();

    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
    }

    private void initControls() {
        mFabEdit = findViewById(R.id.fabEdit);
        mFabVerify = findViewById(R.id.fabVerify);
        mFabVerified = findViewById(R.id.fabVerified);

        tvSubject = findViewById(R.id.tvSubject);
        tvGroup = findViewById(R.id.tvGroup);
        tvPresence = findViewById(R.id.tvPresencePercent);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AttendanceListViewModel.class);

        // TODO:: get subject's attributes in .getDay(..) liveData to reduce observers
        mViewModel.getSubject(mSubjectId).observe(this, new Observer<SubjectModel>() {
            @Override
            public void onChanged(SubjectModel subjectModel) {
                mSubjectModel = subjectModel;
                if(subjectModel!=null){
                    if(subjectModel.ended){
                        mFabEdit.setVisibility(View.GONE);
                        mFabVerify.setVisibility(View.GONE);
                    }
                    tvSubject.setText(subjectModel.course);
                    tvGroup.setText(subjectModel.name);
                }
            }
        });

        mViewModel.getDay(mDayId).observe(this, new Observer<DayModel>() {
            @Override
            public void onChanged(DayModel dayModel) {
                if (dayModel != null) {
                    mDayModel = dayModel;
                    if (dayModel.verified) {
                        mFabEdit.setVisibility(View.GONE);
                        mFabVerify.setVisibility(View.GONE);
                        mFabVerified.setVisibility(View.VISIBLE);
                    } else {
                        mFabVerified.setVisibility(View.GONE);
                    }
                    mCollapsingToolbarLayout.setTitle(DateUtilityService.format_EEEdMMMyyyy(dayModel.date));
                    tvPresence.setText( "Present: "+
                            dayModel.presentStudents + " / " +
                            dayModel.totalStudents + " ("+
                            new DecimalFormat("##0.0").format(dayModel.presentPercent) +
                            "%)");
                    //mToolbar.setTitle(DateUtilityService.format_EEEdMMMyyyy(dayModel.date));
//                    mToolbar.setSubtitle(dayModel.subject + " (" + dayModel.group + ")");
//                    mToolbar.setSubtitleTextAppearance(getApplicationContext(), R.style.Text_SubTitle);
                }
            }
        });


        mViewModel.getAttendanceList(mSubjectId, mDayId).observe(this, new Observer<List<AttendanceModel>>() {

            @Override
            public void onChanged(List<AttendanceModel> models) {
                if (models != null) {
                    if (models.size() > 0 && models.get(models.size() - 1) != null) {
                        models.add(null);
                    }
                    if (mAdapter == null) {
                        mAdapter = new AttendanceListAdapter(getApplicationContext(), models);
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



    private void initClickListeners() {
        mFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClicked();
            }
        });

        mFabVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyClicked();
            }
        });
        mFabVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendanceListActivity.this, "Attendance sheet already verified!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initItemClickListeners() {
        mAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get to next activity
                AttendanceModel model = (AttendanceModel) view.getTag();
                if (model != null) {
                    /*Intent intent = new Intent(getActivity(), .class);
                    intent.putExtra(StudentDetailActivity.ARG_SUBJECT_ID, model.id);
                    intent.putExtra(StudentDetailActivity.ARG_STUDENT_ID, model.id);
                    startActivity(intent);*/
                }
            }
        });

        mAdapter.setOptionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceModel model = (AttendanceModel) view.getTag();
                if (model != null) {
                    /*StudentCreateFragment fragment = StudentCreateFragment.newInstance(mSubjectId, model.id);
                    fragment.show(getFragmentManager(), SubjectCreateFragment.DIALOG_TAG);*/
                }
            }
        });
    }

    private void editClicked() {
        if (mDayModel != null && mDayModel.verified) {
            Toast.makeText(this, "You can't edit a verified attendance sheet.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSubjectModel!=null && mSubjectModel.ended){
            Toast.makeText(this, "You can't make any edits to an ended subject session.", Toast.LENGTH_SHORT).show();
            return;
        }
        AttendanceCreateFragment fragment = AttendanceCreateFragment.newInstance(mSubjectId, mDayId);
        fragment.show(getSupportFragmentManager(), AttendanceCreateFragment.DIALOG_TAG);
    }

    private void verifyClicked() {

        if (mDayModel != null && mDayModel.verified) {
            Toast.makeText(this, "Already verified!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSubjectModel!=null && mSubjectModel.ended){
            Toast.makeText(this, "You can't make any edits to an ended subject session.", Toast.LENGTH_SHORT).show();
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceListActivity.this);
        builder.setMessage("You will not be able to edit/delete once this attendance sheet is verified.")
                .setNegativeButton(R.string.cancel, null)
                .setIcon(R.drawable.ic_warning_color_24dp)
                .setTitle("Verify?")
                .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ResponseModel response = mViewModel.verify(mDayId);
                        if (response != null) {
                            if (!response.message.equals("")) {
                                Toast.makeText(AttendanceListActivity.this, response.message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attendance_list, menu);
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
                editClicked();
                return true;
            case R.id.menu_verify:
                verifyClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
