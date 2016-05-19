/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ import org.shaded.apache.http.util.LangUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHeaderElement
/*     */   implements HeaderElement, Cloneable
/*     */ {
/*     */   private final String name;
/*     */   private final String value;
/*     */   private final NameValuePair[] parameters;
/*     */   
/*     */   public BasicHeaderElement(String name, String value, NameValuePair[] parameters)
/*     */   {
/*  65 */     if (name == null) {
/*  66 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  68 */     this.name = name;
/*  69 */     this.value = value;
/*  70 */     if (parameters != null) {
/*  71 */       this.parameters = parameters;
/*     */     } else {
/*  73 */       this.parameters = new NameValuePair[0];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHeaderElement(String name, String value)
/*     */   {
/*  84 */     this(name, value, null);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  88 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  92 */     return this.value;
/*     */   }
/*     */   
/*     */   public NameValuePair[] getParameters() {
/*  96 */     return (NameValuePair[])this.parameters.clone();
/*     */   }
/*     */   
/*     */   public int getParameterCount() {
/* 100 */     return this.parameters.length;
/*     */   }
/*     */   
/*     */   public NameValuePair getParameter(int index)
/*     */   {
/* 105 */     return this.parameters[index];
/*     */   }
/*     */   
/*     */   public NameValuePair getParameterByName(String name) {
/* 109 */     if (name == null) {
/* 110 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/* 112 */     NameValuePair found = null;
/* 113 */     for (int i = 0; i < this.parameters.length; i++) {
/* 114 */       NameValuePair current = this.parameters[i];
/* 115 */       if (current.getName().equalsIgnoreCase(name)) {
/* 116 */         found = current;
/* 117 */         break;
/*     */       }
/*     */     }
/* 120 */     return found;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 124 */     if (object == null) return false;
/* 125 */     if (this == object) return true;
/* 126 */     if ((object instanceof HeaderElement)) {
/* 127 */       BasicHeaderElement that = (BasicHeaderElement)object;
/* 128 */       return (this.name.equals(that.name)) && (LangUtils.equals(this.value, that.value)) && (LangUtils.equals(this.parameters, that.parameters));
/*     */     }
/*     */     
/*     */ 
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 137 */     int hash = 17;
/* 138 */     hash = LangUtils.hashCode(hash, this.name);
/* 139 */     hash = LangUtils.hashCode(hash, this.value);
/* 140 */     for (int i = 0; i < this.parameters.length; i++) {
/* 141 */       hash = LangUtils.hashCode(hash, this.parameters[i]);
/*     */     }
/* 143 */     return hash;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     CharArrayBuffer buffer = new CharArrayBuffer(64);
/* 148 */     buffer.append(this.name);
/* 149 */     if (this.value != null) {
/* 150 */       buffer.append("=");
/* 151 */       buffer.append(this.value);
/*     */     }
/* 153 */     for (int i = 0; i < this.parameters.length; i++) {
/* 154 */       buffer.append("; ");
/* 155 */       buffer.append(this.parameters[i]);
/*     */     }
/* 157 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 163 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeaderElement.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */