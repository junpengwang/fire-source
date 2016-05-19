/*     */ package org.shaded.apache.commons.logging.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServletContextCleaner
/*     */   implements ServletContextListener
/*     */ {
/*  54 */   private Class[] RELEASE_SIGNATURE = { ClassLoader.class };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void contextDestroyed(ServletContextEvent sce)
/*     */   {
/*  62 */     ClassLoader tccl = Thread.currentThread().getContextClassLoader();
/*     */     
/*  64 */     Object[] params = new Object[1];
/*  65 */     params[0] = tccl;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */     ClassLoader loader = tccl;
/*  98 */     while (loader != null)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 103 */         Class logFactoryClass = loader.loadClass("org.shaded.apache.commons.logging.LogFactory");
/* 104 */         Method releaseMethod = logFactoryClass.getMethod("release", this.RELEASE_SIGNATURE);
/* 105 */         releaseMethod.invoke(null, params);
/* 106 */         loader = logFactoryClass.getClassLoader().getParent();
/*     */       }
/*     */       catch (ClassNotFoundException ex)
/*     */       {
/* 110 */         loader = null;
/*     */       }
/*     */       catch (NoSuchMethodException ex) {
/* 113 */         System.err.println("LogFactory instance found which does not support release method!");
/* 114 */         loader = null;
/*     */       }
/*     */       catch (IllegalAccessException ex) {
/* 117 */         System.err.println("LogFactory instance found which is not accessable!");
/* 118 */         loader = null;
/*     */       }
/*     */       catch (InvocationTargetException ex) {
/* 121 */         System.err.println("LogFactory instance release method failed!");
/* 122 */         loader = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 129 */     LogFactory.release(tccl);
/*     */   }
/*     */   
/*     */   public void contextInitialized(ServletContextEvent sce) {}
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/commons/logging/impl/ServletContextCleaner.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */