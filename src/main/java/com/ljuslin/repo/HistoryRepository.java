package com.ljuslin.repo;

import com.ljuslin.entity.History;
import com.ljuslin.entity.Member;

import java.util.List;

public interface HistoryRepository {
    List<History> getHistory(Member member);
    void save(History history);
}
