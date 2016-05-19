/*     */ package com.shaded.fasterxml.jackson.databind.annotation;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JacksonAnnotation;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter.None;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
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
/*     */ @Target({java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.PARAMETER})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @JacksonAnnotation
/*     */ public @interface JsonSerialize
/*     */ {
/*     */   Class<? extends JsonSerializer<?>> using() default JsonSerializer.None.class;
/*     */   
/*     */   Class<? extends JsonSerializer<?>> contentUsing() default JsonSerializer.None.class;
/*     */   
/*     */   Class<? extends JsonSerializer<?>> keyUsing() default JsonSerializer.None.class;
/*     */   
/*     */   Class<?> as() default NoClass.class;
/*     */   
/*     */   Class<?> keyAs() default NoClass.class;
/*     */   
/*     */   Class<?> contentAs() default NoClass.class;
/*     */   
/*     */   Typing typing() default Typing.DYNAMIC;
/*     */   
/*     */   Class<? extends Converter<?, ?>> converter() default Converter.None.class;
/*     */   
/*     */   Class<? extends Converter<?, ?>> contentConverter() default Converter.None.class;
/*     */   
/*     */   @Deprecated
/*     */   Inclusion include() default Inclusion.ALWAYS;
/*     */   
/*     */   public static enum Inclusion
/*     */   {
/* 164 */     ALWAYS, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 170 */     NON_NULL, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 180 */     NON_DEFAULT, 
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
/* 200 */     NON_EMPTY;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Inclusion() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Typing
/*     */   {
/* 215 */     DYNAMIC, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 221 */     STATIC;
/*     */     
/*     */     private Typing() {}
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/annotation/JsonSerialize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */