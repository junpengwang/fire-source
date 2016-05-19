/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InetAddressSerializer
/*    */   extends StdScalarSerializer<InetAddress>
/*    */ {
/* 18 */   public static final InetAddressSerializer instance = new InetAddressSerializer();
/*    */   
/* 20 */   public InetAddressSerializer() { super(InetAddress.class); }
/*    */   
/*    */ 
/*    */ 
/*    */   public void serialize(InetAddress paramInetAddress, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 27 */     String str = paramInetAddress.toString().trim();
/* 28 */     int i = str.indexOf('/');
/* 29 */     if (i >= 0) {
/* 30 */       if (i == 0) {
/* 31 */         str = str.substring(1);
/*    */       } else {
/* 33 */         str = str.substring(0, i);
/*    */       }
/*    */     }
/* 36 */     paramJsonGenerator.writeString(str);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void serializeWithType(InetAddress paramInetAddress, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 45 */     paramTypeSerializer.writeTypePrefixForScalar(paramInetAddress, paramJsonGenerator, InetAddress.class);
/* 46 */     serialize(paramInetAddress, paramJsonGenerator, paramSerializerProvider);
/* 47 */     paramTypeSerializer.writeTypeSuffixForScalar(paramInetAddress, paramJsonGenerator);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/InetAddressSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */