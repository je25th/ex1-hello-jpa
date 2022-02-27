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
//            member.setName("helloB");
//
//            em.persist(member);

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

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();//WAS내릴때 얘도 close하면 됨
    }
}
