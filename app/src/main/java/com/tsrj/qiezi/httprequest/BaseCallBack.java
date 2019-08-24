package com.tsrj.qiezi.httprequest;

import java.io.IOException;

import retrofit2.Response;

public abstract class BaseCallBack<T> {

    /**
     * Called for [200, 300) responses.
     */
    public abstract void success(Response<T> response);

/*    *//**
     * Called for 401 responses.
     *//*
    public abstract void unauthenticated(Response<?> response);

    *//**
     * Called for [400, 500) responses, except 401.
     *//*
    public abstract void clientError(Response<?> response);*/

    /**
     * Called for [500, 600) response.
     */
    public abstract void serverError(Response<?> response);

    /**
     * Called for network errors while making the call.
     */
    public abstract void networkError(IOException e);
    /**
     * Called for unexpected errors while making the call.
     */
    public abstract void unexpectedError(Throwable t);
}
