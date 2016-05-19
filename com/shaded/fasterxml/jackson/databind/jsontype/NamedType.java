/*    */ package com.shaded.fasterxml.jackson.databind.jsontype;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NamedType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final Class<?> _class;
/*    */   protected final int _hashCode;
/*    */   protected String _name;
/*    */   
/*    */   public NamedType(Class<?> paramClass)
/*    */   {
/* 19 */     this(paramClass, null);
/*    */   }
/*    */   
/*    */   public NamedType(Class<?> paramClass, String paramString) {
/* 23 */     this._class = paramClass;
/* 24 */     this._hashCode = paramClass.getName().hashCode();
/* 25 */     setName(paramString);
/*    */   }
/*    */   
/* 28 */   public Class<?> getType() { return this._class; }
/* 29 */   public String getName() { return this._name; }
/*    */   
/* 31 */   public void setName(String paramString) { this._name = ((paramString == null) || (paramString.length() == 0) ? null : paramString); }
/*    */   
/*    */   public boolean hasName() {
/* 34 */     return this._name != null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 42 */     if (paramObject == this) return true;
/* 43 */     if (paramObject == null) return false;
/* 44 */     if (paramObject.getClass() != getClass()) return false;
/* 45 */     return this._class == ((NamedType)paramObject)._class;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 49 */     return this._hashCode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 53 */     return "[NamedType, class " + this._class.getName() + ", name: " + (this._name == null ? "null" : new StringBuilder().append("'").append(this._name).append("'").toString()) + "]";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/NamedType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */