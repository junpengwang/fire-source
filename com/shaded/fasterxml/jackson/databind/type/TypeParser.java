/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeParser
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final TypeFactory _factory;
/*     */   
/*     */   public TypeParser(TypeFactory paramTypeFactory)
/*     */   {
/*  22 */     this._factory = paramTypeFactory;
/*     */   }
/*     */   
/*     */   public JavaType parse(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/*  28 */     paramString = paramString.trim();
/*  29 */     MyTokenizer localMyTokenizer = new MyTokenizer(paramString);
/*  30 */     JavaType localJavaType = parseType(localMyTokenizer);
/*     */     
/*  32 */     if (localMyTokenizer.hasMoreTokens()) {
/*  33 */       throw _problem(localMyTokenizer, "Unexpected tokens after complete type");
/*     */     }
/*  35 */     return localJavaType;
/*     */   }
/*     */   
/*     */   protected JavaType parseType(MyTokenizer paramMyTokenizer)
/*     */     throws IllegalArgumentException
/*     */   {
/*  41 */     if (!paramMyTokenizer.hasMoreTokens()) {
/*  42 */       throw _problem(paramMyTokenizer, "Unexpected end-of-string");
/*     */     }
/*  44 */     Class localClass = findClass(paramMyTokenizer.nextToken(), paramMyTokenizer);
/*     */     
/*  46 */     if (paramMyTokenizer.hasMoreTokens()) {
/*  47 */       String str = paramMyTokenizer.nextToken();
/*  48 */       if ("<".equals(str)) {
/*  49 */         return this._factory._fromParameterizedClass(localClass, parseTypes(paramMyTokenizer));
/*     */       }
/*     */       
/*  52 */       paramMyTokenizer.pushBack(str);
/*     */     }
/*  54 */     return this._factory._fromClass(localClass, null);
/*     */   }
/*     */   
/*     */   protected List<JavaType> parseTypes(MyTokenizer paramMyTokenizer)
/*     */     throws IllegalArgumentException
/*     */   {
/*  60 */     ArrayList localArrayList = new ArrayList();
/*  61 */     while (paramMyTokenizer.hasMoreTokens()) {
/*  62 */       localArrayList.add(parseType(paramMyTokenizer));
/*  63 */       if (!paramMyTokenizer.hasMoreTokens()) break;
/*  64 */       String str = paramMyTokenizer.nextToken();
/*  65 */       if (">".equals(str)) return localArrayList;
/*  66 */       if (!",".equals(str)) {
/*  67 */         throw _problem(paramMyTokenizer, "Unexpected token '" + str + "', expected ',' or '>')");
/*     */       }
/*     */     }
/*  70 */     throw _problem(paramMyTokenizer, "Unexpected end-of-string");
/*     */   }
/*     */   
/*     */   protected Class<?> findClass(String paramString, MyTokenizer paramMyTokenizer)
/*     */   {
/*     */     try {
/*  76 */       return ClassUtil.findClass(paramString);
/*     */     } catch (Exception localException) {
/*  78 */       if ((localException instanceof RuntimeException)) {
/*  79 */         throw ((RuntimeException)localException);
/*     */       }
/*  81 */       throw _problem(paramMyTokenizer, "Can not locate class '" + paramString + "', problem: " + localException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException _problem(MyTokenizer paramMyTokenizer, String paramString)
/*     */   {
/*  87 */     return new IllegalArgumentException("Failed to parse type '" + paramMyTokenizer.getAllInput() + "' (remaining: '" + paramMyTokenizer.getRemainingInput() + "'): " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   static final class MyTokenizer
/*     */     extends StringTokenizer
/*     */   {
/*     */     protected final String _input;
/*     */     
/*     */     protected int _index;
/*     */     protected String _pushbackToken;
/*     */     
/*     */     public MyTokenizer(String paramString)
/*     */     {
/* 101 */       super("<,>", true);
/* 102 */       this._input = paramString;
/*     */     }
/*     */     
/*     */     public boolean hasMoreTokens()
/*     */     {
/* 107 */       return (this._pushbackToken != null) || (super.hasMoreTokens());
/*     */     }
/*     */     
/*     */     public String nextToken()
/*     */     {
/*     */       String str;
/* 113 */       if (this._pushbackToken != null) {
/* 114 */         str = this._pushbackToken;
/* 115 */         this._pushbackToken = null;
/*     */       } else {
/* 117 */         str = super.nextToken();
/*     */       }
/* 119 */       this._index += str.length();
/* 120 */       return str;
/*     */     }
/*     */     
/*     */     public void pushBack(String paramString) {
/* 124 */       this._pushbackToken = paramString;
/* 125 */       this._index -= paramString.length();
/*     */     }
/*     */     
/* 128 */     public String getAllInput() { return this._input; }
/* 129 */     public String getUsedInput() { return this._input.substring(0, this._index); }
/* 130 */     public String getRemainingInput() { return this._input.substring(this._index); }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/TypeParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */