package nvb.dev.kavoshsampleproject.service.impl;

import nvb.dev.kavoshsampleproject.service.PostLockService;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class PostLockServiceImpl implements PostLockService {

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public ReentrantLock getLock() {
        return lock;
    }
}
