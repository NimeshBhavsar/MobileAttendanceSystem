package com.krishna.attendance.Presentation.Activities.Subjects.Create;

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
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.R;

public class SubjectCreateFragment extends DialogFragment {

    public static final String ARG_SUBJECT_ID = "arg_subject_id";
    public static final String DIALOG_TAG = "subject_create_fragment";
    private SubjectCreateViewModel mViewModel;
    private long mSubjectId;
    private OnFragmentInteractionListener mListener;

    private Button btnSave;
    private Button btnCancel;
    private TextView tvTitle;
    private EditText etName;
    private EditText etCourse;

    public SubjectCreateFragment() {
    }

    public SubjectCreateFragment(long subjectId) {
        mSubjectId = subjectId;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentAction(ResponseModel response);
    }

    public static SubjectCreateFragment newInstance(long subjectId) {
        return new SubjectCreateFragment(subjectId);
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
        return inflater.inflate(R.layout.fragment_subject_create, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
         /*ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(p);*/
        if (getDialog()!= null && getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        tvTitle.setText(getString(R.string.add_subject, (mSubjectId > 0 ? "Edit" : "Create")));
        etName = view.findViewById(R.id.etName);
        etCourse = view.findViewById(R.id.etSubject);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(SubjectCreateViewModel.class);
        //ViewModelProviders.of(this).get(SubjectCreateViewModel.class);
        SubjectModel subjectModel = mViewModel.getSubject(mSubjectId);
        if (subjectModel != null) {
            // populate the data
            etCourse.setText(subjectModel.course);
            etName.setText(subjectModel.name);
        }
    }

    private void initClickListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save
                saveData(); //ResponseModel response =
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
        SubjectModel model = new SubjectModel();
        model.id = mSubjectId;
        model.course = etCourse.getText().toString();
        model.name = etName.getText().toString();
        boolean isValid = true;
        if (model.course.equals("")) {
            etCourse.setError("Required");
            isValid = false;
        }
        if (model.name.equals("")) {
            etName.setError("Required");
            isValid = false;
        }
        if (!isValid) {
            // return ResponseModel.getFail("You have error inputs!");
            Toast.makeText(getActivity(), "You have error inputs!", Toast.LENGTH_SHORT).show();
            return;
        }
        ResponseModel response = mViewModel.saveSubject(model);
        if (response != null && !response.message.equals("")) {
            Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
            if (response.status != ResponseStatusEnum.Fail) {
                dismiss();
            }
        }
    }

}
