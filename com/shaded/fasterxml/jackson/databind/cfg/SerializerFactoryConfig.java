/*    */ package com.shaded.fasterxml.jackson.databind.cfg;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.Serializers;
/*    */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SerializerFactoryConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   protected static final Serializers[] NO_SERIALIZERS = new Serializers[0];
/*    */   
/* 20 */   protected static final BeanSerializerModifier[] NO_MODIFIERS = new BeanSerializerModifier[0];
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected final Serializers[] _additionalSerializers;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected final Serializers[] _additionalKeySerializers;
/*    */   
/*    */ 
/*    */ 
/*    */   protected final BeanSerializerModifier[] _modifiers;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public SerializerFactoryConfig()
/*    */   {
/* 41 */     this(null, null, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected SerializerFactoryConfig(Serializers[] paramArrayOfSerializers1, Serializers[] paramArrayOfSerializers2, BeanSerializerModifier[] paramArrayOfBeanSerializerModifier)
/*    */   {
/* 48 */     this._additionalSerializers = (paramArrayOfSerializers1 == null ? NO_SERIALIZERS : paramArrayOfSerializers1);
/*    */     
/* 50 */     this._additionalKeySerializers = (paramArrayOfSerializers2 == null ? NO_SERIALIZERS : paramArrayOfSerializers2);
/*    */     
/* 52 */     this._modifiers = (paramArrayOfBeanSerializerModifier == null ? NO_MODIFIERS : paramArrayOfBeanSerializerModifier);
/*    */   }
/*    */   
/*    */   public SerializerFactoryConfig withAdditionalSerializers(Serializers paramSerializers)
/*    */   {
/* 57 */     if (paramSerializers == null) {
/* 58 */       throw new IllegalArgumentException("Can not pass null Serializers");
/*    */     }
/* 60 */     Serializers[] arrayOfSerializers = (Serializers[])ArrayBuilders.insertInListNoDup(this._additionalSerializers, paramSerializers);
/* 61 */     return new SerializerFactoryConfig(arrayOfSerializers, this._additionalKeySerializers, this._modifiers);
/*    */   }
/*    */   
/*    */   public SerializerFactoryConfig withAdditionalKeySerializers(Serializers paramSerializers)
/*    */   {
/* 66 */     if (paramSerializers == null) {
/* 67 */       throw new IllegalArgumentException("Can not pass null Serializers");
/*    */     }
/* 69 */     Serializers[] arrayOfSerializers = (Serializers[])ArrayBuilders.insertInListNoDup(this._additionalKeySerializers, paramSerializers);
/* 70 */     return new SerializerFactoryConfig(this._additionalSerializers, arrayOfSerializers, this._modifiers);
/*    */   }
/*    */   
/*    */   public SerializerFactoryConfig withSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier)
/*    */   {
/* 75 */     if (paramBeanSerializerModifier == null) {
/* 76 */       throw new IllegalArgumentException("Can not pass null modifier");
/*    */     }
/* 78 */     BeanSerializerModifier[] arrayOfBeanSerializerModifier = (BeanSerializerModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, paramBeanSerializerModifier);
/* 79 */     return new SerializerFactoryConfig(this._additionalSerializers, this._additionalKeySerializers, arrayOfBeanSerializerModifier);
/*    */   }
/*    */   
/* 82 */   public boolean hasSerializers() { return this._additionalSerializers.length > 0; }
/*    */   
/* 84 */   public boolean hasKeySerializers() { return this._additionalKeySerializers.length > 0; }
/*    */   
/* 86 */   public boolean hasSerializerModifiers() { return this._modifiers.length > 0; }
/*    */   
/*    */   public Iterable<Serializers> serializers() {
/* 89 */     return ArrayBuilders.arrayAsIterable(this._additionalSerializers);
/*    */   }
/*    */   
/*    */   public Iterable<Serializers> keySerializers() {
/* 93 */     return ArrayBuilders.arrayAsIterable(this._additionalKeySerializers);
/*    */   }
/*    */   
/*    */   public Iterable<BeanSerializerModifier> serializerModifiers() {
/* 97 */     return ArrayBuilders.arrayAsIterable(this._modifiers);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/cfg/SerializerFactoryConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */