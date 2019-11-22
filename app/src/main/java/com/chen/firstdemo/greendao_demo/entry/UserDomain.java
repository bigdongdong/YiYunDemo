package com.chen.firstdemo.greendao_demo.entry;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.DaoException;
import com.chen.firstdemo.greendao_demo.dao.DaoSession;
import com.chen.firstdemo.greendao_demo.dao.ClassroomDomainDao;
import com.chen.firstdemo.greendao_demo.dao.UserDomainDao;

@Entity
public class UserDomain {

    @Override
    public String toString() {
        return "id="+id+"   username="+username+"   gender="+gender+"   isVip="+isVip;

    }

    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "name")
    @NotNull
    private String username;

    @NotNull
    private int gender;

    @Transient
    private boolean isVip;

    private Long classroomId ;

    /**
     * 将ClassroomDomain实体绑定到classroomId这个id上
     */
    @ToOne(joinProperty = "classroomId")
    private ClassroomDomain classroomDomain;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1861872003)
    private transient UserDomainDao myDao;

    @Generated(hash = 1361891325)
    private transient Long classroomDomain__resolvedKey;

    @Generated(hash = 1057155539)
    public UserDomain(long id, @NotNull String username, int gender, Long classroomId) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.classroomId = classroomId;
    }

    @Generated(hash = 720870902)
    public UserDomain() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Long getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1630940762)
    public ClassroomDomain getClassroomDomain() {
        Long __key = this.classroomId;
        if (classroomDomain__resolvedKey == null
                || !classroomDomain__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClassroomDomainDao targetDao = daoSession.getClassroomDomainDao();
            ClassroomDomain classroomDomainNew = targetDao.load(__key);
            synchronized (this) {
                classroomDomain = classroomDomainNew;
                classroomDomain__resolvedKey = __key;
            }
        }
        return classroomDomain;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 633166969)
    public void setClassroomDomain(ClassroomDomain classroomDomain) {
        synchronized (this) {
            this.classroomDomain = classroomDomain;
            classroomId = classroomDomain == null ? null : classroomDomain.getId();
            classroomDomain__resolvedKey = classroomId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 165064394)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDomainDao() : null;
    }

}