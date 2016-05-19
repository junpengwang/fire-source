/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import java.util.Iterator;
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
/*     */ abstract class NodeCursor
/*     */   extends JsonStreamContext
/*     */ {
/*     */   protected final NodeCursor _parent;
/*     */   protected String _currentName;
/*     */   
/*     */   public NodeCursor(int paramInt, NodeCursor paramNodeCursor)
/*     */   {
/*  30 */     this._type = paramInt;
/*  31 */     this._index = -1;
/*  32 */     this._parent = paramNodeCursor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final NodeCursor getParent()
/*     */   {
/*  43 */     return this._parent;
/*     */   }
/*     */   
/*     */   public final String getCurrentName() {
/*  47 */     return this._currentName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void overrideCurrentName(String paramString)
/*     */   {
/*  54 */     this._currentName = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract JsonToken nextToken();
/*     */   
/*     */ 
/*     */   public abstract JsonToken nextValue();
/*     */   
/*     */ 
/*     */   public abstract JsonToken endToken();
/*     */   
/*     */ 
/*     */   public abstract JsonNode currentNode();
/*     */   
/*     */ 
/*     */   public abstract boolean currentHasChildren();
/*     */   
/*     */ 
/*     */   public final NodeCursor iterateChildren()
/*     */   {
/*  75 */     JsonNode localJsonNode = currentNode();
/*  76 */     if (localJsonNode == null) throw new IllegalStateException("No current node");
/*  77 */     if (localJsonNode.isArray()) {
/*  78 */       return new Array(localJsonNode, this);
/*     */     }
/*  80 */     if (localJsonNode.isObject()) {
/*  81 */       return new Object(localJsonNode, this);
/*     */     }
/*  83 */     throw new IllegalStateException("Current node of type " + localJsonNode.getClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static final class RootValue
/*     */     extends NodeCursor
/*     */   {
/*     */     protected JsonNode _node;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */     protected boolean _done = false;
/*     */     
/*     */     public RootValue(JsonNode paramJsonNode, NodeCursor paramNodeCursor) {
/* 105 */       super(paramNodeCursor);
/* 106 */       this._node = paramJsonNode;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void overrideCurrentName(String paramString) {}
/*     */     
/*     */ 
/*     */     public JsonToken nextToken()
/*     */     {
/* 116 */       if (!this._done) {
/* 117 */         this._done = true;
/* 118 */         return this._node.asToken();
/*     */       }
/* 120 */       this._node = null;
/* 121 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 125 */     public JsonToken nextValue() { return nextToken(); }
/*     */     
/* 127 */     public JsonToken endToken() { return null; }
/*     */     
/* 129 */     public JsonNode currentNode() { return this._node; }
/*     */     
/* 131 */     public boolean currentHasChildren() { return false; }
/*     */   }
/*     */   
/*     */ 
/*     */   protected static final class Array
/*     */     extends NodeCursor
/*     */   {
/*     */     protected Iterator<JsonNode> _contents;
/*     */     
/*     */     protected JsonNode _currentNode;
/*     */     
/*     */ 
/*     */     public Array(JsonNode paramJsonNode, NodeCursor paramNodeCursor)
/*     */     {
/* 145 */       super(paramNodeCursor);
/* 146 */       this._contents = paramJsonNode.elements();
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonToken nextToken()
/*     */     {
/* 152 */       if (!this._contents.hasNext()) {
/* 153 */         this._currentNode = null;
/* 154 */         return null;
/*     */       }
/* 156 */       this._currentNode = ((JsonNode)this._contents.next());
/* 157 */       return this._currentNode.asToken();
/*     */     }
/*     */     
/*     */ 
/* 161 */     public JsonToken nextValue() { return nextToken(); }
/*     */     
/* 163 */     public JsonToken endToken() { return JsonToken.END_ARRAY; }
/*     */     
/*     */     public JsonNode currentNode() {
/* 166 */       return this._currentNode;
/*     */     }
/*     */     
/*     */     public boolean currentHasChildren() {
/* 170 */       return ((ContainerNode)currentNode()).size() > 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected static final class Object
/*     */     extends NodeCursor
/*     */   {
/*     */     protected Iterator<Map.Entry<String, JsonNode>> _contents;
/*     */     
/*     */     protected Map.Entry<String, JsonNode> _current;
/*     */     
/*     */     protected boolean _needEntry;
/*     */     
/*     */ 
/*     */     public Object(JsonNode paramJsonNode, NodeCursor paramNodeCursor)
/*     */     {
/* 187 */       super(paramNodeCursor);
/* 188 */       this._contents = ((ObjectNode)paramJsonNode).fields();
/* 189 */       this._needEntry = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public JsonToken nextToken()
/*     */     {
/* 196 */       if (this._needEntry) {
/* 197 */         if (!this._contents.hasNext()) {
/* 198 */           this._currentName = null;
/* 199 */           this._current = null;
/* 200 */           return null;
/*     */         }
/* 202 */         this._needEntry = false;
/* 203 */         this._current = ((Map.Entry)this._contents.next());
/* 204 */         this._currentName = (this._current == null ? null : (String)this._current.getKey());
/* 205 */         return JsonToken.FIELD_NAME;
/*     */       }
/* 207 */       this._needEntry = true;
/* 208 */       return ((JsonNode)this._current.getValue()).asToken();
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonToken nextValue()
/*     */     {
/* 214 */       JsonToken localJsonToken = nextToken();
/* 215 */       if (localJsonToken == JsonToken.FIELD_NAME) {
/* 216 */         localJsonToken = nextToken();
/*     */       }
/* 218 */       return localJsonToken;
/*     */     }
/*     */     
/*     */     public JsonToken endToken() {
/* 222 */       return JsonToken.END_OBJECT;
/*     */     }
/*     */     
/*     */     public JsonNode currentNode() {
/* 226 */       return this._current == null ? null : (JsonNode)this._current.getValue();
/*     */     }
/*     */     
/*     */     public boolean currentHasChildren()
/*     */     {
/* 231 */       return ((ContainerNode)currentNode()).size() > 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/NodeCursor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */