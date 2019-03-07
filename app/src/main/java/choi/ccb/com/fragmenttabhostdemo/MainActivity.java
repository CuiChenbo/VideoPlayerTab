package choi.ccb.com.fragmenttabhostdemo;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import choi.ccb.com.fragmenttabhostdemo.fragment.CartFragment;
import choi.ccb.com.fragmenttabhostdemo.fragment.CategoryFragment;
import choi.ccb.com.fragmenttabhostdemo.fragment.HotFragment;
import choi.ccb.com.fragmenttabhostdemo.fragment.HomeFragment;
import choi.ccb.com.fragmenttabhostdemo.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private FrameLayout flContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = findViewById(R.id.tabHost);
        flContent = findViewById(R.id.flContent);
        initData();
        initView();
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_SETTINGS},
                11111);
    }

    private List<TabBean> mTabs = null;
    private void initData() {
        mTabs = new ArrayList();
        mTabs.add(new TabBean(HomeFragment.class,R.string.home,R.mipmap.ic_launcher_round));
        mTabs.add(new TabBean(HotFragment.class,R.string.hot,R.mipmap.ic_launcher_round));
        mTabs.add(new TabBean(CategoryFragment.class,R.string.category,R.mipmap.ic_launcher_round));
        mTabs.add(new TabBean(CartFragment.class,R.string.cart,R.mipmap.ic_launcher_round));
        mTabs.add(new TabBean(MineFragment.class,R.string.mine,R.mipmap.ic_launcher_round));
    }

    private void initView() {
        mTabHost.setup(this,getSupportFragmentManager(),R.id.flContent);

        for(TabBean tab : mTabs){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String s) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }

        mTabHost.setCurrentTab(0);
        upTabHostState();
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabTitle) {
               upTabHostState();
            }
        });
    }

    private void upTabHostState() {
        TabWidget tabw = mTabHost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View view = tabw.getChildAt(i);
            TextView tv = (TextView) view.findViewById(R.id.tab_title);
            if (i == mTabHost.getCurrentTab()) {
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                tv.setTextColor(getResources().getColor(R.color.textColor));
            }

        }
    }

    //构建Indicator
    private View buildIndicator(TabBean tab){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        TextView text = (TextView) view.findViewById(R.id.tab_title);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return  view;
    }
}
