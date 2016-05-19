/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectNode
/*     */   extends ContainerNode<ObjectNode>
/*     */ {
/*  27 */   private final Map<String, JsonNode> _children = new LinkedHashMap();
/*     */   
/*     */   public ObjectNode(JsonNodeFactory paramJsonNodeFactory) {
/*  30 */     super(paramJsonNodeFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode deepCopy()
/*     */   {
/*  40 */     ObjectNode localObjectNode = new ObjectNode(this._nodeFactory);
/*     */     
/*  42 */     for (Map.Entry localEntry : this._children.entrySet()) {
/*  43 */       localObjectNode._children.put(localEntry.getKey(), ((JsonNode)localEntry.getValue()).deepCopy());
/*     */     }
/*  45 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNodeType getNodeType()
/*     */   {
/*  57 */     return JsonNodeType.OBJECT;
/*     */   }
/*     */   
/*  60 */   public JsonToken asToken() { return JsonToken.START_OBJECT; }
/*     */   
/*     */   public int size()
/*     */   {
/*  64 */     return this._children.size();
/*     */   }
/*     */   
/*     */ 
/*     */   public Iterator<JsonNode> elements()
/*     */   {
/*  70 */     return this._children.values().iterator();
/*     */   }
/*     */   
/*     */   public JsonNode get(int paramInt) {
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   public JsonNode get(String paramString)
/*     */   {
/*  79 */     return (JsonNode)this._children.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public Iterator<String> fieldNames()
/*     */   {
/*  85 */     return this._children.keySet().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode path(int paramInt)
/*     */   {
/*  91 */     return MissingNode.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode path(String paramString)
/*     */   {
/*  97 */     JsonNode localJsonNode = (JsonNode)this._children.get(paramString);
/*  98 */     if (localJsonNode != null) {
/*  99 */       return localJsonNode;
/*     */     }
/* 101 */     return MissingNode.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<Map.Entry<String, JsonNode>> fields()
/*     */   {
/* 111 */     return this._children.entrySet().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectNode with(String paramString)
/*     */   {
/* 117 */     JsonNode localJsonNode = (JsonNode)this._children.get(paramString);
/* 118 */     if (localJsonNode != null) {
/* 119 */       if ((localJsonNode instanceof ObjectNode)) {
/* 120 */         return (ObjectNode)localJsonNode;
/*     */       }
/* 122 */       throw new UnsupportedOperationException("Property '" + paramString + "' has value that is not of type ObjectNode (but " + localJsonNode.getClass().getName() + ")");
/*     */     }
/*     */     
/*     */ 
/* 126 */     ObjectNode localObjectNode = objectNode();
/* 127 */     this._children.put(paramString, localObjectNode);
/* 128 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayNode withArray(String paramString)
/*     */   {
/* 134 */     JsonNode localJsonNode = (JsonNode)this._children.get(paramString);
/* 135 */     if (localJsonNode != null) {
/* 136 */       if ((localJsonNode instanceof ArrayNode)) {
/* 137 */         return (ArrayNode)localJsonNode;
/*     */       }
/* 139 */       throw new UnsupportedOperationException("Property '" + paramString + "' has value that is not of type ArrayNode (but " + localJsonNode.getClass().getName() + ")");
/*     */     }
/*     */     
/*     */ 
/* 143 */     ArrayNode localArrayNode = arrayNode();
/* 144 */     this._children.put(paramString, localArrayNode);
/* 145 */     return localArrayNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode findValue(String paramString)
/*     */   {
/* 157 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 158 */       if (paramString.equals(localEntry.getKey())) {
/* 159 */         return (JsonNode)localEntry.getValue();
/*     */       }
/* 161 */       JsonNode localJsonNode = ((JsonNode)localEntry.getValue()).findValue(paramString);
/* 162 */       if (localJsonNode != null) {
/* 163 */         return localJsonNode;
/*     */       }
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<JsonNode> findValues(String paramString, List<JsonNode> paramList)
/*     */   {
/* 172 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 173 */       if (paramString.equals(localEntry.getKey())) {
/* 174 */         if (paramList == null) {
/* 175 */           paramList = new ArrayList();
/*     */         }
/* 177 */         paramList.add(localEntry.getValue());
/*     */       } else {
/* 179 */         paramList = ((JsonNode)localEntry.getValue()).findValues(paramString, paramList);
/*     */       }
/*     */     }
/* 182 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> findValuesAsText(String paramString, List<String> paramList)
/*     */   {
/* 188 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 189 */       if (paramString.equals(localEntry.getKey())) {
/* 190 */         if (paramList == null) {
/* 191 */           paramList = new ArrayList();
/*     */         }
/* 193 */         paramList.add(((JsonNode)localEntry.getValue()).asText());
/*     */       } else {
/* 195 */         paramList = ((JsonNode)localEntry.getValue()).findValuesAsText(paramString, paramList);
/*     */       }
/*     */     }
/*     */     
/* 199 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectNode findParent(String paramString)
/*     */   {
/* 205 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 206 */       if (paramString.equals(localEntry.getKey())) {
/* 207 */         return this;
/*     */       }
/* 209 */       JsonNode localJsonNode = ((JsonNode)localEntry.getValue()).findParent(paramString);
/* 210 */       if (localJsonNode != null) {
/* 211 */         return (ObjectNode)localJsonNode;
/*     */       }
/*     */     }
/* 214 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<JsonNode> findParents(String paramString, List<JsonNode> paramList)
/*     */   {
/* 220 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 221 */       if (paramString.equals(localEntry.getKey())) {
/* 222 */         if (paramList == null) {
/* 223 */           paramList = new ArrayList();
/*     */         }
/* 225 */         paramList.add(this);
/*     */       } else {
/* 227 */         paramList = ((JsonNode)localEntry.getValue()).findParents(paramString, paramList);
/*     */       }
/*     */     }
/*     */     
/* 231 */     return paramList;
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
/*     */   public void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 248 */     paramJsonGenerator.writeStartObject();
/* 249 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 250 */       paramJsonGenerator.writeFieldName((String)localEntry.getKey());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 256 */       ((BaseJsonNode)localEntry.getValue()).serialize(paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 258 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 266 */     paramTypeSerializer.writeTypePrefixForObject(this, paramJsonGenerator);
/* 267 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 268 */       paramJsonGenerator.writeFieldName((String)localEntry.getKey());
/* 269 */       ((BaseJsonNode)localEntry.getValue()).serialize(paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 271 */     paramTypeSerializer.writeTypeSuffixForObject(this, paramJsonGenerator);
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
/*     */   public JsonNode set(String paramString, JsonNode paramJsonNode)
/*     */   {
/* 298 */     if (paramJsonNode == null) {
/* 299 */       paramJsonNode = nullNode();
/*     */     }
/* 301 */     this._children.put(paramString, paramJsonNode);
/* 302 */     return this;
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
/*     */   public JsonNode setAll(Map<String, JsonNode> paramMap)
/*     */   {
/* 317 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 318 */       Object localObject = (JsonNode)localEntry.getValue();
/* 319 */       if (localObject == null) {
/* 320 */         localObject = nullNode();
/*     */       }
/* 322 */       this._children.put(localEntry.getKey(), localObject);
/*     */     }
/* 324 */     return this;
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
/*     */   public JsonNode setAll(ObjectNode paramObjectNode)
/*     */   {
/* 339 */     this._children.putAll(paramObjectNode._children);
/* 340 */     return this;
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
/*     */ 
/*     */   public JsonNode replace(String paramString, JsonNode paramJsonNode)
/*     */   {
/* 357 */     if (paramJsonNode == null) {
/* 358 */       paramJsonNode = nullNode();
/*     */     }
/* 360 */     return (JsonNode)this._children.put(paramString, paramJsonNode);
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
/*     */   public JsonNode without(String paramString)
/*     */   {
/* 373 */     this._children.remove(paramString);
/* 374 */     return this;
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
/*     */   public ObjectNode without(Collection<String> paramCollection)
/*     */   {
/* 389 */     this._children.keySet().removeAll(paramCollection);
/* 390 */     return this;
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
/*     */   public JsonNode put(String paramString, JsonNode paramJsonNode)
/*     */   {
/* 416 */     if (paramJsonNode == null) {
/* 417 */       paramJsonNode = nullNode();
/*     */     }
/* 419 */     return (JsonNode)this._children.put(paramString, paramJsonNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode remove(String paramString)
/*     */   {
/* 431 */     return (JsonNode)this._children.remove(paramString);
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
/*     */   public ObjectNode remove(Collection<String> paramCollection)
/*     */   {
/* 444 */     this._children.keySet().removeAll(paramCollection);
/* 445 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode removeAll()
/*     */   {
/* 457 */     this._children.clear();
/* 458 */     return this;
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
/*     */   public JsonNode putAll(Map<String, JsonNode> paramMap)
/*     */   {
/* 473 */     return setAll(paramMap);
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
/*     */   public JsonNode putAll(ObjectNode paramObjectNode)
/*     */   {
/* 488 */     return setAll(paramObjectNode);
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
/*     */   public ObjectNode retain(Collection<String> paramCollection)
/*     */   {
/* 501 */     this._children.keySet().retainAll(paramCollection);
/* 502 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode retain(String... paramVarArgs)
/*     */   {
/* 514 */     return retain(Arrays.asList(paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode putArray(String paramString)
/*     */   {
/* 536 */     ArrayNode localArrayNode = arrayNode();
/* 537 */     this._children.put(paramString, localArrayNode);
/* 538 */     return localArrayNode;
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
/*     */   public ObjectNode putObject(String paramString)
/*     */   {
/* 554 */     ObjectNode localObjectNode = objectNode();
/* 555 */     this._children.put(paramString, localObjectNode);
/* 556 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ObjectNode putPOJO(String paramString, Object paramObject)
/*     */   {
/* 563 */     this._children.put(paramString, pojoNode(paramObject));
/* 564 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode putNull(String paramString)
/*     */   {
/* 572 */     this._children.put(paramString, nullNode());
/* 573 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, short paramShort)
/*     */   {
/* 582 */     this._children.put(paramString, numberNode(paramShort));
/* 583 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Short paramShort)
/*     */   {
/* 593 */     if (paramShort == null) {
/* 594 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 596 */       this._children.put(paramString, numberNode(paramShort.shortValue()));
/*     */     }
/* 598 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, int paramInt)
/*     */   {
/* 607 */     this._children.put(paramString, numberNode(paramInt));
/* 608 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Integer paramInteger)
/*     */   {
/* 618 */     if (paramInteger == null) {
/* 619 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 621 */       this._children.put(paramString, numberNode(paramInteger.intValue()));
/*     */     }
/* 623 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, long paramLong)
/*     */   {
/* 632 */     this._children.put(paramString, numberNode(paramLong));
/* 633 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Long paramLong)
/*     */   {
/* 643 */     if (paramLong == null) {
/* 644 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 646 */       this._children.put(paramString, numberNode(paramLong.longValue()));
/*     */     }
/* 648 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, float paramFloat)
/*     */   {
/* 657 */     this._children.put(paramString, numberNode(paramFloat));
/* 658 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Float paramFloat)
/*     */   {
/* 668 */     if (paramFloat == null) {
/* 669 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 671 */       this._children.put(paramString, numberNode(paramFloat.floatValue()));
/*     */     }
/* 673 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, double paramDouble)
/*     */   {
/* 682 */     this._children.put(paramString, numberNode(paramDouble));
/* 683 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Double paramDouble)
/*     */   {
/* 693 */     if (paramDouble == null) {
/* 694 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 696 */       this._children.put(paramString, numberNode(paramDouble.doubleValue()));
/*     */     }
/* 698 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, BigDecimal paramBigDecimal)
/*     */   {
/* 707 */     if (paramBigDecimal == null) {
/* 708 */       putNull(paramString);
/*     */     } else {
/* 710 */       this._children.put(paramString, numberNode(paramBigDecimal));
/*     */     }
/* 712 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString1, String paramString2)
/*     */   {
/* 721 */     if (paramString2 == null) {
/* 722 */       putNull(paramString1);
/*     */     } else {
/* 724 */       this._children.put(paramString1, textNode(paramString2));
/*     */     }
/* 726 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, boolean paramBoolean)
/*     */   {
/* 735 */     this._children.put(paramString, booleanNode(paramBoolean));
/* 736 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, Boolean paramBoolean)
/*     */   {
/* 746 */     if (paramBoolean == null) {
/* 747 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 749 */       this._children.put(paramString, booleanNode(paramBoolean.booleanValue()));
/*     */     }
/* 751 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode put(String paramString, byte[] paramArrayOfByte)
/*     */   {
/* 760 */     if (paramArrayOfByte == null) {
/* 761 */       this._children.put(paramString, nullNode());
/*     */     } else {
/* 763 */       this._children.put(paramString, binaryNode(paramArrayOfByte));
/*     */     }
/* 765 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 777 */     if (paramObject == this) return true;
/* 778 */     if (paramObject == null) { return false;
/*     */     }
/*     */     
/* 781 */     if (getClass() != paramObject.getClass()) {
/* 782 */       return false;
/*     */     }
/* 784 */     return this._children.equals(((ObjectNode)paramObject)._children);
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 790 */     return this._children.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 796 */     StringBuilder localStringBuilder = new StringBuilder(32 + (size() << 4));
/* 797 */     localStringBuilder.append("{");
/* 798 */     int i = 0;
/* 799 */     for (Map.Entry localEntry : this._children.entrySet()) {
/* 800 */       if (i > 0) {
/* 801 */         localStringBuilder.append(",");
/*     */       }
/* 803 */       i++;
/* 804 */       TextNode.appendQuoted(localStringBuilder, (String)localEntry.getKey());
/* 805 */       localStringBuilder.append(':');
/* 806 */       localStringBuilder.append(((JsonNode)localEntry.getValue()).toString());
/*     */     }
/* 808 */     localStringBuilder.append("}");
/* 809 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/ObjectNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */