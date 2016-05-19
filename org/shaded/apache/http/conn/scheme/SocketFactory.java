package org.shaded.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.shaded.apache.http.conn.ConnectTimeoutException;
import org.shaded.apache.http.params.HttpParams;

public abstract interface SocketFactory
{
  public abstract Socket createSocket()
    throws IOException;
  
  public abstract Socket connectSocket(Socket paramSocket, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpParams paramHttpParams)
    throws IOException, UnknownHostException, ConnectTimeoutException;
  
  public abstract boolean isSecure(Socket paramSocket)
    throws IllegalArgumentException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/scheme/SocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */