package com.chen.firstdemo.greendao_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.chen.firstdemo.Chen;
import com.chen.firstdemo.greendao_demo.dao.ClassroomDomainDao;
import com.chen.firstdemo.greendao_demo.dao.DaoSession;
import com.chen.firstdemo.greendao_demo.dao.UserDomainDao;
import com.chen.firstdemo.greendao_demo.entry.ClassroomDomain;
import com.chen.firstdemo.greendao_demo.entry.UserDomain;

import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class GreendaoActivity extends AppCompatActivity {

    LinearLayout linearLayout ;
    LinearLayout.LayoutParams params ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout = new LinearLayout(this);
        this.setContentView(linearLayout);

        Chen application = (Chen)getApplication();
        DaoSession daoSession = application.getDaoSession();

        UserDomainDao userDao = daoSession.getUserDomainDao();
        ClassroomDomainDao classroomDao = daoSession.getClassroomDomainDao();

        try{
            userDao.deleteAll();
            classroomDao.deleteAll();
        }catch (Exception e){

        }

        /*插入一个教室*/
        classroomDao.insert(new ClassroomDomain(2112L,"阶梯教室"));

        for (int i = 0; i < 20; i++) {
            UserDomain userDomain = new UserDomain();
            userDomain.setId(i);
            userDomain.setUsername("张"+i);
            userDomain.setGender(i%2==0?1:0);
            userDomain.setClassroomId(21112L);
            userDao.insert(userDomain);
        }

        /*多表联查*/

//        List<UserDomain> list = userDao.queryBuilder()
//                .where(UserDomainDao.Properties.Username.like("张%1"))
//                .where(UserDomainDao.)
//                .list();

//        for(UserDomain userDomain:list ){
//            if(!userDomain.getClassroomDomain().getRoomname().equals("阶梯教")){
//                list.remove(userDomain);
//            }
//        }
//
//        for (UserDomain userDomain:list ){
//            Log.i("aaa", "userDomain: "+userDomain.toString());
//        }
    }

    class NewBean {
        UserDomain userDomain;
        ClassroomDomain classroomDomain ;
    }
}
