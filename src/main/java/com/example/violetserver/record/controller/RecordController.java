package com.example.violetserver.record.controller;

import com.example.violetserver.record.dto.RecordDto;
import com.example.violetserver.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello!";
    }

    @PostMapping("/view_close")
    @ResponseBody
    public RecordDto.ViewCloseResponse viewClose(@RequestBody RecordDto.ViewCloseRequest dto) {
        recordService.insertView(dto);
        return RecordDto.ViewCloseResponse.builder().msg("success").build();
    }

    @GetMapping("/view")
    @ResponseBody
    public Double view(@RequestParam int articleId) {
        return recordService.view(articleId);
    }

}
