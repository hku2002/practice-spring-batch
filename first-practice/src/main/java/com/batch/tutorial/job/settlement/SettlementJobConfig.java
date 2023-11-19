package com.batch.tutorial.job.settlement;

import com.batch.tutorial.domain.order.Order;
import com.batch.tutorial.domain.order.OrderRepository;
import com.batch.tutorial.domain.settlement.Settlement;
import com.batch.tutorial.domain.settlement.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;

/**
 * 주문 데이터를 정산으로 마이그레이션 하는 배치
 * params: --spring.batch.job.name=orderToSettlementJob
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SettlementJobConfig {

    private final OrderRepository orderRepository;
    private final SettlementRepository settlementRepository;

    @Bean
    public Job orderToSettlementJob(JobRepository jobRepository, Step orderToSettlementJobStep) {
        return new JobBuilder("orderToSettlementJob", jobRepository)
                .start(orderToSettlementJobStep)
                .build();
    }

    @Bean
    @JobScope
    public Step orderToSettlementJobStep(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , ItemReader<Order> itemReader
            , ItemProcessor<Order, Settlement> itemProcessor
            , ItemWriter<Settlement> itemWriter) {
        return new StepBuilder("orderToSettlementStep", jobRepository)
                .<Order, Settlement>chunk(3, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Order> readOrders() {
        return new RepositoryItemReaderBuilder<Order>()
                .name("orderReader")
                .repository(orderRepository)
                .methodName("findAll")
                .pageSize(3)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Order, Settlement> orderToSettlementProcessor() {
        return Settlement::from;
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Settlement> settlementWriter() {
        return new RepositoryItemWriterBuilder<Settlement>()
                .repository(settlementRepository)
                .methodName("save")
                .build();

    }

}
