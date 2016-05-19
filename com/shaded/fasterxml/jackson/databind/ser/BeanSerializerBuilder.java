/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanSerializerBuilder
/*     */ {
/*  19 */   private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BeanDescription _beanDesc;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SerializationConfig _config;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected List<BeanPropertyWriter> _properties;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanPropertyWriter[] _filteredProperties;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnyGetterWriter _anyGetter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object _filterId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedMember _typeId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectIdWriter _objectIdWriter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanSerializerBuilder(BeanDescription paramBeanDescription)
/*     */   {
/*  77 */     this._beanDesc = paramBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected BeanSerializerBuilder(BeanSerializerBuilder paramBeanSerializerBuilder)
/*     */   {
/*  84 */     this._beanDesc = paramBeanSerializerBuilder._beanDesc;
/*  85 */     this._properties = paramBeanSerializerBuilder._properties;
/*  86 */     this._filteredProperties = paramBeanSerializerBuilder._filteredProperties;
/*  87 */     this._anyGetter = paramBeanSerializerBuilder._anyGetter;
/*  88 */     this._filterId = paramBeanSerializerBuilder._filterId;
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
/*     */   protected void setConfig(SerializationConfig paramSerializationConfig)
/*     */   {
/* 101 */     this._config = paramSerializationConfig;
/*     */   }
/*     */   
/*     */   public void setProperties(List<BeanPropertyWriter> paramList) {
/* 105 */     this._properties = paramList;
/*     */   }
/*     */   
/*     */   public void setFilteredProperties(BeanPropertyWriter[] paramArrayOfBeanPropertyWriter) {
/* 109 */     this._filteredProperties = paramArrayOfBeanPropertyWriter;
/*     */   }
/*     */   
/*     */   public void setAnyGetter(AnyGetterWriter paramAnyGetterWriter) {
/* 113 */     this._anyGetter = paramAnyGetterWriter;
/*     */   }
/*     */   
/*     */   public void setFilterId(Object paramObject) {
/* 117 */     this._filterId = paramObject;
/*     */   }
/*     */   
/*     */   public void setTypeId(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 122 */     if (this._typeId != null) {
/* 123 */       throw new IllegalArgumentException("Multiple type ids specified with " + this._typeId + " and " + paramAnnotatedMember);
/*     */     }
/* 125 */     this._typeId = paramAnnotatedMember;
/*     */   }
/*     */   
/*     */   public void setObjectIdWriter(ObjectIdWriter paramObjectIdWriter) {
/* 129 */     this._objectIdWriter = paramObjectIdWriter;
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
/* 140 */   public AnnotatedClass getClassInfo() { return this._beanDesc.getClassInfo(); }
/*     */   
/* 142 */   public BeanDescription getBeanDescription() { return this._beanDesc; }
/*     */   
/* 144 */   public List<BeanPropertyWriter> getProperties() { return this._properties; }
/*     */   
/* 146 */   public boolean hasProperties() { return (this._properties != null) && (this._properties.size() > 0); }
/*     */   
/*     */ 
/* 149 */   public BeanPropertyWriter[] getFilteredProperties() { return this._filteredProperties; }
/*     */   
/* 151 */   public AnyGetterWriter getAnyGetter() { return this._anyGetter; }
/*     */   
/* 153 */   public Object getFilterId() { return this._filterId; }
/*     */   
/* 155 */   public AnnotatedMember getTypeId() { return this._typeId; }
/*     */   
/* 157 */   public ObjectIdWriter getObjectIdWriter() { return this._objectIdWriter; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> build()
/*     */   {
/*     */     BeanPropertyWriter[] arrayOfBeanPropertyWriter;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 174 */     if ((this._properties == null) || (this._properties.isEmpty())) {
/* 175 */       if (this._anyGetter == null) {
/* 176 */         return null;
/*     */       }
/* 178 */       arrayOfBeanPropertyWriter = NO_PROPERTIES;
/*     */     } else {
/* 180 */       arrayOfBeanPropertyWriter = (BeanPropertyWriter[])this._properties.toArray(new BeanPropertyWriter[this._properties.size()]);
/*     */     }
/*     */     
/* 183 */     return new BeanSerializer(this._beanDesc.getType(), this, arrayOfBeanPropertyWriter, this._filteredProperties);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanSerializer createDummy()
/*     */   {
/* 193 */     return BeanSerializer.createDummy(this._beanDesc.getType());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BeanSerializerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */