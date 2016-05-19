/*    */ package com.shaded.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
/*    */ import java.io.StringReader;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DOMDeserializer<T>
/*    */   extends FromStringDeserializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 25 */   private static final DocumentBuilderFactory _parserFactory = ;
/*    */   
/* 27 */   static { _parserFactory.setNamespaceAware(true); }
/*    */   
/*    */   protected DOMDeserializer(Class<T> paramClass) {
/* 30 */     super(paramClass);
/*    */   }
/*    */   
/*    */   protected final Document parse(String paramString)
/*    */     throws IllegalArgumentException
/*    */   {
/*    */     try
/*    */     {
/* 38 */       return _parserFactory.newDocumentBuilder().parse(new InputSource(new StringReader(paramString)));
/*    */     } catch (Exception localException) {
/* 40 */       throw new IllegalArgumentException("Failed to parse JSON String as XML: " + localException.getMessage(), localException);
/*    */     }
/*    */   }
/*    */   
/*    */   public abstract T _deserialize(String paramString, DeserializationContext paramDeserializationContext);
/*    */   
/*    */   public static class NodeDeserializer
/*    */     extends DOMDeserializer<Node>
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public NodeDeserializer()
/*    */     {
/* 53 */       super();
/*    */     }
/*    */     
/* 56 */     public Node _deserialize(String paramString, DeserializationContext paramDeserializationContext) throws IllegalArgumentException { return parse(paramString); }
/*    */   }
/*    */   
/*    */   public static class DocumentDeserializer extends DOMDeserializer<Document> {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public DocumentDeserializer() {
/* 63 */       super();
/*    */     }
/*    */     
/* 66 */     public Document _deserialize(String paramString, DeserializationContext paramDeserializationContext) throws IllegalArgumentException { return parse(paramString); }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ext/DOMDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */