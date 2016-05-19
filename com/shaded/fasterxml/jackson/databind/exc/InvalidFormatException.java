/*    */ package com.shaded.fasterxml.jackson.databind.exc;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
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
/*    */ public class InvalidFormatException
/*    */   extends JsonMappingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final Object _value;
/*    */   protected final Class<?> _targetType;
/*    */   
/*    */   public InvalidFormatException(String paramString, Object paramObject, Class<?> paramClass)
/*    */   {
/* 39 */     super(paramString);
/* 40 */     this._value = paramObject;
/* 41 */     this._targetType = paramClass;
/*    */   }
/*    */   
/*    */ 
/*    */   public InvalidFormatException(String paramString, JsonLocation paramJsonLocation, Object paramObject, Class<?> paramClass)
/*    */   {
/* 47 */     super(paramString, paramJsonLocation);
/* 48 */     this._value = paramObject;
/* 49 */     this._targetType = paramClass;
/*    */   }
/*    */   
/*    */ 
/*    */   public static InvalidFormatException from(JsonParser paramJsonParser, String paramString, Object paramObject, Class<?> paramClass)
/*    */   {
/* 55 */     return new InvalidFormatException(paramString, paramJsonParser.getTokenLocation(), paramObject, paramClass);
/*    */   }
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
/*    */   public Object getValue()
/*    */   {
/* 72 */     return this._value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class<?> getTargetType()
/*    */   {
/* 82 */     return this._targetType;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/exc/InvalidFormatException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */