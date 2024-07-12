package com.example.ncc_spring.service.scheduling;

import com.example.ncc_spring.model.entity.Scheduler;
import com.example.ncc_spring.model.entity.Task;
import com.example.ncc_spring.repository.scheduler.SchedulerRepository;
import com.example.ncc_spring.repository.scheduler.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class DynamicSchedulerService {

    @Autowired
    private TaskScheduler taskScheduler;
    private TaskRepository taskRepository;
    private SchedulingService schedulingService;

    private SchedulerRepository schedulerRepository;
    private Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    @PostConstruct
    public void  initializeJobsFromDb() {
        try {
            List<Task> tasks = taskRepository.findAll();
            for(Task task : tasks) {
                System.out.println(task.getJobId());
                Optional<Scheduler> scheduler = schedulerRepository.findFirstSchedulerByTaskId(task.getId());
                System.out.println(scheduler.get().getCron());
                scheduler.ifPresent(value -> initScheduleTask(task.getJobId(), value.getCron(), getTaskByJobId(task.getJobId())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initScheduleTask(String jobId, String cronExpression, Runnable task) {
        if (jobsMap.containsKey(jobId)) {
            jobsMap.get(jobId).cancel(false);
        }
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, new CronTrigger(cronExpression));
        jobsMap.put(jobId, scheduledFuture);
    }

    @Transactional
    public void scheduleTask(String jobId, String cronExpression, Runnable task) {
        if (jobsMap.containsKey(jobId)) {
            jobsMap.get(jobId).cancel(false);
        }
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, new CronTrigger(cronExpression));
        jobsMap.put(jobId, scheduledFuture);
        Task taskSave  = Task.builder()
                .jobId(jobId)
                .build();
        if(!jobsMap.containsKey(jobId)) {
            Task taskRes= taskRepository.save(taskSave);

            Scheduler scheduler = Scheduler.builder()
                    .cron(cronExpression)
                    .task(taskRes)
                    .build();
            schedulerRepository.save(scheduler);
        } else {
            Scheduler scheduler = Scheduler.builder()
                    .cron(cronExpression)
                    .task(taskSave)
                    .build();
            schedulerRepository.save(scheduler);
        }
    }

    public void stopTask(String jobId) {
        if (jobsMap.containsKey(jobId)) {
            jobsMap.get(jobId).cancel(false);
            jobsMap.remove(jobId);
        }
    }

    private Runnable getTaskByJobId(String jobId) {
        if ("reportTimeTask".equals(jobId)) {
            return schedulingService::reportTime;
        } else if ("commitFaultTask".equals(jobId)) {
            return schedulingService::commitFault;
        } else {
            throw new IllegalArgumentException("Unknown jobId: " + jobId);
        }
    }
}
