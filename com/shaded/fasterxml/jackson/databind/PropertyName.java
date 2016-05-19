/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
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
/*     */ public class PropertyName
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7930806520033045126L;
/*     */   private static final String _USE_DEFAULT = "";
/*     */   private static final String _NO_NAME = "#disabled";
/*  23 */   public static final PropertyName USE_DEFAULT = new PropertyName("", null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  30 */   public static final PropertyName NO_NAME = new PropertyName(new String("#disabled"), null);
/*     */   
/*     */ 
/*     */ 
/*     */   protected final String _simpleName;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final String _namespace;
/*     */   
/*     */ 
/*     */ 
/*     */   public PropertyName(String paramString)
/*     */   {
/*  44 */     this(paramString, null);
/*     */   }
/*     */   
/*     */   public PropertyName(String paramString1, String paramString2)
/*     */   {
/*  49 */     this._simpleName = (paramString1 == null ? "" : paramString1);
/*  50 */     this._namespace = paramString2;
/*     */   }
/*     */   
/*     */   protected Object readResolve()
/*     */   {
/*  55 */     if ((this._simpleName == null) || ("".equals(this._simpleName))) {
/*  56 */       return USE_DEFAULT;
/*     */     }
/*  58 */     if (this._simpleName.equals("#disabled")) {
/*  59 */       return NO_NAME;
/*     */     }
/*  61 */     return this;
/*     */   }
/*     */   
/*     */   public static PropertyName construct(String paramString1, String paramString2)
/*     */   {
/*  66 */     if (paramString1 == null) {
/*  67 */       paramString1 = "";
/*     */     }
/*  69 */     if ((paramString2 == null) && (paramString1.length() == 0)) {
/*  70 */       return USE_DEFAULT;
/*     */     }
/*  72 */     return new PropertyName(paramString1, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName withSimpleName(String paramString)
/*     */   {
/*  81 */     if (paramString == null) {
/*  82 */       paramString = "";
/*     */     }
/*  84 */     if (paramString.equals(this._simpleName)) {
/*  85 */       return this;
/*     */     }
/*  87 */     return new PropertyName(paramString, this._namespace);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName withNamespace(String paramString)
/*     */   {
/*  95 */     if (paramString == null) {
/*  96 */       if (this._namespace == null) {
/*  97 */         return this;
/*     */       }
/*  99 */     } else if (paramString.equals(this._namespace)) {
/* 100 */       return this;
/*     */     }
/* 102 */     return new PropertyName(this._simpleName, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSimpleName()
/*     */   {
/* 112 */     return this._simpleName;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/* 116 */     return this._namespace;
/*     */   }
/*     */   
/*     */   public boolean hasSimpleName() {
/* 120 */     return this._simpleName.length() > 0;
/*     */   }
/*     */   
/*     */   public boolean hasNamespace() {
/* 124 */     return this._namespace != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 136 */     if (paramObject == this) return true;
/* 137 */     if (paramObject == null) { return false;
/*     */     }
/*     */     
/*     */ 
/* 141 */     if (paramObject.getClass() != getClass()) { return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 146 */     PropertyName localPropertyName = (PropertyName)paramObject;
/* 147 */     if (this._simpleName == null) {
/* 148 */       if (localPropertyName._simpleName != null) return false;
/* 149 */     } else if (!this._simpleName.equals(localPropertyName._simpleName)) {
/* 150 */       return false;
/*     */     }
/* 152 */     if (this._namespace == null) {
/* 153 */       return null == localPropertyName._namespace;
/*     */     }
/* 155 */     return this._namespace.equals(localPropertyName._namespace);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 160 */     if (this._namespace == null) {
/* 161 */       return this._simpleName.hashCode();
/*     */     }
/* 163 */     return this._namespace.hashCode() ^ this._simpleName.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 168 */     if (this._namespace == null) {
/* 169 */       return this._simpleName;
/*     */     }
/* 171 */     return "{" + this._namespace + "}" + this._simpleName;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/PropertyName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */