package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinUserWithSheep {
    @Id
    private Long id;

    private Long uId;

    private Long sId;

    @Generated(hash = 1026260241)
    public JoinUserWithSheep(Long id, Long uId, Long sId) {
        this.id = id;
        this.uId = uId;
        this.sId = sId;
    }

    @Generated(hash = 297124019)
    public JoinUserWithSheep() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUId() {
        return this.uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public Long getSId() {
        return this.sId;
    }

    public void setSId(Long sId) {
        this.sId = sId;
    }
}