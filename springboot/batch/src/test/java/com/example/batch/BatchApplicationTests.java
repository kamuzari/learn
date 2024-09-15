package com.example.batch;

import com.example.batch.domain.Person;
import com.example.batch.domain.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBatchTest
@SpringBootTest
class BatchApplicationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testJob() throws Exception {
        // 배치 작업 실행
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // 배치 작업 결과 검증
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        // 데이터베이스 검증
        List<Person> people = personRepository.findAll();
        Assertions.assertFalse(people.isEmpty());
    }
}
