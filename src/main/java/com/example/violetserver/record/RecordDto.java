package com.example.violetserver.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
