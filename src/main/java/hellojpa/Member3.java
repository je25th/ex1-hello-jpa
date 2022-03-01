package hellojpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Member3 {

    @Id//직접 할당 : @Id만 사용
    @GeneratedValue(strategy = GenerationType.AUTO)//AUTO가 기본값, Db방언에 맞춰서 알아서 전략을 맞춰줌
    private Long id;//Integer는 10억정도까지 저장가능함
    //권장하는 식별자 전략
    //Long형 + 대체키(시퀀스, UUID 등등) + 키 생성전략 사용
    // -> autoincrement나 시퀀스를 쓰자

    //@GeneratedValue(strategy = GenerationType.IDENTITY) : persist할때 바로 insert 쿼리 날아감

    private String name;
}
