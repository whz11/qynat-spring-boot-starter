package com.qingyou.qynat.commom.exception;

import java.util.function.Supplier;

/**
 * @author whz
 * @date 2021/7/15 20:53
 **/
public class QyNatException extends Exception implements Supplier<QyNatException> {
    public QyNatException(String message){
        super(message);
    }

    @Override
    public QyNatException get() {
        return this;
    }
}
