package com.zk.manylayout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.v7.widget.LinearLayoutManager.*;

import static android.support.v7.widget.LinearLayoutManager.*;

public final class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    RecyclerView mRecy;
    ListView     mLv;
    //    List<Integer> mList = new ArrayList<>();
    List<Bean> mList = new ArrayList<>();
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        initView();
        CrashHandler.getInstance().init(this);
    }

    private void initList() {
        //随机数 用来标记item界面的类型
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int nextInt = random.nextInt(3);
            Bean bean = new Bean();
            //nexInte(3) 随机 生成的0，1，2
            //每循环一次 就给当前位置的条目设置一个标签类型
            bean.type = nextInt;
            mList.add(bean);
            Log.e("随机数",nextInt+"/n");;
        }
    }

    private void initView() {
        mRecy = (RecyclerView) findViewById(R.id.mRecy);
        mLv = (ListView) findViewById(R.id.mLv);
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        //TODO 分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.HORIZONTAL_LIST);
        mRecy.addItemDecoration ( itemDecoration);
        //        mRecy.setAdapter(new MyAdapter(this, mList));
        //        mLv.setAdapter(new MyAdapter2(mList,this));
        mRecy.setAdapter(new MyAdapter3(this, mList));
        mLv.setOnItemClickListener(this);
       fManager = getFragmentManager();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        NewContentFragment nlFragment = new NewContentFragment();
        fTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        fTransaction.replace(R.id.mRecy, nlFragment);
        //调用addToBackStack将Fragment添加到栈中
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}
