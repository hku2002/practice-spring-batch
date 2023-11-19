package com.batch.tutorial.job.filereadwrite;

import com.batch.tutorial.job.filereadwrite.dto.Player;
import com.batch.tutorial.job.filereadwrite.dto.PlayerYears;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 파일 정보를 가져와서 읽고 쓰는 배치(공식 문서 샘플 데이터 참고)
 * params: --spring.batch.job.name=fileReadWriteJob
 */
@Slf4j
@Configuration
public class FileReadWriteConfig {

    @Bean
    public Job fileReadWriteJob(JobRepository jobRepository, Step fileReadWriteStep) {
        return new JobBuilder("fileReadWriteJob", jobRepository)
                .start(fileReadWriteStep)
                .build();
    }

    @Bean
    @JobScope
    public Step fileReadWriteStep(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , FlatFileItemReader<Player> playerFileItemReader
            , ItemProcessor<Player, PlayerYears> itemProcessor
            , FlatFileItemWriter<PlayerYears> playerYearsFileItemWriter) {
        return new StepBuilder("fileReadWriteStep", jobRepository)
                .<Player, PlayerYears>chunk(3, transactionManager)
                .reader(playerFileItemReader)
                .processor(itemProcessor)
                .writer(playerYearsFileItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Player> playerFileItemReader() {
        return new FlatFileItemReaderBuilder<Player>()
                .name("playerItemReader")
                .resource(new ClassPathResource("player/Player.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .fieldSetMapper(new Player.PlayerFieldSetMapper())
                .linesToSkip(1)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Player, PlayerYears> playerItemProcessor() {
        return PlayerYears::from;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<PlayerYears> playerYearsFileItemWriter() {
        BeanWrapperFieldExtractor<PlayerYears> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"ID", "lastName", "position", "yearsExperience"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<PlayerYears> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FileSystemResource outputResource = new FileSystemResource("player-output.txt");

        return new FlatFileItemWriterBuilder<PlayerYears>()
                .name("playerItemWriter")
                .resource(outputResource)
                .lineAggregator(lineAggregator)
                .build();
    }

}
