package cn.edu.njupt.iot.b16070520.express;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.njupt.iot.b16070520.express.fragment.CodeFragment;
import cn.edu.njupt.iot.b16070520.express.fragment.ExpressFragment;
import cn.edu.njupt.iot.b16070520.express.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {


    /*
        数据适配器的作用：把复杂的数据(数组、链表、数据库、集合等)填充到在指定视图界面上。是连接数据源和视图界面的桥梁
        ArrayAdapter(上下文, 当前ListView加载的每一个列表项所对应的布局文件, 数据源)用于绑定格式单一的数据，数据源可以是集合或者数组
        SimpleAdapter用于绑定个数复杂的数据，数据源只能是特定泛型的集合
        新建适配器->添加数据源到适配器->视图加载适配器
     */

    /*
        ViewPager相当于一个容器，数据源Fragment或者View集合
     */
    //TabLayout
    private TabLayout mTabLayout;

    //ViewPager
    private ViewPager mViewPager;

    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);


        initData();
        initView();

    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("物流查询"); //物流查询
        mTitle.add("二维码扫描"); //二维码扫描
        mTitle.add("个人中心"); //个人中心


        mFragment = new ArrayList<>();
        mFragment.add(new ExpressFragment());
        mFragment.add(new CodeFragment());
        mFragment.add(new UserFragment());
    }

    @SuppressLint("RestrictedApi")
    private void initView() {

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);


        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());


        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
