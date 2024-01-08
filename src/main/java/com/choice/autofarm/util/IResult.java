package com.choice.autofarm.util;

public interface IResult<T> {

    void onSuccess(T data);
    void onFailure(Throwable throwable);

    void onError(String error);

}
