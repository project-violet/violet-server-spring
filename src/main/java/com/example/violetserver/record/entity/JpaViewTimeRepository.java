package com.example.violetserver.record.entity;

import com.example.violetserver.record.model.TopRecentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface JpaViewTimeRepository extends JpaRepository<ViewTime, ViewTimeRepository>, ViewTimeRepository  {
    @Query(value = "select count(*) as C, article_id as ArticleId from (select article_id from view_time where view_seconds >= 24 order by Id desc limit ?1) as a group by article_id order by C desc, article_id desc limit 1000", nativeQuery = true)
    ArrayList<TopRecentModel> findTopRecent(int s);
}
