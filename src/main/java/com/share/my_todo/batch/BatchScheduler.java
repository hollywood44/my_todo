package com.share.my_todo.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@EnableScheduling
@RequiredArgsConstructor
@Component
public class BatchScheduler {

    private final TomorrowBatchConfig tomorrowBatchConfig;
    private final FollowerDeleteBatchConfig followerDeleteBatchConfig;
    private final ProceedingToFailedBatchConfig proceedingToFailedBatchConfig;
    private final JobLauncher jobLauncher;

    // 초 분 시 날짜 월 요일
    @Scheduled(cron = "0 55 1 * * ?")
    public void tomorrowNotificationSchedule() {
        try {
            Map <String, JobParameter> jobParametersMap = new HashMap <> ();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            JobExecution jobExecution = jobLauncher.run(tomorrowBatchConfig.notificationJob(), parameters);

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 55 1 * * ?")
    public void proceedingToFailedSchedule() {
        try {
            Map <String, JobParameter> jobParametersMap = new HashMap <> ();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            JobExecution jobExecution = jobLauncher.run(proceedingToFailedBatchConfig.PtoFJob(), parameters);

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 55 1 * * ?")
    public void followerDeleteSchedule() {
        try {
            Map <String, JobParameter> jobParametersMap = new HashMap <> ();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            JobExecution jobExecution = jobLauncher.run(followerDeleteBatchConfig.followerDeleteJob(), parameters);


        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
