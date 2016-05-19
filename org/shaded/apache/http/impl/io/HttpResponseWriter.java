/*    */ package org.shaded.apache.http.impl.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpMessage;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.io.SessionOutputBuffer;
/*    */ import org.shaded.apache.http.message.LineFormatter;
/*    */ import org.shaded.apache.http.params.HttpParams;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpResponseWriter
/*    */   extends AbstractMessageWriter
/*    */ {
/*    */   public HttpResponseWriter(SessionOutputBuffer buffer, LineFormatter formatter, HttpParams params)
/*    */   {
/* 53 */     super(buffer, formatter, params);
/*    */   }
/*    */   
/*    */   protected void writeHeadLine(HttpMessage message)
/*    */     throws IOException
/*    */   {
/* 59 */     this.lineFormatter.formatStatusLine(this.lineBuf, ((HttpResponse)message).getStatusLine());
/*    */     
/* 61 */     this.sessionBuffer.writeLine(this.lineBuf);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/HttpResponseWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */