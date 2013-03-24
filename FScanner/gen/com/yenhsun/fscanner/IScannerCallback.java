/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\s011208\\Desktop\\Android\\Android-related-utility\\FScanner\\src\\com\\yenhsun\\fscanner\\IScannerCallback.aidl
 */
package com.yenhsun.fscanner;
public interface IScannerCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.yenhsun.fscanner.IScannerCallback
{
private static final java.lang.String DESCRIPTOR = "com.yenhsun.fscanner.IScannerCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.yenhsun.fscanner.IScannerCallback interface,
 * generating a proxy if needed.
 */
public static com.yenhsun.fscanner.IScannerCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.yenhsun.fscanner.IScannerCallback))) {
return ((com.yenhsun.fscanner.IScannerCallback)iin);
}
return new com.yenhsun.fscanner.IScannerCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_done:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
this.done(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_report:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.report(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.yenhsun.fscanner.IScannerCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void done(java.lang.String[] result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(result);
mRemote.transact(Stub.TRANSACTION_done, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void report(java.lang.String report) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(report);
mRemote.transact(Stub.TRANSACTION_report, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_done = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_report = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void done(java.lang.String[] result) throws android.os.RemoteException;
public void report(java.lang.String report) throws android.os.RemoteException;
}
