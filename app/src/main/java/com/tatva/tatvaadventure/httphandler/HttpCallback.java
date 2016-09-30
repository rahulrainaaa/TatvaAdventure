package com.tatva.tatvaadventure.httphandler;

/**
 * HTTPCallback interface
 */
public interface HttpCallback {


    //200 server response
    public void onHttpSuccess(int statusCode, String statusMessage, String data, int tag);

    //non 200 server response
    public void onHttpFail(int statusCode, String statusMessage, int tag);

    //connection-error, exception
    public void onHttpError(int tag);


}
