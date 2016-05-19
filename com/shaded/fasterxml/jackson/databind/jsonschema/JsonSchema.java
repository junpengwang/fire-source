/*    */ package com.shaded.fasterxml.jackson.databind.jsonschema;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonCreator;
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonValue;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*    */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
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
/*    */ @Deprecated
/*    */ public class JsonSchema
/*    */ {
/*    */   private final ObjectNode schema;
/*    */   
/*    */   @JsonCreator
/*    */   public JsonSchema(ObjectNode paramObjectNode)
/*    */   {
/* 38 */     this.schema = paramObjectNode;
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
/*    */   @JsonValue
/*    */   public ObjectNode getSchemaNode()
/*    */   {
/* 53 */     return this.schema;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 59 */     return this.schema.toString();
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 65 */     return this.schema.hashCode();
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 71 */     if (paramObject == this) return true;
/* 72 */     if (paramObject == null) return false;
/* 73 */     if (!(paramObject instanceof JsonSchema)) { return false;
/*    */     }
/* 75 */     JsonSchema localJsonSchema = (JsonSchema)paramObject;
/* 76 */     if (this.schema == null) {
/* 77 */       return localJsonSchema.schema == null;
/*    */     }
/* 79 */     return this.schema.equals(localJsonSchema.schema);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static JsonNode getDefaultSchemaNode()
/*    */   {
/* 89 */     ObjectNode localObjectNode = JsonNodeFactory.instance.objectNode();
/* 90 */     localObjectNode.put("type", "any");
/*    */     
/*    */ 
/* 93 */     return localObjectNode;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonschema/JsonSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */