/*     */ package com.firebase.client.utilities;
/*     */ 
/*     */ import com.firebase.client.FirebaseException;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Validation
/*     */ {
/*  22 */   private static final Pattern INVALID_PATH_REGEX = Pattern.compile("[\\[\\]\\.#$]");
/*  23 */   private static final Pattern INVALID_KEY_REGEX = Pattern.compile("[\\[\\]\\.#\\$\\/\\u0000-\\u001F\\u007F]");
/*     */   
/*     */   private static boolean isValidPathString(String pathString) {
/*  26 */     return !INVALID_PATH_REGEX.matcher(pathString).find();
/*     */   }
/*     */   
/*     */   public static void validatePathString(String pathString) throws FirebaseException {
/*  30 */     if (!isValidPathString(pathString)) {
/*  31 */       throw new FirebaseException("Invalid Firebase path: " + pathString + ". Firebase paths must not contain '.', '#', '$', '[', or ']'");
/*     */     }
/*     */   }
/*     */   
/*     */   public static void validateRootPathString(String pathString) throws FirebaseException
/*     */   {
/*  37 */     if (pathString.startsWith(".info")) {
/*  38 */       validatePathString(pathString.substring(5));
/*  39 */     } else if (pathString.startsWith("/.info")) {
/*  40 */       validatePathString(pathString.substring(6));
/*     */     } else {
/*  42 */       validatePathString(pathString);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isWritableKey(String key) {
/*  47 */     return (key != null) && (key.length() > 0) && ((key.equals(".value")) || (key.equals(".priority")) || ((!key.startsWith(".")) && (!INVALID_KEY_REGEX.matcher(key).find())));
/*     */   }
/*     */   
/*     */ 
/*     */   private static boolean isValidKey(String key)
/*     */   {
/*  53 */     return (key.equals(".info")) || (!INVALID_KEY_REGEX.matcher(key).find());
/*     */   }
/*     */   
/*     */   public static void validateNullableKey(String key) throws FirebaseException {
/*  57 */     if ((key != null) && (!isValidKey(key))) {
/*  58 */       throw new FirebaseException("Invalid key: " + key + ". Keys must not contain '/', '.', '#', '$', '[', or ']'");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean isWritablePath(Path path)
/*     */   {
/*  66 */     ChildKey front = path.getFront();
/*  67 */     return (front == null) || (!front.asString().startsWith("."));
/*     */   }
/*     */   
/*     */   public static void validateWritableObject(Object object)
/*     */   {
/*  72 */     if ((object instanceof Map)) {
/*  73 */       Map<String, Object> map = (Map)object;
/*  74 */       if (map.containsKey(".sv"))
/*     */       {
/*  76 */         return;
/*     */       }
/*  78 */       for (Map.Entry<String, Object> entry : map.entrySet()) {
/*  79 */         validateWritableKey((String)entry.getKey());
/*  80 */         validateWritableObject(entry.getValue());
/*     */       }
/*  82 */     } else if ((object instanceof List)) {
/*  83 */       List<Object> list = (List)object;
/*  84 */       for (Object child : list) {
/*  85 */         validateWritableObject(child);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void validateWritableKey(String key)
/*     */     throws FirebaseException
/*     */   {
/*  93 */     if (!isWritableKey(key)) {
/*  94 */       throw new FirebaseException("Invalid key: " + key + ". Keys must not contain '/', '.', '#', '$', '[', or ']'");
/*     */     }
/*     */   }
/*     */   
/*     */   public static void validateWritablePath(Path path) throws FirebaseException
/*     */   {
/* 100 */     if (!isWritablePath(path)) {
/* 101 */       throw new FirebaseException("Invalid write location: " + path.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/Validation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */