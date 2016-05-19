/*    */ package com.shaded.fasterxml.jackson.databind;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*    */ import com.shaded.fasterxml.jackson.core.format.InputAccessor;
/*    */ import com.shaded.fasterxml.jackson.core.format.MatchStrength;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MappingJsonFactory
/*    */   extends JsonFactory
/*    */ {
/*    */   private static final long serialVersionUID = -6744103724013275513L;
/*    */   
/*    */   public MappingJsonFactory()
/*    */   {
/* 39 */     this(null);
/*    */   }
/*    */   
/*    */   public MappingJsonFactory(ObjectMapper paramObjectMapper)
/*    */   {
/* 44 */     super(paramObjectMapper);
/* 45 */     if (paramObjectMapper == null) {
/* 46 */       setCodec(new ObjectMapper(this));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final ObjectMapper getCodec()
/*    */   {
/* 55 */     return (ObjectMapper)this._objectCodec;
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonFactory copy()
/*    */   {
/* 61 */     _checkInvalidCopy(MappingJsonFactory.class);
/*    */     
/* 63 */     return new MappingJsonFactory(null);
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getFormatName()
/*    */   {
/* 81 */     return "JSON";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public MatchStrength hasFormat(InputAccessor paramInputAccessor)
/*    */     throws IOException
/*    */   {
/* 90 */     if (getClass() == MappingJsonFactory.class) {
/* 91 */       return hasJSONFormat(paramInputAccessor);
/*    */     }
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/MappingJsonFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */