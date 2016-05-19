/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ 
/*    */ public class TimeZoneSerializer
/*    */   extends StdScalarSerializer<TimeZone>
/*    */ {
/* 14 */   public static final TimeZoneSerializer instance = new TimeZoneSerializer();
/*    */   
/* 16 */   public TimeZoneSerializer() { super(TimeZone.class); }
/*    */   
/*    */ 
/*    */   public void serialize(TimeZone paramTimeZone, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 22 */     paramJsonGenerator.writeString(paramTimeZone.getID());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void serializeWithType(TimeZone paramTimeZone, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 31 */     paramTypeSerializer.writeTypePrefixForScalar(paramTimeZone, paramJsonGenerator, TimeZone.class);
/* 32 */     serialize(paramTimeZone, paramJsonGenerator, paramSerializerProvider);
/* 33 */     paramTypeSerializer.writeTypeSuffixForScalar(paramTimeZone, paramJsonGenerator);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/TimeZoneSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */