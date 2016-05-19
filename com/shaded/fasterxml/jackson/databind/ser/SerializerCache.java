/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
/*     */ import java.util.HashMap;
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
/*     */ public final class SerializerCache
/*     */ {
/*  31 */   private HashMap<TypeKey, JsonSerializer<Object>> _sharedMap = new HashMap(64);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  36 */   private ReadOnlyClassToSerializerMap _readOnlyMap = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReadOnlyClassToSerializerMap getReadOnlyLookupMap()
/*     */   {
/*     */     ReadOnlyClassToSerializerMap localReadOnlyClassToSerializerMap;
/*     */     
/*     */ 
/*     */ 
/*  47 */     synchronized (this) {
/*  48 */       localReadOnlyClassToSerializerMap = this._readOnlyMap;
/*  49 */       if (localReadOnlyClassToSerializerMap == null) {
/*  50 */         this._readOnlyMap = (localReadOnlyClassToSerializerMap = ReadOnlyClassToSerializerMap.from(this._sharedMap));
/*     */       }
/*     */     }
/*  53 */     return localReadOnlyClassToSerializerMap.instance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int size()
/*     */   {
/*  63 */     return this._sharedMap.size();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public JsonSerializer<Object> untypedValueSerializer(Class<?> paramClass)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 24	com/shaded/fasterxml/jackson/databind/ser/SerializerCache:_sharedMap	Ljava/util/HashMap;
/*     */     //   8: new 7	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey
/*     */     //   11: dup
/*     */     //   12: aload_1
/*     */     //   13: iconst_0
/*     */     //   14: invokespecial 48	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey:<init>	(Ljava/lang/Class;Z)V
/*     */     //   17: invokevirtual 52	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   20: checkcast 54	com/shaded/fasterxml/jackson/databind/JsonSerializer
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #72	-> byte code offset #0
/*     */     //   Java source line #73	-> byte code offset #4
/*     */     //   Java source line #74	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	SerializerCache
/*     */     //   0	31	1	paramClass	Class<?>
/*     */     //   2	26	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public JsonSerializer<Object> untypedValueSerializer(JavaType paramJavaType)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 24	com/shaded/fasterxml/jackson/databind/ser/SerializerCache:_sharedMap	Ljava/util/HashMap;
/*     */     //   8: new 7	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey
/*     */     //   11: dup
/*     */     //   12: aload_1
/*     */     //   13: iconst_0
/*     */     //   14: invokespecial 60	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey:<init>	(Lcom/shaded/fasterxml/jackson/databind/JavaType;Z)V
/*     */     //   17: invokevirtual 52	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   20: checkcast 54	com/shaded/fasterxml/jackson/databind/JsonSerializer
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #79	-> byte code offset #0
/*     */     //   Java source line #80	-> byte code offset #4
/*     */     //   Java source line #81	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	SerializerCache
/*     */     //   0	31	1	paramJavaType	JavaType
/*     */     //   2	26	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public JsonSerializer<Object> typedValueSerializer(JavaType paramJavaType)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 24	com/shaded/fasterxml/jackson/databind/ser/SerializerCache:_sharedMap	Ljava/util/HashMap;
/*     */     //   8: new 7	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey
/*     */     //   11: dup
/*     */     //   12: aload_1
/*     */     //   13: iconst_1
/*     */     //   14: invokespecial 60	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey:<init>	(Lcom/shaded/fasterxml/jackson/databind/JavaType;Z)V
/*     */     //   17: invokevirtual 52	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   20: checkcast 54	com/shaded/fasterxml/jackson/databind/JsonSerializer
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #86	-> byte code offset #0
/*     */     //   Java source line #87	-> byte code offset #4
/*     */     //   Java source line #88	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	SerializerCache
/*     */     //   0	31	1	paramJavaType	JavaType
/*     */     //   2	26	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public JsonSerializer<Object> typedValueSerializer(Class<?> paramClass)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 24	com/shaded/fasterxml/jackson/databind/ser/SerializerCache:_sharedMap	Ljava/util/HashMap;
/*     */     //   8: new 7	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey
/*     */     //   11: dup
/*     */     //   12: aload_1
/*     */     //   13: iconst_1
/*     */     //   14: invokespecial 48	com/shaded/fasterxml/jackson/databind/ser/SerializerCache$TypeKey:<init>	(Ljava/lang/Class;Z)V
/*     */     //   17: invokevirtual 52	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   20: checkcast 54	com/shaded/fasterxml/jackson/databind/JsonSerializer
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #93	-> byte code offset #0
/*     */     //   Java source line #94	-> byte code offset #4
/*     */     //   Java source line #95	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	SerializerCache
/*     */     //   0	31	1	paramClass	Class<?>
/*     */     //   2	26	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   public void addTypedSerializer(JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 111 */     synchronized (this) {
/* 112 */       if (this._sharedMap.put(new TypeKey(paramJavaType, true), paramJsonSerializer) == null)
/*     */       {
/* 114 */         this._readOnlyMap = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addTypedSerializer(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 121 */     synchronized (this) {
/* 122 */       if (this._sharedMap.put(new TypeKey(paramClass, true), paramJsonSerializer) == null)
/*     */       {
/* 124 */         this._readOnlyMap = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addAndResolveNonTypedSerializer(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 133 */     synchronized (this) {
/* 134 */       if (this._sharedMap.put(new TypeKey(paramClass, false), paramJsonSerializer) == null)
/*     */       {
/* 136 */         this._readOnlyMap = null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */       if ((paramJsonSerializer instanceof ResolvableSerializer)) {
/* 146 */         ((ResolvableSerializer)paramJsonSerializer).resolve(paramSerializerProvider);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addAndResolveNonTypedSerializer(JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 155 */     synchronized (this) {
/* 156 */       if (this._sharedMap.put(new TypeKey(paramJavaType, false), paramJsonSerializer) == null)
/*     */       {
/* 158 */         this._readOnlyMap = null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 167 */       if ((paramJsonSerializer instanceof ResolvableSerializer)) {
/* 168 */         ((ResolvableSerializer)paramJsonSerializer).resolve(paramSerializerProvider);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void flush()
/*     */   {
/* 178 */     this._sharedMap.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class TypeKey
/*     */   {
/*     */     protected int _hashCode;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Class<?> _class;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected JavaType _type;
/*     */     
/*     */ 
/*     */ 
/*     */     protected boolean _isTyped;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public TypeKey(Class<?> paramClass, boolean paramBoolean)
/*     */     {
/* 208 */       this._class = paramClass;
/* 209 */       this._type = null;
/* 210 */       this._isTyped = paramBoolean;
/* 211 */       this._hashCode = hash(paramClass, paramBoolean);
/*     */     }
/*     */     
/*     */     public TypeKey(JavaType paramJavaType, boolean paramBoolean) {
/* 215 */       this._type = paramJavaType;
/* 216 */       this._class = null;
/* 217 */       this._isTyped = paramBoolean;
/* 218 */       this._hashCode = hash(paramJavaType, paramBoolean);
/*     */     }
/*     */     
/*     */     private static final int hash(Class<?> paramClass, boolean paramBoolean) {
/* 222 */       int i = paramClass.getName().hashCode();
/* 223 */       if (paramBoolean) {
/* 224 */         i++;
/*     */       }
/* 226 */       return i;
/*     */     }
/*     */     
/*     */     private static final int hash(JavaType paramJavaType, boolean paramBoolean) {
/* 230 */       int i = paramJavaType.hashCode() - 1;
/* 231 */       if (paramBoolean) {
/* 232 */         i--;
/*     */       }
/* 234 */       return i;
/*     */     }
/*     */     
/*     */     public void resetTyped(Class<?> paramClass) {
/* 238 */       this._type = null;
/* 239 */       this._class = paramClass;
/* 240 */       this._isTyped = true;
/* 241 */       this._hashCode = hash(paramClass, true);
/*     */     }
/*     */     
/*     */     public void resetUntyped(Class<?> paramClass) {
/* 245 */       this._type = null;
/* 246 */       this._class = paramClass;
/* 247 */       this._isTyped = false;
/* 248 */       this._hashCode = hash(paramClass, false);
/*     */     }
/*     */     
/*     */     public void resetTyped(JavaType paramJavaType) {
/* 252 */       this._type = paramJavaType;
/* 253 */       this._class = null;
/* 254 */       this._isTyped = true;
/* 255 */       this._hashCode = hash(paramJavaType, true);
/*     */     }
/*     */     
/*     */     public void resetUntyped(JavaType paramJavaType) {
/* 259 */       this._type = paramJavaType;
/* 260 */       this._class = null;
/* 261 */       this._isTyped = false;
/* 262 */       this._hashCode = hash(paramJavaType, false);
/*     */     }
/*     */     
/* 265 */     public final int hashCode() { return this._hashCode; }
/*     */     
/*     */     public final String toString() {
/* 268 */       if (this._class != null) {
/* 269 */         return "{class: " + this._class.getName() + ", typed? " + this._isTyped + "}";
/*     */       }
/* 271 */       return "{type: " + this._type + ", typed? " + this._isTyped + "}";
/*     */     }
/*     */     
/*     */ 
/*     */     public final boolean equals(Object paramObject)
/*     */     {
/* 277 */       if (paramObject == null) return false;
/* 278 */       if (paramObject == this) return true;
/* 279 */       if (paramObject.getClass() != getClass()) {
/* 280 */         return false;
/*     */       }
/* 282 */       TypeKey localTypeKey = (TypeKey)paramObject;
/* 283 */       if (localTypeKey._isTyped == this._isTyped) {
/* 284 */         if (this._class != null) {
/* 285 */           return localTypeKey._class == this._class;
/*     */         }
/* 287 */         return this._type.equals(localTypeKey._type);
/*     */       }
/* 289 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/SerializerCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */