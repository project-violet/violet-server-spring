package com.example.violetserver.record;

import com.example.violetserver.record.model.TopRecentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class RecordDto {

    @Data
    @AllArgsConstructor
    @Builder
    public static class ViewCloseRequest {
        private int no;
        private int time;
        private String user;
    }

    @Data
    @Builder
    public static class ViewCloseResponse {
        private String msg;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ViewReportRequest {
        private String user;
        private int id;
        private long startsTime;
        private long endsTime;
        private int pages;
        private int lastPage;
        private int validSeconds;
        private String msPerPages;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class TopRequest {
        private int offset;
        private int count;
        private String type;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class TopResponse {
        private String msg;
        private List<List<Long>> result;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class TopTsResponse {
        private String msg;
        private Long result;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class TopRecentResponse {
        private String msg;
        private List<List<Long>> result;
    }

}
