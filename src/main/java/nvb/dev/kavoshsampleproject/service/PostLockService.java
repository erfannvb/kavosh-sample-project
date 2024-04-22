package nvb.dev.kavoshsampleproject.service;

import java.util.concurrent.locks.ReentrantLock;

public interface PostLockService {

    ReentrantLock getLock();

}
