package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member2 {

    @Id
    private Long id;

    @Column(name = "name", updatable = false)//이 컬럼은 수정해도 update 안됨
    private String username;

    @Column(unique = true)//유니크 제약조건, 제약조건 이름이 랜덤으로 생성되기때문에 잘 사용하지 않음
    private String username2;

    @Column(nullable = false)//null 비허용 제약조건
    private Integer age;

    @Enumerated(EnumType.STRING)//EnumType.ORDINAL로 사용하면 안됨!(디폴트가 ORDINAL임)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private LocalDate lastModifieDate;//알아서 Date 타입으로 생성됨
    private LocalDateTime lastModifieDate2;//알아서 timestamp 타입으로 생성됨

    @Lob
    private String description;

    @Transient//컬럼과 매핑 안함
    private int temp;

    public Member2() {
    }
}
