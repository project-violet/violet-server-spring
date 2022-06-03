package com.example.violetserver.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/view")
    @ResponseBody
    public Double view(@RequestParam int articleId) {
        return recordService.view(articleId);
    }

}
