package cn.edu.sdust.cise.itransfer.exception;

/**
 * Created by 宇强 on 2016/6/24 0024.
 */
public class FileExpiredException extends RuntimeException {
    public FileExpiredException(String message) {
        super(message);
    }

    public FileExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
