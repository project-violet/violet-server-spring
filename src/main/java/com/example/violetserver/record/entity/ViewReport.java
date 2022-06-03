package com.example.violetserver.record.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access  = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ViewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long ArticleId;

    private Long Pages;

    private Long LastPage;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date TimeStamp;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date StartsTime;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date EndsTime;

    private Long ValidSeconds;

    @Lob
    private String MsPerPages;

    @Column(length=50)
    private String UserAppId;
}
