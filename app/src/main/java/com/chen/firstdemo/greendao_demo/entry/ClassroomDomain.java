package com.chen.firstdemo.greendao_demo.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ClassroomDomain {

    @Id
    private Long id ;

    @Property(nameInDb = "name")
    private String roomname ;

    @Generated(hash = 574979127)
    public ClassroomDomain(Long id, String roomname) {
        this.id = id;
        this.roomname = roomname;
    }

    @Generated(hash = 1581197456)
    public ClassroomDomain() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomname() {
        return this.roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }


}
