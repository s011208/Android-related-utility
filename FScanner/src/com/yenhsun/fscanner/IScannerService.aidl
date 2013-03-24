package com.yenhsun.fscanner;

import  com.yenhsun.fscanner.IScannerCallback;

interface IScannerService {
    boolean isTaskRunning();
       
    void stopRunningTask();   
   
    void registerCallback(IScannerCallback cb);   
  
    void unregisterCallback(IScannerCallback cb);
    
    void requestScanService(String target);
}