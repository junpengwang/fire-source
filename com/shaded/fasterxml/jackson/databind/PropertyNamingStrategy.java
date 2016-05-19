/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertyNamingStrategy
/*     */   implements Serializable
/*     */ {
/*     */   public String nameForField(MapperConfig<?> paramMapperConfig, AnnotatedField paramAnnotatedField, String paramString)
/*     */   {
/*  55 */     return paramString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String nameForGetterMethod(MapperConfig<?> paramMapperConfig, AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */   {
/*  76 */     return paramString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String nameForSetterMethod(MapperConfig<?> paramMapperConfig, AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */   {
/*  96 */     return paramString;
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
/*     */ 
/*     */ 
/*     */   public String nameForConstructorParameter(MapperConfig<?> paramMapperConfig, AnnotatedParameter paramAnnotatedParameter, String paramString)
/*     */   {
/* 114 */     return paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class PropertyNamingStrategyBase
/*     */     extends PropertyNamingStrategy
/*     */   {
/*     */     public String nameForField(MapperConfig<?> paramMapperConfig, AnnotatedField paramAnnotatedField, String paramString)
/*     */     {
/* 128 */       return translate(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public String nameForGetterMethod(MapperConfig<?> paramMapperConfig, AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */     {
/* 134 */       return translate(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public String nameForSetterMethod(MapperConfig<?> paramMapperConfig, AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */     {
/* 140 */       return translate(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public String nameForConstructorParameter(MapperConfig<?> paramMapperConfig, AnnotatedParameter paramAnnotatedParameter, String paramString)
/*     */     {
/* 147 */       return translate(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract String translate(String paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */   public static final PropertyNamingStrategy CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES = new LowerCaseWithUnderscoresStrategy();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class LowerCaseWithUnderscoresStrategy
/*     */     extends PropertyNamingStrategy.PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String paramString)
/*     */     {
/* 220 */       if (paramString == null) return paramString;
/* 221 */       int i = paramString.length();
/* 222 */       StringBuilder localStringBuilder = new StringBuilder(i * 2);
/* 223 */       int j = 0;
/* 224 */       int k = 0;
/* 225 */       for (int m = 0; m < i; m++)
/*     */       {
/* 227 */         char c = paramString.charAt(m);
/* 228 */         if ((m > 0) || (c != '_'))
/*     */         {
/* 230 */           if (Character.isUpperCase(c))
/*     */           {
/* 232 */             if ((k == 0) && (j > 0) && (localStringBuilder.charAt(j - 1) != '_'))
/*     */             {
/* 234 */               localStringBuilder.append('_');
/* 235 */               j++;
/*     */             }
/* 237 */             c = Character.toLowerCase(c);
/* 238 */             k = 1;
/*     */           }
/*     */           else
/*     */           {
/* 242 */             k = 0;
/*     */           }
/* 244 */           localStringBuilder.append(c);
/* 245 */           j++;
/*     */         }
/*     */       }
/* 248 */       return j > 0 ? localStringBuilder.toString() : paramString;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 257 */   public static final PropertyNamingStrategy PASCAL_CASE_TO_CAMEL_CASE = new PascalCaseStrategy();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class PascalCaseStrategy
/*     */     extends PropertyNamingStrategy.PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String paramString)
/*     */     {
/* 288 */       if ((paramString == null) || (paramString.length() == 0)) {
/* 289 */         return paramString;
/*     */       }
/*     */       
/* 292 */       char c = paramString.charAt(0);
/* 293 */       if (Character.isUpperCase(c)) {
/* 294 */         return paramString;
/*     */       }
/* 296 */       StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 297 */       localStringBuilder.setCharAt(0, Character.toUpperCase(c));
/* 298 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/PropertyNamingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */