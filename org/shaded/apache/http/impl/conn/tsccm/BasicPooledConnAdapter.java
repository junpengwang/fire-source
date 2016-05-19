/*    */ package org.shaded.apache.http.impl.conn.tsccm;
/*    */ 
/*    */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*    */ import org.shaded.apache.http.impl.conn.AbstractPoolEntry;
/*    */ import org.shaded.apache.http.impl.conn.AbstractPooledConnAdapter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicPooledConnAdapter
/*    */   extends AbstractPooledConnAdapter
/*    */ {
/*    */   protected BasicPooledConnAdapter(ThreadSafeClientConnManager tsccm, AbstractPoolEntry entry)
/*    */   {
/* 53 */     super(tsccm, entry);
/* 54 */     markReusable();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected ClientConnectionManager getManager()
/*    */   {
/* 61 */     return super.getManager();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected AbstractPoolEntry getPoolEntry()
/*    */   {
/* 71 */     return this.poolEntry;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void detach()
/*    */   {
/* 79 */     super.detach();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/BasicPooledConnAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */