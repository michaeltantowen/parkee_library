package com.parkee.library.repository;

import com.parkee.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdCardNumber(Long idCardNumber);

}
