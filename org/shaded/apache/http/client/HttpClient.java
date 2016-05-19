package org.shaded.apache.http.client;

import java.io.IOException;
import org.shaded.apache.http.HttpHost;
import org.shaded.apache.http.HttpRequest;
import org.shaded.apache.http.HttpResponse;
import org.shaded.apache.http.client.methods.HttpUriRequest;
import org.shaded.apache.http.conn.ClientConnectionManager;
import org.shaded.apache.http.params.HttpParams;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface HttpClient
{
  public abstract HttpParams getParams();
  
  public abstract ClientConnectionManager getConnectionManager();
  
  public abstract HttpResponse execute(HttpUriRequest paramHttpUriRequest)
    throws IOException, ClientProtocolException;
  
  public abstract HttpResponse execute(HttpUriRequest paramHttpUriRequest, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException;
  
  public abstract HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest)
    throws IOException, ClientProtocolException;
  
  public abstract HttpResponse execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException;
  
  public abstract <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException;
  
  public abstract <T> T execute(HttpUriRequest paramHttpUriRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException;
  
  public abstract <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler)
    throws IOException, ClientProtocolException;
  
  public abstract <T> T execute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, ResponseHandler<? extends T> paramResponseHandler, HttpContext paramHttpContext)
    throws IOException, ClientProtocolException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/HttpClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */