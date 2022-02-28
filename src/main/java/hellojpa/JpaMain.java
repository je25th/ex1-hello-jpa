package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");//DB당 1개만 생성해서 애플리케이션 전체에서 공유

        EntityManager em = emf.createEntityManager();//고객이 요청할때마다 쓰고 버림, 쓰레드간 공유 절대 안됨

        EntityTransaction tx = em.getTransaction();//jpa의 모든 데이터 변경은 트랜젝션 안애서 일어나야한다!
        tx.begin();//트랜젝션 시작

        try {
            //등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("helloB");//여기까지는 비영속상태
//
//            em.persist(member);//여기부터 영속상태, 이때 DB에 저장되는것은 아님(1차 캐시(한 트랜젝션기간동안만 유지)에 저장됨)
//
//            em.detach(member);//detach하면 준영속상태가 됨

            //삭제
//            Member findMember = em.find(Member.class, 1L);
//            em.remove(findMember);

            //수정
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("testA");

            //전체 회원 조회
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)//페이징도 쉽게 적용할 수 있다
                    .setMaxResults(10)//페이징도 쉽게!
                    .getResultList();
            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }

            //영속 엔티티의 동일성 보장(같은 트랜젝션 내일때!)
            Member a = em.find(Member.class, 2L);
            Member b = em.find(Member.class, 2L);
            System.out.println("result = " +  (a == b));//true

            //쓰기 지연
            Member memberA = new Member(101L, "A");
            Member memberB = new Member(102L, "B");
            em.persist(memberA);//아직 쿼리 안나감, 쓰기 지연 SQL 저장소에 쿼리 등록해둠
            em.persist(memberB);//아직 쿼리 안나감, 쓰기 지연 SQL 저장소에 쿼리 등록해둠

            tx.commit();//플러시(flush) 발생! - 이때 한번에 쓰기 지연 SQL 저장소에 등록되어있는 쿼리가 나감!

            //flush
            Member member200 = new Member(200L, "200");
            em.persist(member200);
            em.flush();//쿼리 나감, 1차 캐시는 유지됨(영속성 컨텍스트를 비우지 않음)

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();//WAS내릴때 얘도 close하면 됨
    }
}
