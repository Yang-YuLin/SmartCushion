package cn.edu.nuc.cushion.passenger;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.BottomNavigationView;
import com.orhanobut.logger.Logger;

import cn.edu.nuc.cushion.mine.MineFragment;
import cn.edu.nuc.cushion.R;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class PassengerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView = null;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view_passenger);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_bus:
                        showFragment(0);
                        return true;
                    case R.id.navigation_mine:
                        showFragment(1);
                        return true;
                }
                return false;
            }
        });
        initFragment();
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());//底部菜单默认选中第一个
    }

    private void initFragment() {
        if (fragmentList != null) {
            fragmentList.clear();
        } else {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new BusFragment());
        fragmentList.add(new MineFragment());
    }

    /**
     * 使用show、hide来管理fragment
     */
    private void showFragment(int position) {
        if (fragmentList != null && fragmentList.size() > 0) {
            Fragment fragment = fragmentList.get(position);//得到需要的fragment
            if (fragment != null && currentFragment != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment != null) {
                    transaction.hide(currentFragment);//隐藏当前fragment
                } else {
                    Logger.d("current f == null");
                }
                currentFragment = fragment;//把当前的fragment设置为现在需要的fragment
                if (!fragment.isAdded()) {
                    transaction.add(R.id.fragment_container, fragment);
                } else {
                    transaction.show(fragment);
                }
                transaction.commit();
            }
        }
    }
}
