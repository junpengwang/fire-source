/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.FirebaseException;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class ValidationPath
/*     */ {
/*  20 */   private final List<String> parts = new ArrayList();
/*  21 */   private int byteLength = 0;
/*     */   public static final int MAX_PATH_LENGTH_BYTES = 768;
/*     */   public static final int MAX_PATH_DEPTH = 32;
/*     */   
/*     */   private ValidationPath(Path path) throws FirebaseException
/*     */   {
/*  27 */     for (ChildKey key : path) {
/*  28 */       this.parts.add(key.asString());
/*     */     }
/*     */     
/*     */ 
/*  32 */     this.byteLength = Math.max(1, this.parts.size());
/*  33 */     for (int i = 0; i < this.parts.size(); i++) {
/*  34 */       this.byteLength += utf8Bytes((CharSequence)this.parts.get(i));
/*     */     }
/*  36 */     checkValid();
/*     */   }
/*     */   
/*     */   public static void validateWithObject(Path path, Object value) throws FirebaseException {
/*  40 */     new ValidationPath(path).withObject(value);
/*     */   }
/*     */   
/*     */   private void withObject(Object value) throws FirebaseException {
/*  44 */     if ((value instanceof Map)) {
/*  45 */       Map<String, Object> mapValue = (Map)value;
/*  46 */       for (String key : mapValue.keySet())
/*  47 */         if (!key.startsWith("."))
/*     */         {
/*     */ 
/*  50 */           push(key);
/*  51 */           withObject(mapValue.get(key));
/*  52 */           pop();
/*     */         }
/*  54 */       return;
/*     */     }
/*     */     
/*  57 */     if ((value instanceof List)) {
/*  58 */       List listValue = (List)value;
/*  59 */       for (int i = 0; i < listValue.size(); i++) {
/*  60 */         String key = Integer.toString(i);
/*  61 */         push(key);
/*  62 */         withObject(listValue.get(i));
/*  63 */         pop();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void push(String child) throws FirebaseException
/*     */   {
/*  70 */     if (this.parts.size() > 0) {
/*  71 */       this.byteLength += 1;
/*     */     }
/*  73 */     this.parts.add(child);
/*  74 */     this.byteLength += utf8Bytes(child);
/*  75 */     checkValid();
/*     */   }
/*     */   
/*     */   private String pop() {
/*  79 */     String last = (String)this.parts.remove(this.parts.size() - 1);
/*  80 */     this.byteLength -= utf8Bytes(last);
/*     */     
/*  82 */     if (this.parts.size() > 0) {
/*  83 */       this.byteLength -= 1;
/*     */     }
/*  85 */     return last;
/*     */   }
/*     */   
/*     */   private void checkValid() throws FirebaseException {
/*  89 */     if (this.byteLength > 768) {
/*  90 */       throw new FirebaseException("Data has a key path longer than 768 bytes (" + this.byteLength + ").");
/*     */     }
/*     */     
/*  93 */     if (this.parts.size() > 32) {
/*  94 */       throw new FirebaseException("Path specified exceeds the maximum depth that can be written (32) or object contains a cycle " + toErrorString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private String toErrorString()
/*     */   {
/* 101 */     if (this.parts.size() == 0) {
/* 102 */       return "";
/*     */     }
/* 104 */     return "in path '" + joinStringList("/", this.parts) + "'";
/*     */   }
/*     */   
/*     */   private static String joinStringList(String delimeter, List<String> parts) {
/* 108 */     StringBuilder sb = new StringBuilder();
/* 109 */     for (int i = 0; i < parts.size(); i++) {
/* 110 */       if (i > 0) {
/* 111 */         sb.append(delimeter);
/*     */       }
/* 113 */       sb.append((String)parts.get(i));
/*     */     }
/* 115 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int utf8Bytes(CharSequence sequence)
/*     */   {
/* 123 */     int count = 0;
/* 124 */     int i = 0; for (int len = sequence.length(); i < len; i++) {
/* 125 */       char ch = sequence.charAt(i);
/* 126 */       if (ch <= '') {
/* 127 */         count++;
/* 128 */       } else if (ch <= '߿') {
/* 129 */         count += 2;
/* 130 */       } else if (Character.isHighSurrogate(ch)) {
/* 131 */         count += 4;
/* 132 */         i++;
/*     */       } else {
/* 134 */         count += 3;
/*     */       }
/*     */     }
/* 137 */     return count;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/ValidationPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */