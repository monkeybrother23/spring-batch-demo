package com.albert.springbatch.tasklet;

import com.albert.springbatch.util.BatchDao;
import com.albert.springbatch.util.BatchDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Albert
 */
public class DemoBatchTasklet implements Tasklet, StepExecutionListener {
    private Logger logger = LoggerFactory.getLogger(DemoBatchTasklet.class);
    private JdbcTemplate jdbcTemplate;
    private BatchDao batchDao;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger = LoggerFactory.getLogger(DemoBatchTasklet.class);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getFailureExceptions().isEmpty()) {
            return ExitStatus.COMPLETED;
        } else {
            return ExitStatus.FAILED;
        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        return null;
    }

    public BatchDao getBatchDao() {
        if (batchDao == null) {
            batchDao = new BatchDaoImpl(jdbcTemplate);
        }
        return batchDao;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
