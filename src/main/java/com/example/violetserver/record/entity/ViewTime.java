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
public class ViewTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long ArticleId;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date TimeStamp;

    private Long ViewSeconds;

    @Column(length=50)
    private String UserAppId;
}
