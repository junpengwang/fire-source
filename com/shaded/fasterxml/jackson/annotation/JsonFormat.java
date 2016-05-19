/*     */ package com.shaded.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ @Target({java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @JacksonAnnotation
/*     */ public @interface JsonFormat
/*     */ {
/*     */   public static final String DEFAULT_LOCALE = "##default";
/*     */   public static final String DEFAULT_TIMEZONE = "##default";
/*     */   
/*     */   String pattern() default "";
/*     */   
/*     */   Shape shape() default Shape.ANY;
/*     */   
/*     */   String locale() default "##default";
/*     */   
/*     */   String timezone() default "##default";
/*     */   
/*     */   public static enum Shape
/*     */   {
/* 122 */     ANY, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 128 */     SCALAR, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 133 */     ARRAY, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 138 */     OBJECT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */     NUMBER, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 150 */     NUMBER_FLOAT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */     NUMBER_INT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 161 */     STRING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 167 */     BOOLEAN;
/*     */     
/*     */     private Shape() {}
/*     */     
/* 171 */     public boolean isNumeric() { return (this == NUMBER) || (this == NUMBER_INT) || (this == NUMBER_FLOAT); }
/*     */     
/*     */     public boolean isStructured()
/*     */     {
/* 175 */       return (this == OBJECT) || (this == ARRAY);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class Value
/*     */   {
/*     */     private final String pattern;
/*     */     
/*     */     private final JsonFormat.Shape shape;
/*     */     
/*     */     private final Locale locale;
/*     */     private final TimeZone timezone;
/*     */     
/*     */     public Value()
/*     */     {
/* 191 */       this("", JsonFormat.Shape.ANY, "", "");
/*     */     }
/*     */     
/*     */     public Value(JsonFormat paramJsonFormat) {
/* 195 */       this(paramJsonFormat.pattern(), paramJsonFormat.shape(), paramJsonFormat.locale(), paramJsonFormat.timezone());
/*     */     }
/*     */     
/*     */     public Value(String paramString1, JsonFormat.Shape paramShape, String paramString2, String paramString3)
/*     */     {
/* 200 */       this(paramString1, paramShape, (paramString2 == null) || (paramString2.length() == 0) || ("##default".equals(paramString2)) ? null : new Locale(paramString2), (paramString3 == null) || (paramString3.length() == 0) || ("##default".equals(paramString3)) ? null : TimeZone.getTimeZone(paramString3));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Value(String paramString, JsonFormat.Shape paramShape, Locale paramLocale, TimeZone paramTimeZone)
/*     */     {
/* 213 */       this.pattern = paramString;
/* 214 */       this.shape = paramShape;
/* 215 */       this.locale = paramLocale;
/* 216 */       this.timezone = paramTimeZone;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Value withPattern(String paramString)
/*     */     {
/* 223 */       return new Value(paramString, this.shape, this.locale, this.timezone);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Value withShape(JsonFormat.Shape paramShape)
/*     */     {
/* 230 */       return new Value(this.pattern, paramShape, this.locale, this.timezone);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Value withLocale(Locale paramLocale)
/*     */     {
/* 237 */       return new Value(this.pattern, this.shape, paramLocale, this.timezone);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Value withTimeZone(TimeZone paramTimeZone)
/*     */     {
/* 244 */       return new Value(this.pattern, this.shape, this.locale, paramTimeZone);
/*     */     }
/*     */     
/* 247 */     public String getPattern() { return this.pattern; }
/* 248 */     public JsonFormat.Shape getShape() { return this.shape; }
/* 249 */     public Locale getLocale() { return this.locale; }
/* 250 */     public TimeZone getTimeZone() { return this.timezone; }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/annotation/JsonFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */