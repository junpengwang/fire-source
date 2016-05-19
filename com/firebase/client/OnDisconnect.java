/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.Repo;
/*     */ import com.firebase.client.core.ValidationPath;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import com.firebase.client.snapshot.PriorityUtilities;
/*     */ import com.firebase.client.utilities.Validation;
/*     */ import com.firebase.client.utilities.encoding.JsonHelpers;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class OnDisconnect
/*     */ {
/*     */   private Repo repo;
/*     */   private Path path;
/*     */   
/*     */   OnDisconnect(Repo repo, Path path)
/*     */   {
/*  38 */     this.repo = repo;
/*  39 */     this.path = path;
/*     */   }
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
/*     */   public void setValue(Object value)
/*     */   {
/*  53 */     onDisconnectSetInternal(value, PriorityUtilities.NullPriority(), null);
/*     */   }
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
/*     */   public void setValue(Object value, String priority)
/*     */   {
/*  68 */     onDisconnectSetInternal(value, PriorityUtilities.parsePriority(priority), null);
/*     */   }
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
/*     */   public void setValue(Object value, double priority)
/*     */   {
/*  83 */     onDisconnectSetInternal(value, PriorityUtilities.parsePriority(Double.valueOf(priority)), null);
/*     */   }
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
/*     */   public void setValue(Object value, Firebase.CompletionListener listener)
/*     */   {
/*  98 */     onDisconnectSetInternal(value, PriorityUtilities.NullPriority(), listener);
/*     */   }
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
/*     */   public void setValue(Object value, String priority, Firebase.CompletionListener listener)
/*     */   {
/* 114 */     onDisconnectSetInternal(value, PriorityUtilities.parsePriority(priority), listener);
/*     */   }
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
/*     */   public void setValue(Object value, double priority, Firebase.CompletionListener listener)
/*     */   {
/* 130 */     onDisconnectSetInternal(value, PriorityUtilities.parsePriority(Double.valueOf(priority)), listener);
/*     */   }
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
/*     */   public void setValue(Object value, Map priority, Firebase.CompletionListener listener)
/*     */   {
/* 146 */     onDisconnectSetInternal(value, PriorityUtilities.parsePriority(priority), listener);
/*     */   }
/*     */   
/*     */   private void onDisconnectSetInternal(Object value, Node priority, final Firebase.CompletionListener onComplete) {
/* 150 */     Validation.validateWritablePath(this.path);
/* 151 */     ValidationPath.validateWithObject(this.path, value);
/*     */     try {
/* 153 */       Object bouncedValue = JsonHelpers.getMapper().convertValue(value, Object.class);
/* 154 */       Validation.validateWritableObject(bouncedValue);
/* 155 */       final Node node = NodeUtilities.NodeFromJSON(bouncedValue, priority);
/* 156 */       this.repo.scheduleNow(new Runnable()
/*     */       {
/*     */         public void run() {
/* 159 */           OnDisconnect.this.repo.onDisconnectSetValue(OnDisconnect.this.path, node, onComplete);
/*     */         }
/*     */       });
/*     */     } catch (IllegalArgumentException e) {
/* 163 */       throw new FirebaseException("Failed to parse to snapshot", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateChildren(Map<String, Object> children)
/*     */   {
/* 175 */     updateChildren(children, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateChildren(final Map<String, Object> children, final Firebase.CompletionListener listener)
/*     */   {
/* 185 */     ValidationPath.validateWithObject(this.path, children);
/* 186 */     final Map<ChildKey, Node> parsedUpdate = new HashMap();
/* 187 */     for (Map.Entry<String, Object> entry : children.entrySet()) {
/* 188 */       Validation.validateWritableObject(entry.getValue());
/* 189 */       parsedUpdate.put(ChildKey.fromString((String)entry.getKey()), NodeUtilities.NodeFromJSON(entry.getValue()));
/*     */     }
/* 191 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 194 */         OnDisconnect.this.repo.onDisconnectUpdate(OnDisconnect.this.path, parsedUpdate, listener, children);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeValue()
/*     */   {
/* 205 */     setValue(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeValue(Firebase.CompletionListener listener)
/*     */   {
/* 213 */     setValue(null, listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cancel()
/*     */   {
/* 222 */     cancel(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cancel(final Firebase.CompletionListener listener)
/*     */   {
/* 230 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 233 */         OnDisconnect.this.repo.onDisconnectCancel(OnDisconnect.this.path, listener);
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/OnDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */