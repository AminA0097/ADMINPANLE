package com.freq.arvand.arvand.CoreSelect;


import com.freq.arvand.arvand.Base.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "CORES_CORE_SELECT")
@TableGenerator(
        name = "CORE_CORE_SELECT_SEQ",
        table = "CORE_SEQ",
        pkColumnName = "TABLE_NAME",
        valueColumnName = "SEQ_COUNT",
        pkColumnValue = "CoreSelectEntitySeq",
        allocationSize = 1
)
public class CoreSelectEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CORE_CORE_SELECT_SEQ")
    @Column(name = "FLD_ID")
    private Long coreId;

    @Column(name = "FLD_NAME")
    private String coreName;

    @Column(name = "FLD_GROUP")
    private String coreGroup;

    @Column(name = "FLD_PARENT")
    private String coreParent;

    @Column(name = "FLD_VAL")
    private String coreVal;

    @Override
    public Long getId() {
        return coreId;
    }
}
