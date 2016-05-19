/*     */ package com.shaded.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AbstractTypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.KeyDeserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiators;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DeserializerFactoryConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3683541151102256824L;
/*  16 */   protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
/*  17 */   protected static final BeanDeserializerModifier[] NO_MODIFIERS = new BeanDeserializerModifier[0];
/*  18 */   protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
/*  19 */   protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  27 */   protected static final KeyDeserializers[] DEFAULT_KEY_DESERIALIZERS = { new StdKeyDeserializers() };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Deserializers[] _additionalDeserializers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final KeyDeserializers[] _additionalKeyDeserializers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BeanDeserializerModifier[] _modifiers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final AbstractTypeResolver[] _abstractTypeResolvers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ValueInstantiators[] _valueInstantiators;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializerFactoryConfig()
/*     */   {
/*  71 */     this(null, null, null, null, null);
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
/*     */   protected DeserializerFactoryConfig(Deserializers[] paramArrayOfDeserializers, KeyDeserializers[] paramArrayOfKeyDeserializers, BeanDeserializerModifier[] paramArrayOfBeanDeserializerModifier, AbstractTypeResolver[] paramArrayOfAbstractTypeResolver, ValueInstantiators[] paramArrayOfValueInstantiators)
/*     */   {
/*  84 */     this._additionalDeserializers = (paramArrayOfDeserializers == null ? NO_DESERIALIZERS : paramArrayOfDeserializers);
/*     */     
/*  86 */     this._additionalKeyDeserializers = (paramArrayOfKeyDeserializers == null ? DEFAULT_KEY_DESERIALIZERS : paramArrayOfKeyDeserializers);
/*     */     
/*  88 */     this._modifiers = (paramArrayOfBeanDeserializerModifier == null ? NO_MODIFIERS : paramArrayOfBeanDeserializerModifier);
/*  89 */     this._abstractTypeResolvers = (paramArrayOfAbstractTypeResolver == null ? NO_ABSTRACT_TYPE_RESOLVERS : paramArrayOfAbstractTypeResolver);
/*  90 */     this._valueInstantiators = (paramArrayOfValueInstantiators == null ? NO_VALUE_INSTANTIATORS : paramArrayOfValueInstantiators);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers paramDeserializers)
/*     */   {
/* 101 */     if (paramDeserializers == null) {
/* 102 */       throw new IllegalArgumentException("Can not pass null Deserializers");
/*     */     }
/* 104 */     Deserializers[] arrayOfDeserializers = (Deserializers[])ArrayBuilders.insertInListNoDup(this._additionalDeserializers, paramDeserializers);
/* 105 */     return new DeserializerFactoryConfig(arrayOfDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers paramKeyDeserializers)
/*     */   {
/* 116 */     if (paramKeyDeserializers == null) {
/* 117 */       throw new IllegalArgumentException("Can not pass null KeyDeserializers");
/*     */     }
/* 119 */     KeyDeserializers[] arrayOfKeyDeserializers = (KeyDeserializers[])ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, paramKeyDeserializers);
/* 120 */     return new DeserializerFactoryConfig(this._additionalDeserializers, arrayOfKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier)
/*     */   {
/* 132 */     if (paramBeanDeserializerModifier == null) {
/* 133 */       throw new IllegalArgumentException("Can not pass null modifier");
/*     */     }
/* 135 */     BeanDeserializerModifier[] arrayOfBeanDeserializerModifier = (BeanDeserializerModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, paramBeanDeserializerModifier);
/* 136 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, arrayOfBeanDeserializerModifier, this._abstractTypeResolvers, this._valueInstantiators);
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
/*     */   public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver paramAbstractTypeResolver)
/*     */   {
/* 149 */     if (paramAbstractTypeResolver == null) {
/* 150 */       throw new IllegalArgumentException("Can not pass null resolver");
/*     */     }
/* 152 */     AbstractTypeResolver[] arrayOfAbstractTypeResolver = (AbstractTypeResolver[])ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, paramAbstractTypeResolver);
/* 153 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, arrayOfAbstractTypeResolver, this._valueInstantiators);
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
/*     */   public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators paramValueInstantiators)
/*     */   {
/* 169 */     if (paramValueInstantiators == null) {
/* 170 */       throw new IllegalArgumentException("Can not pass null resolver");
/*     */     }
/* 172 */     ValueInstantiators[] arrayOfValueInstantiators = (ValueInstantiators[])ArrayBuilders.insertInListNoDup(this._valueInstantiators, paramValueInstantiators);
/* 173 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, arrayOfValueInstantiators);
/*     */   }
/*     */   
/*     */ 
/* 177 */   public boolean hasDeserializers() { return this._additionalDeserializers.length > 0; }
/*     */   
/* 179 */   public boolean hasKeyDeserializers() { return this._additionalKeyDeserializers.length > 0; }
/*     */   
/* 181 */   public boolean hasDeserializerModifiers() { return this._modifiers.length > 0; }
/*     */   
/* 183 */   public boolean hasAbstractTypeResolvers() { return this._abstractTypeResolvers.length > 0; }
/*     */   
/* 185 */   public boolean hasValueInstantiators() { return this._valueInstantiators.length > 0; }
/*     */   
/*     */   public Iterable<Deserializers> deserializers() {
/* 188 */     return ArrayBuilders.arrayAsIterable(this._additionalDeserializers);
/*     */   }
/*     */   
/*     */   public Iterable<KeyDeserializers> keyDeserializers() {
/* 192 */     return ArrayBuilders.arrayAsIterable(this._additionalKeyDeserializers);
/*     */   }
/*     */   
/*     */   public Iterable<BeanDeserializerModifier> deserializerModifiers() {
/* 196 */     return ArrayBuilders.arrayAsIterable(this._modifiers);
/*     */   }
/*     */   
/*     */   public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
/* 200 */     return ArrayBuilders.arrayAsIterable(this._abstractTypeResolvers);
/*     */   }
/*     */   
/*     */   public Iterable<ValueInstantiators> valueInstantiators() {
/* 204 */     return ArrayBuilders.arrayAsIterable(this._valueInstantiators);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/cfg/DeserializerFactoryConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */