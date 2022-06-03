package com.example.violetserver.record;

import com.example.violetserver.record.model.TopRecentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/view_close")
    @ResponseBody
    public RecordDto.ViewCloseResponse viewClose(@RequestBody RecordDto.ViewCloseRequest dto) {
        recordService.insertView(dto);
        return RecordDto.ViewCloseResponse.builder().msg("success").build();
    }

    @PostMapping("/view_report")
    @ResponseBody
    public RecordDto.ViewCloseResponse viewReport(@RequestBody RecordDto.ViewReportRequest dto) {
        recordService.insertViewReport(dto);
        return RecordDto.ViewCloseResponse.builder().msg("success").build();
    }


    @GetMapping("/view")
    @ResponseBody
    public Double view(@RequestParam int articleId) {
        return recordService.view(articleId);
    }


    @GetMapping("/top")
    @ResponseBody
    public RecordDto.TopResponse top(RecordDto.TopRequest dto) {
        List<List<Long>> result = recordService.top(dto);
        return RecordDto.TopResponse.builder().msg("success").result(result).build();
    }

    @GetMapping("/top_ts")
    @ResponseBody
    public RecordDto.TopTsResponse topTs(@RequestParam(name = "s", required = true) int s) {
        Long timeStamp = recordService.topTs(s);
        return RecordDto.TopTsResponse.builder().msg("success").result(timeStamp).build();
    }

    @GetMapping("/top_recent")
    @ResponseBody
    public RecordDto.TopRecentResponse topRecent(@RequestParam(name = "s", required = true) int s) {
        List<List<Long>> result = recordService.topRecent(s);
        return RecordDto.TopRecentResponse.builder().msg("success").result(result).build();
    }


}
