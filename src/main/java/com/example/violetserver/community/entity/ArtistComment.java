package com.example.violetserver.community.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access  = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArtistComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date TimeStamp;

    @Column(length=500)
    private String Body;

    @Column(length=50)
    private String UserAppId;

    @Column(length = 100)
    private String ArtistName;

    private int Parent;
}
