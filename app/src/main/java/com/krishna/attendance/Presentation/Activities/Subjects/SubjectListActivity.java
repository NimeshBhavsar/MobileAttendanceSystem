package com.krishna.attendance.Presentation.Activities.Subjects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Presentation.Activities.SubjectDetails.SubjectDetailActivity;
import com.krishna.attendance.Presentation.Activities.Subjects.Create.SubjectCreateFragment;
import com.krishna.attendance.R;

import java.util.List;

public class SubjectListActivity extends AppCompatActivity implements SubjectCreateFragment.OnFragmentInteractionListener {

    private SubjectListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SubjectListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        initToolbar();

        initRecyclerView();

        initControls();

        initViewModel();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
    }

    private void initControls() {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                SubjectCreateFragment fragment = SubjectCreateFragment.newInstance(0);
                fragment.show(getSupportFragmentManager(), SubjectCreateFragment.DIALOG_TAG);
            }
        });

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(SubjectListViewModel.class);
        mViewModel.getSubjectList().observe(this, new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> models) {
                if (models != null) {
                    if(models.size() > 0 && models.get(models.size()-1) !=null){
                        models.add(null);
                    }
                    if (mAdapter == null) {
                        mAdapter = new SubjectListAdapter(getApplicationContext(), models);
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
                SubjectModel model = (SubjectModel) view.getTag();
                if (model != null) {
                    Intent intent = new Intent(SubjectListActivity.this, SubjectDetailActivity.class);
                    intent.putExtra(SubjectDetailActivity.ARG_SUBJECT_ID, model.id);
                    startActivity(intent);
                }
            }
        });

        mAdapter.setOptionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SubjectModel model = (SubjectModel) view.getTag();
                if (model != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectListActivity.this)
                            .setIcon(R.drawable.ic_menu_24dp)
                            .setTitle("Options")
                            .setItems(new String[]{"Edit", "Delete"},
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i) {
                                                case 0:
                                                    SubjectCreateFragment fragment = SubjectCreateFragment.newInstance(model.id);
                                                    fragment.show(getSupportFragmentManager(), SubjectCreateFragment.DIALOG_TAG);
                                                    break;
                                                case 1:
                                                    new AlertDialog.Builder(SubjectListActivity.this)
                                                            .setIcon(R.drawable.ic_warning_color_24dp)
                                                            .setTitle(R.string.delete_que)
                                                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    ResponseModel response = mViewModel.deleteSubject(model.id);
                                                                    if (ResponseModel.shouldShowMessage(response)) {
                                                                        Toast.makeText(SubjectListActivity.this, response.message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onFragmentAction(ResponseModel response) {
        // handle below in fragment -- handled
        /*if (response != null) {
            if (!response.message.equals("")) {
                Toast.makeText(SubjectListActivity.this, response.message, Toast.LENGTH_SHORT).show();
            }
            if (response.status != ResponseStatusEnum.Fail) {
                if(mSubjectCreateFragment!=null){
                    mSubjectCreateFragment.dismiss();
                }
            }
        }*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session_list, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }*/
}
