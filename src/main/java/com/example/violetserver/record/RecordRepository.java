package com.example.violetserver.record;

import com.example.violetserver.record.entity.ViewTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class RecordRepository {
    @PersistenceContext
    private EntityManager em;

    public Long queryTopTs(int s) {
        return ((Date) em.createQuery("select p.TimeStamp from ViewTime p where p.ViewSeconds >= 24 order by p.Id desc")
                .setFirstResult(s)
                .setMaxResults(1)
                .getResultList()
                .get(0)).getTime() / 1000L;
    }

    public List<ViewTime> queryRecent(int limit, int offset, int count) {
        return em.createQuery("select p.Id, p.ArticleId, p.ViewSeconds from ViewTime p where p.ViewSeconds >= ?1 and p.Id >= ?2 order by p.Id desc", ViewTime.class)
                .setParameter("1", limit)
                .setParameter("2", offset)
                .setMaxResults(count)
                .getResultList();
    }

    public List<ViewTime> queryRecentU(int limit, int offset, int count) {
        return em.createQuery("select p.Id, p.ArticleId, p.ViewSeconds, p.UserAppId from ViewTime p where p.ViewSeconds >= ?1 and p.Id >= ?2 order by p.Id desc", ViewTime.class)
                .setParameter("1", limit)
                .setParameter("2", offset)
                .setMaxResults(count)
                .getResultList();
    }

    public List<ViewTime> queryUserRecent(int limit, int count, String user) {
        return em.createQuery("select p.Id, p.ArticleId, p.ViewSeconds from ViewTime p where p.ViewSeconds >= ?1 and p.UserAppId = ?2 order by p.Id desc", ViewTime.class)
                .setParameter("1", limit)
                .setParameter("2", user)
                .setMaxResults(count)
                .getResultList();
    }


}
