package com.yenhsun.fscanner;

interface IScannerCallback {
    void done(in String[] result);
    
    void report(in String report);
}