package com.krishna.attendance.Presentation.Activities.Students.Create;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.krishna.attendance.Core.Enums.ResponseStatusEnum;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Utilities.CrnUtilityService;
import com.krishna.attendance.R;

public class StudentCreateFragment extends DialogFragment {

    public static final String ARG_SUBJECT_ID = "arg_subject_id";
    public static final String ARG_STUDENT_ID = "arg_student_id";
    public static final String DIALOG_TAG = "student_create_fragment";
    private StudentCreateViewModel mViewModel;
    private long mSubjectId;
    private long mStudentId;
    private OnFragmentInteractionListener mListener;

    private Button btnSave;
    private Button btnCancel;
    private TextView tvTitle;
    private EditText etName;
    private EditText etCrn;

    public StudentCreateFragment() {
    }

    public StudentCreateFragment(long subjectId, long studentId) {
        mSubjectId = subjectId;
        mStudentId = studentId;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentAction(ResponseModel response);
    }

    public static StudentCreateFragment newInstance(long subjectId, long studentId) {
        return new StudentCreateFragment(subjectId, studentId);
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
            mSubjectId = getArguments().getInt(ARG_SUBJECT_ID);
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
        return inflater.inflate(R.layout.fragment_student_create, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initToolbar(view);
        initControls(view);

        initViewModel();

        initClickListeners();
    }

    void initControls(View view) {
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.add_student, mStudentId > 0 ? "Edit" : "Add"));
        etCrn = view.findViewById(R.id.etCrn);
        etName = view.findViewById(R.id.etName);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(StudentCreateViewModel.class);
        // SubjectModel subjectModel = mViewModel.getSubject(mSubjectId);
        StudentModel studentModel = mViewModel.getStudent(mStudentId);
        if (studentModel != null) {
            // populate the data
            etCrn.setText(studentModel.crn);
            etName.setText(studentModel.name);
        }
    }

    private void initClickListeners() {
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
                //mListener.onFragmentAction(ResponseModel.getNone());
                dismiss();
            }
        });
    }

    private void saveData() {
        StudentModel model = new StudentModel();
        model.id = mStudentId;
        model.subjectId = mSubjectId;
        model.crn = etCrn.getText().toString();
        model.name = etName.getText().toString();
        // TODO: get image in string format and assign to 'model'
        model.image = "";

        boolean isValid = true;
        if (model.crn.equals("")) {
            etCrn.setError("Required");
            isValid = false;
        }
        if (model.name.equals("")) {
            etName.setError("Required");
            isValid = false;
        }
        if (!isValid) {
            Toast.makeText(getActivity(), "You have error inputs!", Toast.LENGTH_SHORT).show();
            return;
        }
        ResponseModel response = mViewModel.saveStudent(model);
        if (response != null && !response.message.equals("")) {
            Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();

            if (response.status == ResponseStatusEnum.Fail) {
                etCrn.setError(response.message);
                return;
            }
            // clear the inputs if user is in 'add' mode ; else if the user is in 'edit' mode then dismiss
            if (mStudentId <= 0) {

                // clear the inputs but don't dismiss
                mStudentId = 0;
                etName.setText("");
                // etCrn.requestFocus();

                // increment the crn
                try {
                    etCrn.setText(CrnUtilityService.nextCrn(etCrn.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    etCrn.setText("");
                }

            } else {
                dismiss();
            }
        }
    }

}
