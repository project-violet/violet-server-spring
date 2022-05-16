package com.example.violetserver.record.dto;

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
    }

    @Data
    @Builder
    public static class ViewCloseResponse {
        private String msg;
    }
}
