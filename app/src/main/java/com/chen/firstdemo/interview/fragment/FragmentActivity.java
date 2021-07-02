package com.chen.firstdemo.interview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActivity extends BaseActivity {

    @BindView(R.id.addAFraBtn)
    Button addAFraBtn;
    @BindView(R.id.addBFraBtn)
    Button addBFraBtn;
    @BindView(R.id.convertBFraBtn)
    Button convertBFraBtn;
    @BindView(R.id.convertAFraBtn)
    Button convertAFraBtn;
    @BindView(R.id.fragmentFL)
    FrameLayout fragmentFL;
    @BindView(R.id.attachAFraBtn)
    Button attachAFraBtn;

    Fragment afragmet;
    Fragment bfragmet;

    @BindView(R.id.showAFraBtn)
    Button showAFraBtn;
    @BindView(R.id.showBFraBtn)
    Button showBFraBtn;
    @BindView(R.id.hideAFraBtn)
    Button hideAFraBtn;
    @BindView(R.id.hideBFraBtn)
    Button hideBFraBtn;
    @BindView(R.id.removeAFraBtn)
    Button removeAFraBtn;
    @BindView(R.id.removeBFraBtn)
    Button removeBFraBtn;
    @BindView(R.id.attachBFraBtn)
    Button attachBFraBtn;
    @BindView(R.id.addToStack1Btn)
    Button addToStack1Btn;
    @BindView(R.id.addToStack2Btn)
    Button addToStack2Btn;
    @BindView(R.id.popStackBtn)
    Button popStackBtn;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        if(savedInstanceState == null){
            manager = this.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            /**
             * 事务被提交之后，则失去作用，需要重新manager.beginTransaction()获取一个新的事务
             * return new BackStackRecord(this);
             */
            transaction.setReorderingAllowed(true);
            transaction.commit();
        }


        afragmet = new AFragment();
        afragmet.setArguments(null);
        bfragmet = new BFragment();

        attachAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.attach(afragmet);
                transaction.commit();
                print();
            }
        });
        attachBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.attach(bfragmet);
                transaction.commit();
                print();
            }
        });

        addAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (afragmet.isAdded()) {
                    return;
                }
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragmentFL, afragmet);
                transaction.commit();
                print();
            }
        });

        addBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bfragmet.isAdded()) {
                    return;
                }
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragmentFL, bfragmet);
                transaction.commit();
                print();
            }
        });
        convertAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentFL, afragmet);
                transaction.commit();
                print();
            }
        });
        convertBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentFL, bfragmet);
                transaction.commit();
                print();
            }
        });

        showAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(afragmet);
                transaction.commit();
                print();
            }
        });
        showBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(bfragmet);
                transaction.commit();
                print();
            }
        });
        hideAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(afragmet);
                transaction.commit();
                print();
            }
        });

        hideBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(bfragmet);
                transaction.commit();
                print();
            }
        });

        removeAFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(afragmet);
                transaction.commit();
                print();
            }
        });
        removeBFraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(bfragmet);
                transaction.commit();
                print();
            }
        });

        addToStack1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(afragmet);
                transaction.hide(bfragmet);
                transaction.addToBackStack("事务1");
                transaction.commit();
                print();
            }
        });
        addToStack2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(bfragmet);
                transaction.hide(afragmet);
                transaction.addToBackStack("事务2");
                transaction.commit();
                print();
            }
        });
        popStackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.popBackStack();
            }
        });
    }

    private void print() {
//        Log.i(TAG, "afragmet.isHidden(): "+afragmet.isHidden());
//        Log.i(TAG, "afragmet.isVisible(): "+afragmet.isVisible());
//        Log.i(TAG, "bfragmet.isHidden(): "+bfragmet.isHidden());
//        Log.i(TAG, "bfragmet.isVisible(): "+bfragmet.isVisible());
//        Log.i(TAG, "printing.... ");
//        List<Fragment> fragments = manager.getFragments();
//        for (Fragment f : fragments) {
//            Log.i(TAG, "print: " + f.getClass());
//        }
////        Log.i(TAG, "print manager.getBackStackEntryCount() : " + manager.getBackStackEntryCount());
////
//        if (manager.getBackStackEntryCount() > 0) {
//            for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
//                FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(i);
//                Log.i(TAG, "print: entry.getName() " + entry.getName());
//            }
//        }
    }
}
