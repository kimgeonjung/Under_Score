package edu.du.pproject11.repository;

import edu.du.pproject11.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    @Query("select m.loginId from Member m where m.name = :name and m.email = :email")
    Optional<String> findLoginIdByNameAndEmail(String name, String email);
}
