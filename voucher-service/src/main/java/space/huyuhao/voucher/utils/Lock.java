package space.huyuhao.voucher.utils;

/**
 * @author hyh
 * @since 2025-10-31
 */
public interface Lock {
    /**
     * 尝试获取锁
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unLock();
}
