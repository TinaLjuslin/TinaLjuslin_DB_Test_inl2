package com.ljuslin.repo;

import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> getAll();
    Optional getById(Long id);
    void save(Member member);
    Member change(Member member);
    //delete får skötas i servicelagret, sätt active till false och köp update
    Optional<Member> findByEmail(String email);
    List<Member> search(String searchText);
}
