package com.albert.springbatch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Albert
 */
@RestController
public class JobLauncherController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job parametersDemoJob;

    @GetMapping("/job/run")
    public String runJob(@RequestParam String msg) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("data", new Date())
                .addString("msg", msg)
                .toJobParameters();
        try {
            jobLauncher.run(parametersDemoJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}