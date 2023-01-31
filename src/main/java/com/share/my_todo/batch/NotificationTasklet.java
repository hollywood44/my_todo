//package com.share.my_todo.batch;
//
//import com.share.my_todo.repository.NoticeRepository;
//import com.share.my_todo.repository.TodoRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//@StepScope // Step 시점에 Bean이 생성
//@Slf4j
//public class NotificationTasklet implements Tasklet {
//
//    private final NoticeRepository noticeRepository;
//    private final TodoRepository todoRepository;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        log.info(contribution.toString());
//        log.info(chunkContext.toString());
//
//        return null;
//    }
//}
