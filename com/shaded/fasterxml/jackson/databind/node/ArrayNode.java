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
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArrayNode
/*     */   extends ContainerNode<ArrayNode>
/*     */ {
/*  23 */   private final List<JsonNode> _children = new ArrayList();
/*     */   
/*  25 */   public ArrayNode(JsonNodeFactory paramJsonNodeFactory) { super(paramJsonNodeFactory); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode deepCopy()
/*     */   {
/*  32 */     ArrayNode localArrayNode = new ArrayNode(this._nodeFactory);
/*     */     
/*  34 */     for (JsonNode localJsonNode : this._children) {
/*  35 */       localArrayNode._children.add(localJsonNode.deepCopy());
/*     */     }
/*  37 */     return localArrayNode;
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
/*  49 */     return JsonNodeType.ARRAY;
/*     */   }
/*     */   
/*  52 */   public JsonToken asToken() { return JsonToken.START_ARRAY; }
/*     */   
/*     */ 
/*     */   public int size()
/*     */   {
/*  57 */     return this._children.size();
/*     */   }
/*     */   
/*     */ 
/*     */   public Iterator<JsonNode> elements()
/*     */   {
/*  63 */     return this._children.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode get(int paramInt)
/*     */   {
/*  69 */     if ((paramInt >= 0) && (paramInt < this._children.size())) {
/*  70 */       return (JsonNode)this._children.get(paramInt);
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public JsonNode get(String paramString) {
/*  76 */     return null;
/*     */   }
/*     */   
/*  79 */   public JsonNode path(String paramString) { return MissingNode.getInstance(); }
/*     */   
/*     */ 
/*     */   public JsonNode path(int paramInt)
/*     */   {
/*  84 */     if ((paramInt >= 0) && (paramInt < this._children.size())) {
/*  85 */       return (JsonNode)this._children.get(paramInt);
/*     */     }
/*  87 */     return MissingNode.getInstance();
/*     */   }
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
/* 100 */     paramJsonGenerator.writeStartArray();
/* 101 */     for (JsonNode localJsonNode : this._children)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */       ((BaseJsonNode)localJsonNode).serialize(paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 109 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 117 */     paramTypeSerializer.writeTypePrefixForArray(this, paramJsonGenerator);
/* 118 */     for (JsonNode localJsonNode : this._children) {
/* 119 */       ((BaseJsonNode)localJsonNode).serialize(paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 121 */     paramTypeSerializer.writeTypeSuffixForArray(this, paramJsonGenerator);
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
/* 133 */     for (JsonNode localJsonNode1 : this._children) {
/* 134 */       JsonNode localJsonNode2 = localJsonNode1.findValue(paramString);
/* 135 */       if (localJsonNode2 != null) {
/* 136 */         return localJsonNode2;
/*     */       }
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<JsonNode> findValues(String paramString, List<JsonNode> paramList)
/*     */   {
/* 145 */     for (JsonNode localJsonNode : this._children) {
/* 146 */       paramList = localJsonNode.findValues(paramString, paramList);
/*     */     }
/* 148 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> findValuesAsText(String paramString, List<String> paramList)
/*     */   {
/* 154 */     for (JsonNode localJsonNode : this._children) {
/* 155 */       paramList = localJsonNode.findValuesAsText(paramString, paramList);
/*     */     }
/* 157 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectNode findParent(String paramString)
/*     */   {
/* 163 */     for (JsonNode localJsonNode1 : this._children) {
/* 164 */       JsonNode localJsonNode2 = localJsonNode1.findParent(paramString);
/* 165 */       if (localJsonNode2 != null) {
/* 166 */         return (ObjectNode)localJsonNode2;
/*     */       }
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<JsonNode> findParents(String paramString, List<JsonNode> paramList)
/*     */   {
/* 175 */     for (JsonNode localJsonNode : this._children) {
/* 176 */       paramList = localJsonNode.findParents(paramString, paramList);
/*     */     }
/* 178 */     return paramList;
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
/*     */   public JsonNode set(int paramInt, JsonNode paramJsonNode)
/*     */   {
/* 200 */     if (paramJsonNode == null) {
/* 201 */       paramJsonNode = nullNode();
/*     */     }
/* 203 */     if ((paramInt < 0) || (paramInt >= this._children.size())) {
/* 204 */       throw new IndexOutOfBoundsException("Illegal index " + paramInt + ", array size " + size());
/*     */     }
/* 206 */     return (JsonNode)this._children.set(paramInt, paramJsonNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(JsonNode paramJsonNode)
/*     */   {
/* 216 */     if (paramJsonNode == null) {
/* 217 */       paramJsonNode = nullNode();
/*     */     }
/* 219 */     _add(paramJsonNode);
/* 220 */     return this;
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
/*     */   public ArrayNode addAll(ArrayNode paramArrayNode)
/*     */   {
/* 233 */     this._children.addAll(paramArrayNode._children);
/* 234 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode addAll(Collection<JsonNode> paramCollection)
/*     */   {
/* 246 */     this._children.addAll(paramCollection);
/* 247 */     return this;
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
/*     */   public ArrayNode insert(int paramInt, JsonNode paramJsonNode)
/*     */   {
/* 261 */     if (paramJsonNode == null) {
/* 262 */       paramJsonNode = nullNode();
/*     */     }
/* 264 */     _insert(paramInt, paramJsonNode);
/* 265 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode remove(int paramInt)
/*     */   {
/* 277 */     if ((paramInt >= 0) && (paramInt < this._children.size())) {
/* 278 */       return (JsonNode)this._children.remove(paramInt);
/*     */     }
/* 280 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode removeAll()
/*     */   {
/* 292 */     this._children.clear();
/* 293 */     return this;
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
/*     */   public ArrayNode addArray()
/*     */   {
/* 310 */     ArrayNode localArrayNode = arrayNode();
/* 311 */     _add(localArrayNode);
/* 312 */     return localArrayNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode addObject()
/*     */   {
/* 323 */     ObjectNode localObjectNode = objectNode();
/* 324 */     _add(localObjectNode);
/* 325 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode addPOJO(Object paramObject)
/*     */   {
/* 336 */     if (paramObject == null) {
/* 337 */       addNull();
/*     */     } else {
/* 339 */       _add(pojoNode(paramObject));
/*     */     }
/* 341 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode addNull()
/*     */   {
/* 351 */     _add(nullNode());
/* 352 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(int paramInt)
/*     */   {
/* 361 */     _add(numberNode(paramInt));
/* 362 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(Integer paramInteger)
/*     */   {
/* 372 */     if (paramInteger == null) {
/* 373 */       return addNull();
/*     */     }
/* 375 */     return _add(numberNode(paramInteger.intValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(long paramLong)
/*     */   {
/* 383 */     return _add(numberNode(paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(Long paramLong)
/*     */   {
/* 392 */     if (paramLong == null) {
/* 393 */       return addNull();
/*     */     }
/* 395 */     return _add(numberNode(paramLong.longValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(float paramFloat)
/*     */   {
/* 404 */     return _add(numberNode(paramFloat));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(Float paramFloat)
/*     */   {
/* 414 */     if (paramFloat == null) {
/* 415 */       return addNull();
/*     */     }
/* 417 */     return _add(numberNode(paramFloat.floatValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(double paramDouble)
/*     */   {
/* 426 */     return _add(numberNode(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(Double paramDouble)
/*     */   {
/* 436 */     if (paramDouble == null) {
/* 437 */       return addNull();
/*     */     }
/* 439 */     return _add(numberNode(paramDouble.doubleValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(BigDecimal paramBigDecimal)
/*     */   {
/* 448 */     if (paramBigDecimal == null) {
/* 449 */       return addNull();
/*     */     }
/* 451 */     return _add(numberNode(paramBigDecimal));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(String paramString)
/*     */   {
/* 460 */     if (paramString == null) {
/* 461 */       return addNull();
/*     */     }
/* 463 */     return _add(textNode(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(boolean paramBoolean)
/*     */   {
/* 472 */     return _add(booleanNode(paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(Boolean paramBoolean)
/*     */   {
/* 482 */     if (paramBoolean == null) {
/* 483 */       return addNull();
/*     */     }
/* 485 */     return _add(booleanNode(paramBoolean.booleanValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode add(byte[] paramArrayOfByte)
/*     */   {
/* 495 */     if (paramArrayOfByte == null) {
/* 496 */       return addNull();
/*     */     }
/* 498 */     return _add(binaryNode(paramArrayOfByte));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insertArray(int paramInt)
/*     */   {
/* 509 */     ArrayNode localArrayNode = arrayNode();
/* 510 */     _insert(paramInt, localArrayNode);
/* 511 */     return localArrayNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectNode insertObject(int paramInt)
/*     */   {
/* 523 */     ObjectNode localObjectNode = objectNode();
/* 524 */     _insert(paramInt, localObjectNode);
/* 525 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insertPOJO(int paramInt, Object paramObject)
/*     */   {
/* 536 */     if (paramObject == null) {
/* 537 */       return insertNull(paramInt);
/*     */     }
/* 539 */     return _insert(paramInt, pojoNode(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insertNull(int paramInt)
/*     */   {
/* 550 */     _insert(paramInt, nullNode());
/* 551 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt1, int paramInt2)
/*     */   {
/* 561 */     _insert(paramInt1, numberNode(paramInt2));
/* 562 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, Integer paramInteger)
/*     */   {
/* 572 */     if (paramInteger == null) {
/* 573 */       insertNull(paramInt);
/*     */     } else {
/* 575 */       _insert(paramInt, numberNode(paramInteger.intValue()));
/*     */     }
/* 577 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, long paramLong)
/*     */   {
/* 587 */     return _insert(paramInt, numberNode(paramLong));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, Long paramLong)
/*     */   {
/* 597 */     if (paramLong == null) {
/* 598 */       return insertNull(paramInt);
/*     */     }
/* 600 */     return _insert(paramInt, numberNode(paramLong.longValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, float paramFloat)
/*     */   {
/* 610 */     return _insert(paramInt, numberNode(paramFloat));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, Float paramFloat)
/*     */   {
/* 620 */     if (paramFloat == null) {
/* 621 */       return insertNull(paramInt);
/*     */     }
/* 623 */     return _insert(paramInt, numberNode(paramFloat.floatValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, double paramDouble)
/*     */   {
/* 633 */     return _insert(paramInt, numberNode(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, Double paramDouble)
/*     */   {
/* 643 */     if (paramDouble == null) {
/* 644 */       return insertNull(paramInt);
/*     */     }
/* 646 */     return _insert(paramInt, numberNode(paramDouble.doubleValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, BigDecimal paramBigDecimal)
/*     */   {
/* 656 */     if (paramBigDecimal == null) {
/* 657 */       return insertNull(paramInt);
/*     */     }
/* 659 */     return _insert(paramInt, numberNode(paramBigDecimal));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, String paramString)
/*     */   {
/* 669 */     if (paramString == null) {
/* 670 */       return insertNull(paramInt);
/*     */     }
/* 672 */     return _insert(paramInt, textNode(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, boolean paramBoolean)
/*     */   {
/* 682 */     return _insert(paramInt, booleanNode(paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, Boolean paramBoolean)
/*     */   {
/* 692 */     if (paramBoolean == null) {
/* 693 */       return insertNull(paramInt);
/*     */     }
/* 695 */     return _insert(paramInt, booleanNode(paramBoolean.booleanValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayNode insert(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 706 */     if (paramArrayOfByte == null) {
/* 707 */       return insertNull(paramInt);
/*     */     }
/* 709 */     return _insert(paramInt, binaryNode(paramArrayOfByte));
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
/* 721 */     if (paramObject == this) return true;
/* 722 */     if (paramObject == null) return false;
/* 723 */     if (getClass() != paramObject.getClass()) {
/* 724 */       return false;
/*     */     }
/* 726 */     return this._children.equals(((ArrayNode)paramObject)._children);
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 732 */     return this._children.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 739 */     StringBuilder localStringBuilder = new StringBuilder(16 + (size() << 4));
/* 740 */     localStringBuilder.append('[');
/* 741 */     int i = 0; for (int j = this._children.size(); i < j; i++) {
/* 742 */       if (i > 0) {
/* 743 */         localStringBuilder.append(',');
/*     */       }
/* 745 */       localStringBuilder.append(((JsonNode)this._children.get(i)).toString());
/*     */     }
/* 747 */     localStringBuilder.append(']');
/* 748 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayNode _add(JsonNode paramJsonNode)
/*     */   {
/* 759 */     this._children.add(paramJsonNode);
/* 760 */     return this;
/*     */   }
/*     */   
/*     */   private ArrayNode _insert(int paramInt, JsonNode paramJsonNode)
/*     */   {
/* 765 */     if (paramInt < 0) {
/* 766 */       this._children.add(0, paramJsonNode);
/* 767 */     } else if (paramInt >= this._children.size()) {
/* 768 */       this._children.add(paramJsonNode);
/*     */     } else {
/* 770 */       this._children.add(paramInt, paramJsonNode);
/*     */     }
/* 772 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/ArrayNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */