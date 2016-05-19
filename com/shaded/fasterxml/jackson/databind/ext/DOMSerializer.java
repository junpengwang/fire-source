/*    */ package com.shaded.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.bootstrap.DOMImplementationRegistry;
/*    */ import org.w3c.dom.ls.DOMImplementationLS;
/*    */ import org.w3c.dom.ls.LSSerializer;
/*    */ 
/*    */ public class DOMSerializer
/*    */   extends StdSerializer<Node>
/*    */ {
/*    */   protected final DOMImplementationLS _domImpl;
/*    */   
/*    */   public DOMSerializer()
/*    */   {
/* 25 */     super(Node.class);
/*    */     DOMImplementationRegistry localDOMImplementationRegistry;
/*    */     try {
/* 28 */       localDOMImplementationRegistry = DOMImplementationRegistry.newInstance();
/*    */     } catch (Exception localException) {
/* 30 */       throw new IllegalStateException("Could not instantiate DOMImplementationRegistry: " + localException.getMessage(), localException);
/*    */     }
/* 32 */     this._domImpl = ((DOMImplementationLS)localDOMImplementationRegistry.getDOMImplementation("LS"));
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(Node paramNode, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 39 */     if (this._domImpl == null) throw new IllegalStateException("Could not find DOM LS");
/* 40 */     LSSerializer localLSSerializer = this._domImpl.createLSSerializer();
/* 41 */     paramJsonGenerator.writeString(localLSSerializer.writeToString(paramNode));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 48 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 55 */     if (paramJsonFormatVisitorWrapper != null) paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ext/DOMSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */