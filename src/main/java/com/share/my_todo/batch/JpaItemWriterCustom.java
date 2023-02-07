package com.share.my_todo.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.JpaItemWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaItemWriterCustom<T> extends JpaItemWriter<T> {

    protected static final Log logger = LogFactory.getLog(JpaItemWriter.class);

    private EntityManagerFactory entityManagerFactory;
    private boolean usePersist = false;

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {

        if (logger.isDebugEnabled()) {
            logger.debug("Writing to JPA with " + items.size() + " items.");
        }

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
            if (logger.isDebugEnabled()) {
                logger.debug(addedToContextCount + " entities " + (usePersist ? " persisted." : "merged."));
                logger.debug((items.size() - addedToContextCount) + " entities found in persistence context.");
            }
        }

    }
}
