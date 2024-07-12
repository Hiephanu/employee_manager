package com.example.ncc_spring.controller.scheduler;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.service.scheduling.DynamicSchedulerService;
import com.example.ncc_spring.service.scheduling.SchedulingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
@AllArgsConstructor
public class SchedulerController {
    private DynamicSchedulerService dynamicSchedulerService;
    private SchedulingService schedulingService;

    @PostMapping("/schedule/reportTime")
    public ResponseEntity<?> scheduleTaskReport(@RequestParam String cronExpression) {
        dynamicSchedulerService.scheduleTask("reportTimeTask",cronExpression, schedulingService::reportTime);
        return SuccessResponseHelper.createSuccessResponse("Task cron report time at : " +cronExpression);
    }

    @PostMapping("/schedule/commitFault")
    public ResponseEntity<?> scheduleTaskCommitFault(@RequestParam String cronExpression) {
        dynamicSchedulerService.scheduleTask("commitFaultTask",cronExpression, schedulingService::commitFault);
        return SuccessResponseHelper.createSuccessResponse("Task cron commit fault at : " +cronExpression);
    }

    @PostMapping("/stop/{id}")
    public ResponseEntity<?> stopTask(@PathVariable String  id) {
        dynamicSchedulerService.stopTask(id);
        return  SuccessResponseHelper.createSuccessResponse("Stop task");
    }
}
