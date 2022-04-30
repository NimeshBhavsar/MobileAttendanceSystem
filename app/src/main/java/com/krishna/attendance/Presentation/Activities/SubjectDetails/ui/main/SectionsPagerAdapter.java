package com.krishna.attendance.Presentation.Activities.SubjectDetails.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.krishna.attendance.Presentation.Activities.Days.DayListFragment;
import com.krishna.attendance.Presentation.Activities.Students.StudentListFragment;
import com.krishna.attendance.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_students, R.string.tab_attendances};
    private final Context mContext;
    private long mSubjectId;

    public SectionsPagerAdapter(Context context, FragmentManager fm, long subjectId) {
        super(fm);
        mContext = context;
        mSubjectId = subjectId;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return StudentListFragment.newInstance(mSubjectId);
            case 1:
                return DayListFragment.newInstance(mSubjectId);
            default:
                break;
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}