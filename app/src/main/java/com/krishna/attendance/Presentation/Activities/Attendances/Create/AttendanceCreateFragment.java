package com.krishna.attendance.Presentation.Activities.Attendances.Create;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.attendance.Core.Enums.ResponseStatusEnum;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Utilities.DateUtilityService;
import com.krishna.attendance.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceCreateFragment extends DialogFragment {

    public static final String ARG_SUBJECT_ID = "arg_subject_id";
    public static final String ARG_DAY_ID = "arg_day_id";
    public static final String DIALOG_TAG = "attendance_create_fragment";
    private AttendanceCreateViewModel mViewModel;
    private long mSubjectId;
    private long mDayId;
    private OnFragmentInteractionListener mListener;

    private Button btnSave;
    private Button btnCancel;
    private EditText etDate;
    private TextView tvTitle;
    private RecyclerView mRecyclerView;

    private AttendanceCreateAdapter mAdapter;

    private Date mDate;
    private AlertDialog dialogDate;
    private ImageButton btnDate;

    public AttendanceCreateFragment() {
    }

    public AttendanceCreateFragment(long subjectId, long dayId) {
        mSubjectId = subjectId;
        mDayId = dayId;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentAction(ResponseModel response);
    }

    public static AttendanceCreateFragment newInstance(long subjectId, long dayId) {
        return new AttendanceCreateFragment(subjectId, dayId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSubjectId = getArguments().getLong(ARG_SUBJECT_ID);
            mDayId = getArguments().getLong(ARG_DAY_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
        // return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance_create, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initToolbar(view);
        initControls(view);

        initRecyclerView(view);

        initViewModel();

        initDateAlertDialog();

        initClickListeners();
    }

    void initControls(View view) {
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(mDayId > 0 ? R.string.edit_attendance: R.string.add_attendance);
        btnDate = view.findViewById(R.id.btnDate);
        etDate = view.findViewById(R.id.etDate);
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AttendanceCreateViewModel.class);
        // SubjectModel subjectModel = mViewModel.getSubject(mSubjectId);
        DayModel dayModel = mViewModel.getDay(mDayId);
        if (dayModel == null) {
            // populate the data
            mDate = Calendar.getInstance().getTime();
        } else {
            mDate = dayModel.date;
        }
        etDate.setText(DateUtilityService.format_EEEdMMMyyyy(mDate));

        // populate the student list
        List<AttendanceModel> models = mViewModel.getAttendanceList(mSubjectId, mDayId);
        if(models!=null){
            /*if (models.get(models.size() - 1) != null) {
                models.add(null);
            }*/
            if (mAdapter == null) {
                mAdapter = new AttendanceCreateAdapter(models);
                // initItemClickListeners();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setList(models);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    private void initClickListeners() {

        // TODO:: resolve below "setOnTouchListener" issue
        etDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // show date dialog
                dialogDate.show();
                return true;
            }

        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save
                saveData();
                // call listener
                // mListener.onFragmentAction(response);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call listener
                mListener.onFragmentAction(ResponseModel.getNone());
                dismiss();
            }
        });
    }

    void initDateAlertDialog() {
        DatePicker datePicker = new DatePicker(getContext());
        //necessary; when user clicks on side of the date picker then it should close
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.dismiss();
            }
        });
        DatePicker.OnDateChangedListener listener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                /*String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                etDate.setText(text);
                etDate.setSelection(etDate.getText().toString().length());*/
                Calendar cal = Calendar.getInstance();
                cal.set(year, monthOfYear, dayOfMonth);
                String dateStr = DateUtilityService.format_EEEdMMMyyyy(cal.getTime());
                etDate.setText(dateStr);
                mDate = cal.getTime();
                dialogDate.dismiss();
            }
        };
        Calendar calendar = Calendar.getInstance(); //right now
        //datePicker.updateDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), listener);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//getActivity()
        // 2. Chain together various setter methods to set the dialog characteristics
        builder//.setMessage("Choose Date")//R.string.dialog_message
                .setView(datePicker)
                .setTitle("Choose Date"); //R.string.dialog_title
        // 3. Get the AlertDialog from create()
        dialogDate = builder.create();
    }

    private void saveData() {
        DayModel model = new DayModel();
        model.id = mDayId;
        model.subjectId = mSubjectId;
        boolean isValid = true;
        //String dateStr = etDate.getText().toString();
       /* if (dateStr.equals("")) {
            etDate.setError("Required");
            isValid = false;
        } else {
            try {
                model.date = DateUtilityService.parse_yyyyMMdd(dateStr);
                    //new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
        model.date = mDate;
        model.attendanceList = mAdapter.getList();
        if(model.attendanceList == null || model.attendanceList.size() == 0){
            Toast.makeText(getActivity(), "There aren't any students for attendance!", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (!isValid) {
            Toast.makeText(getActivity(), "You have error inputs!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        ResponseModel response = mViewModel.saveAttendance(model);
        if (response != null && !response.message.equals("")) {
            Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
            if (response.status != ResponseStatusEnum.Fail) {
                dismiss();
            }
        }
    }

}
