package com.example.batch;

import com.example.batch.domain.Spot;
import com.example.batch.domain.SpotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBatchTest
@SpringBootTest
class BatchApplicationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private Job job;

    @Test
    public void testJob() throws Exception {
        // 배치 작업 실행

        for (int pageNo = 1; pageNo <= 20; pageNo++) {  // 페이지 수만큼 반복
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addLong("pageNo", (long) pageNo)
                    .toJobParameters();

            JobExecution run = jobLauncherTestUtils.getJobLauncher().run(job, jobParameters);// Job 실행
            Long jobId = run.getJobId();
        }

        List<Spot> spots = spotRepository.findAll();
        Assertions.assertFalse(spots.isEmpty());
        org.assertj.core.api.Assertions.assertThat(spots.size()).isEqualTo(4000);
    }
}
