package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.service.impl.PostLockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PostLockServiceTest {

    @InjectMocks
    PostLockServiceImpl postLockService;

    @Test
    void testLockAcquisition() {
        postLockService.getLock().lock();
        assertTrue(postLockService.getLock().isLocked());
    }

    @Test
    void testLockRelease() {
        postLockService.getLock().lock();
        postLockService.getLock().unlock();
        assertFalse(postLockService.getLock().isLocked());
    }

    @Test
    void testConcurrentLocking() throws InterruptedException {
        final int THREAD_COUNT = 10;
        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                postLockService.getLock().lock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    postLockService.getLock().unlock();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertFalse(postLockService.getLock().isLocked());
    }

}