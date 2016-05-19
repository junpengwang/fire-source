/*     */ package com.shaded.fasterxml.jackson.databind.ext;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.Serializers;
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
/*     */ public class OptionalHandlerFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
/*     */   private static final String SERIALIZERS_FOR_JAVAX_XML = "com.shaded.fasterxml.jackson.databind.ext.CoreXMLSerializers";
/*     */   private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.shaded.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
/*     */   private static final String CLASS_NAME_DOM_NODE = "org.w3c.dom.Node";
/*     */   private static final String CLASS_NAME_DOM_DOCUMENT = "org.w3c.dom.Node";
/*     */   private static final String SERIALIZER_FOR_DOM_NODE = "com.shaded.fasterxml.jackson.databind.ext.DOMSerializer";
/*     */   private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.shaded.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
/*     */   private static final String DESERIALIZER_FOR_DOM_NODE = "com.shaded.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
/*  34 */   public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();
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
/*     */   public JsonSerializer<?> findSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */   {
/*  47 */     Class localClass = paramJavaType.getRawClass();
/*  48 */     String str1 = localClass.getName();
/*     */     
/*     */     String str2;
/*  51 */     if ((str1.startsWith("javax.xml.")) || (hasSupertypeStartingWith(localClass, "javax.xml.")))
/*     */     {
/*  53 */       str2 = "com.shaded.fasterxml.jackson.databind.ext.CoreXMLSerializers";
/*  54 */     } else { if (doesImplement(localClass, "org.w3c.dom.Node")) {
/*  55 */         return (JsonSerializer)instantiate("com.shaded.fasterxml.jackson.databind.ext.DOMSerializer");
/*     */       }
/*  57 */       return null;
/*     */     }
/*     */     
/*  60 */     Object localObject = instantiate(str2);
/*  61 */     if (localObject == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     return ((Serializers)localObject).findSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonDeserializer<?> findDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/*  71 */     Class localClass = paramJavaType.getRawClass();
/*  72 */     String str1 = localClass.getName();
/*     */     
/*     */     String str2;
/*  75 */     if ((str1.startsWith("javax.xml.")) || (hasSupertypeStartingWith(localClass, "javax.xml.")))
/*     */     {
/*  77 */       str2 = "com.shaded.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
/*  78 */     } else { if (doesImplement(localClass, "org.w3c.dom.Node"))
/*  79 */         return (JsonDeserializer)instantiate("com.shaded.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer");
/*  80 */       if (doesImplement(localClass, "org.w3c.dom.Node")) {
/*  81 */         return (JsonDeserializer)instantiate("com.shaded.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer");
/*     */       }
/*  83 */       return null;
/*     */     }
/*  85 */     Object localObject = instantiate(str2);
/*  86 */     if (localObject == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     return ((Deserializers)localObject).findBeanDeserializer(paramJavaType, paramDeserializationConfig, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object instantiate(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       return Class.forName(paramString).newInstance();
/*     */     }
/*     */     catch (LinkageError localLinkageError) {}catch (Exception localException) {}
/*     */     
/*     */ 
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private boolean doesImplement(Class<?> paramClass, String paramString)
/*     */   {
/* 111 */     for (Object localObject = paramClass; localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/* 112 */       if (((Class)localObject).getName().equals(paramString)) {
/* 113 */         return true;
/*     */       }
/*     */       
/* 116 */       if (hasInterface((Class)localObject, paramString)) {
/* 117 */         return true;
/*     */       }
/*     */     }
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   private boolean hasInterface(Class<?> paramClass, String paramString)
/*     */   {
/* 125 */     Class[] arrayOfClass1 = paramClass.getInterfaces();
/* 126 */     Class localClass; for (localClass : arrayOfClass1) {
/* 127 */       if (localClass.getName().equals(paramString)) {
/* 128 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 132 */     for (localClass : arrayOfClass1) {
/* 133 */       if (hasInterface(localClass, paramString)) {
/* 134 */         return true;
/*     */       }
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean hasSupertypeStartingWith(Class<?> paramClass, String paramString)
/*     */   {
/* 143 */     for (Object localObject = paramClass.getSuperclass(); localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/* 144 */       if (((Class)localObject).getName().startsWith(paramString)) {
/* 145 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 149 */     for (localObject = paramClass; localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/* 150 */       if (hasInterfaceStartingWith((Class)localObject, paramString)) {
/* 151 */         return true;
/*     */       }
/*     */     }
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   private boolean hasInterfaceStartingWith(Class<?> paramClass, String paramString)
/*     */   {
/* 159 */     Class[] arrayOfClass1 = paramClass.getInterfaces();
/* 160 */     Class localClass; for (localClass : arrayOfClass1) {
/* 161 */       if (localClass.getName().startsWith(paramString)) {
/* 162 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 166 */     for (localClass : arrayOfClass1) {
/* 167 */       if (hasInterfaceStartingWith(localClass, paramString)) {
/* 168 */         return true;
/*     */       }
/*     */     }
/* 171 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ext/OptionalHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */