package com.freq.arvand.arvand.User;

import com.freq.arvand.arvand.Base.BaseEntity;
import com.freq.arvand.arvand.CoreSelect.CoreSelectEntity;
import com.freq.arvand.arvand.annotation.BooleanToNumberConverter;
import jakarta.persistence.*;


@Entity
@Table(name = "CORES_USERS")
@TableGenerator(
        name = "CORE_USER_SEQ",
        table = "CORE_SEQ",
        pkColumnName = "TABLE_NAME",
        valueColumnName = "SEQ_COUNT",
        pkColumnValue = "UsersEntitySeq",
        allocationSize = 1
)
public class UsersEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CORE_USER_SEQ")
    @Column(name = "FLD_USFER_ID")
    private Long userId;

    @Column(name = "FLD_USER_NAME")
    private String userName;

    @Column(name = "FLD_USER_PASSWORD")
    private String password;

    @Column(name = "FLD_IS_SYS_ADMIN")
    @Convert(converter = BooleanToNumberConverter.class)
    private boolean isSysAdmin;

    @Column(name = "FLD_USER_ACCESS")
    private String userAccess;

    @ManyToOne
    @JoinColumn(name = "FLD_STATUS")
    private CoreSelectEntity status;

    public CoreSelectEntity getStatus() {
        return status;
    }
    public void setStatus(CoreSelectEntity status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSysAdmin() {
        return isSysAdmin;
    }

    public void setSysAdmin(boolean sysAdmin) {
        isSysAdmin = sysAdmin;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }
    @Override
    public Long getId() {
        return this.userId;
    }
}
