package com.share.my_todo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JpaItemWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
public class JpaItemWriterCustom<T> extends JpaItemWriter<T> {

    private EntityManagerFactory entityManagerFactory;
    private boolean usePersist = false;

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {

        if (!items.isEmpty()) {
            long addedToContextCount = 0;
            for (T item : items) {
                if (!entityManager.contains(item)) {
                    if(usePersist) {
                        entityManager.persist(item);
                    }
                    else {
                        entityManager.remove(entityManager.contains(item) ? item : entityManager.merge(item));
                    }
                    addedToContextCount++;
                }
            }
            log.debug(addedToContextCount + " entities " + (usePersist ? " persisted." : "merged."));
            log.debug((items.size() - addedToContextCount) + " entities found in persistence context.");
        }
    }
}
