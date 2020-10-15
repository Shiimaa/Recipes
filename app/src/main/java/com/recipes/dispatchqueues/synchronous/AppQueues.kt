package com.recipes.dispatchqueues.synchronous

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

private var networkHandler: Handler? = null
private var networkHandlerThread: HandlerThread? = null

fun postToNetworkQueue(runnable: Runnable, delay: Long = 0) {
    if (networkHandlerThread == null) {
        networkHandlerThread = HandlerThread("networkQueue")
        networkHandlerThread!!.start()
    }

    if (networkHandler == null) {
        networkHandler = Handler(networkHandlerThread!!.looper)
    }
    networkHandler?.postDelayed(runnable, delay)
}

private var handleNetworkResponseHandler: Handler? = null
private var handleNetworkResponseHandlerThread: HandlerThread? = null

fun postToHandleNetworkResponseQueue(runnable: Runnable, delay: Long = 0) {
    if (handleNetworkResponseHandlerThread == null) {
        handleNetworkResponseHandlerThread = HandlerThread("handleNetworkResponseQueue")
        handleNetworkResponseHandlerThread!!.start()
    }

    if (handleNetworkResponseHandler == null) {
        handleNetworkResponseHandler = Handler(handleNetworkResponseHandlerThread!!.looper)
    }
    handleNetworkResponseHandler?.postDelayed(runnable, delay)

}

private var dbHandler: Handler? = null
private var dbHandlerThread: HandlerThread? = null

fun postToHandleDbQueue(runnable: Runnable, delay: Long = 0) {
    if (dbHandlerThread == null) {
        dbHandlerThread = HandlerThread("dbQueue")
        dbHandlerThread!!.start()
    }

    if (dbHandler == null) {
        dbHandler = Handler(dbHandlerThread!!.looper)
    }
    dbHandler?.postDelayed(runnable, delay)
}

private var uiHandler: Handler? = null

fun postTOUiThread(runnable: Runnable, delay: Long = 0) {
    if (uiHandler == null)
        uiHandler = Handler(Looper.getMainLooper())

    uiHandler!!.postDelayed(runnable, delay)
}