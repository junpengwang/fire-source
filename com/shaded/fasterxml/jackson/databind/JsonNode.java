/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.TreeNode;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeType;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EmptyIterator;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonNode
/*     */   implements TreeNode, Iterable<JsonNode>
/*     */ {
/*     */   public abstract <T extends JsonNode> T deepCopy();
/*     */   
/*     */   public int size()
/*     */   {
/*  74 */     return 0;
/*     */   }
/*     */   
/*     */   public final boolean isValueNode()
/*     */   {
/*  79 */     switch (getNodeType()) {
/*     */     case ARRAY: case OBJECT: case MISSING: 
/*  81 */       return false;
/*     */     }
/*  83 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public final boolean isContainerNode()
/*     */   {
/*  89 */     JsonNodeType localJsonNodeType = getNodeType();
/*  90 */     return (localJsonNodeType == JsonNodeType.OBJECT) || (localJsonNodeType == JsonNodeType.ARRAY);
/*     */   }
/*     */   
/*     */   public final boolean isMissingNode()
/*     */   {
/*  95 */     return getNodeType() == JsonNodeType.MISSING;
/*     */   }
/*     */   
/*     */   public final boolean isArray()
/*     */   {
/* 100 */     return getNodeType() == JsonNodeType.ARRAY;
/*     */   }
/*     */   
/*     */   public final boolean isObject()
/*     */   {
/* 105 */     return getNodeType() == JsonNodeType.OBJECT;
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
/*     */   public abstract JsonNode get(int paramInt);
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
/*     */   public JsonNode get(String paramString)
/*     */   {
/* 148 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JsonNode path(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JsonNode path(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<String> fieldNames()
/*     */   {
/* 175 */     return EmptyIterator.instance();
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
/*     */   public abstract JsonNodeType getNodeType();
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
/*     */   public final boolean isPojo()
/*     */   {
/* 204 */     return getNodeType() == JsonNodeType.POJO;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isNumber()
/*     */   {
/* 211 */     return getNodeType() == JsonNodeType.NUMBER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isIntegralNumber()
/*     */   {
/* 219 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFloatingPointNumber()
/*     */   {
/* 225 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isShort()
/*     */   {
/* 237 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInt()
/*     */   {
/* 249 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLong()
/*     */   {
/* 261 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 266 */   public boolean isFloat() { return false; }
/*     */   
/* 268 */   public boolean isDouble() { return false; }
/* 269 */   public boolean isBigDecimal() { return false; }
/* 270 */   public boolean isBigInteger() { return false; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isTextual()
/*     */   {
/* 277 */     return getNodeType() == JsonNodeType.STRING;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isBoolean()
/*     */   {
/* 285 */     return getNodeType() == JsonNodeType.BOOLEAN;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isNull()
/*     */   {
/* 293 */     return getNodeType() == JsonNodeType.NULL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isBinary()
/*     */   {
/* 305 */     return getNodeType() == JsonNodeType.BINARY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canConvertToInt()
/*     */   {
/* 317 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canConvertToLong()
/*     */   {
/* 328 */     return false;
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
/*     */   public String textValue()
/*     */   {
/* 346 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] binaryValue()
/*     */     throws IOException
/*     */   {
/* 359 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean booleanValue()
/*     */   {
/* 370 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Number numberValue()
/*     */   {
/* 380 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short shortValue()
/*     */   {
/* 392 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 404 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 416 */     return 0L;
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
/*     */   public float floatValue()
/*     */   {
/* 429 */     return 0.0F;
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
/* 442 */   public double doubleValue() { return 0.0D; }
/*     */   
/* 444 */   public BigDecimal decimalValue() { return BigDecimal.ZERO; }
/* 445 */   public BigInteger bigIntegerValue() { return BigInteger.ZERO; }
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
/*     */   public abstract String asText();
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
/*     */   public int asInt()
/*     */   {
/* 472 */     return asInt(0);
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
/*     */   public int asInt(int paramInt)
/*     */   {
/* 486 */     return paramInt;
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
/*     */   public long asLong()
/*     */   {
/* 500 */     return asLong(0L);
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
/*     */   public long asLong(long paramLong)
/*     */   {
/* 514 */     return paramLong;
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
/*     */   public double asDouble()
/*     */   {
/* 528 */     return asDouble(0.0D);
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
/*     */   public double asDouble(double paramDouble)
/*     */   {
/* 542 */     return paramDouble;
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
/*     */   public boolean asBoolean()
/*     */   {
/* 556 */     return asBoolean(false);
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
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/* 570 */     return paramBoolean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean has(String paramString)
/*     */   {
/* 600 */     return get(paramString) != null;
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
/*     */   public boolean has(int paramInt)
/*     */   {
/* 626 */     return get(paramInt) != null;
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
/*     */   public boolean hasNonNull(String paramString)
/*     */   {
/* 641 */     JsonNode localJsonNode = get(paramString);
/* 642 */     return (localJsonNode != null) && (!localJsonNode.isNull());
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
/*     */   public boolean hasNonNull(int paramInt)
/*     */   {
/* 657 */     JsonNode localJsonNode = get(paramInt);
/* 658 */     return (localJsonNode != null) && (!localJsonNode.isNull());
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
/*     */   public final Iterator<JsonNode> iterator()
/*     */   {
/* 673 */     return elements();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<JsonNode> elements()
/*     */   {
/* 682 */     return EmptyIterator.instance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<Map.Entry<String, JsonNode>> fields()
/*     */   {
/* 690 */     return EmptyIterator.instance();
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
/*     */   public abstract JsonNode findValue(String paramString);
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
/*     */   public final List<JsonNode> findValues(String paramString)
/*     */   {
/* 721 */     List localList = findValues(paramString, null);
/* 722 */     if (localList == null) {
/* 723 */       return Collections.emptyList();
/*     */     }
/* 725 */     return localList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final List<String> findValuesAsText(String paramString)
/*     */   {
/* 734 */     List localList = findValuesAsText(paramString, null);
/* 735 */     if (localList == null) {
/* 736 */       return Collections.emptyList();
/*     */     }
/* 738 */     return localList;
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
/*     */   public abstract JsonNode findPath(String paramString);
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
/*     */   public abstract JsonNode findParent(String paramString);
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
/*     */   public final List<JsonNode> findParents(String paramString)
/*     */   {
/* 777 */     List localList = findParents(paramString, null);
/* 778 */     if (localList == null) {
/* 779 */       return Collections.emptyList();
/*     */     }
/* 781 */     return localList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<JsonNode> findValues(String paramString, List<JsonNode> paramList);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<String> findValuesAsText(String paramString, List<String> paramList);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<JsonNode> findParents(String paramString, List<JsonNode> paramList);
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonNode with(String paramString)
/*     */   {
/* 803 */     throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), can not call with() on it");
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
/*     */   public JsonNode withArray(String paramString)
/*     */   {
/* 816 */     throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), can not call withArray() on it");
/*     */   }
/*     */   
/*     */   public abstract String toString();
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/JsonNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */