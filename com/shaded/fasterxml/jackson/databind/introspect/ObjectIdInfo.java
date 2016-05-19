/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectIdInfo
/*    */ {
/*    */   protected final String _propertyName;
/*    */   protected final Class<? extends ObjectIdGenerator<?>> _generator;
/*    */   protected final Class<?> _scope;
/*    */   protected final boolean _alwaysAsId;
/*    */   
/*    */   public ObjectIdInfo(String paramString, Class<?> paramClass, Class<? extends ObjectIdGenerator<?>> paramClass1)
/*    */   {
/* 19 */     this(paramString, paramClass, paramClass1, false);
/*    */   }
/*    */   
/*    */ 
/*    */   protected ObjectIdInfo(String paramString, Class<?> paramClass, Class<? extends ObjectIdGenerator<?>> paramClass1, boolean paramBoolean)
/*    */   {
/* 25 */     this._propertyName = paramString;
/* 26 */     this._scope = paramClass;
/* 27 */     this._generator = paramClass1;
/* 28 */     this._alwaysAsId = paramBoolean;
/*    */   }
/*    */   
/*    */   public ObjectIdInfo withAlwaysAsId(boolean paramBoolean) {
/* 32 */     if (this._alwaysAsId == paramBoolean) {
/* 33 */       return this;
/*    */     }
/* 35 */     return new ObjectIdInfo(this._propertyName, this._scope, this._generator, paramBoolean);
/*    */   }
/*    */   
/* 38 */   public String getPropertyName() { return this._propertyName; }
/* 39 */   public Class<?> getScope() { return this._scope; }
/* 40 */   public Class<? extends ObjectIdGenerator<?>> getGeneratorType() { return this._generator; }
/* 41 */   public boolean getAlwaysAsId() { return this._alwaysAsId; }
/*    */   
/*    */   public String toString()
/*    */   {
/* 45 */     return "ObjectIdInfo: propName=" + this._propertyName + ", scope=" + (this._scope == null ? "null" : this._scope.getName()) + ", generatorType=" + (this._generator == null ? "null" : this._generator.getName()) + ", alwaysAsId=" + this._alwaysAsId;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/ObjectIdInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */