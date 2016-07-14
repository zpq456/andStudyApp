package kr.ac.hansung.criminallntent;

import android.support.v4.app.Fragment;

/**
 * Created by Owner on 2016-07-14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
