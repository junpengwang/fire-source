/*     */ package com.firebase.client.core.view;
/*     */ 
/*     */ import com.firebase.client.core.view.filter.IndexedFilter;
/*     */ import com.firebase.client.core.view.filter.LimitedFilter;
/*     */ import com.firebase.client.core.view.filter.NodeFilter;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.Index;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import com.firebase.client.snapshot.PriorityIndex;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class QueryParams
/*     */ {
/*     */   public static final QueryParams DEFAULT_PARAMS;
/*     */   private static final String INDEX_START_VALUE = "sp";
/*     */   private static final String INDEX_START_NAME = "sn";
/*     */   private static final String INDEX_END_VALUE = "ep";
/*     */   private static final String INDEX_END_NAME = "en";
/*     */   private static final String LIMIT = "l";
/*     */   private static final String VIEW_FROM = "vf";
/*     */   private static final String INDEX = "i";
/*     */   private static final ObjectMapper mapperInstance;
/*     */   private Integer limit;
/*     */   private ViewFrom viewFrom;
/*     */   
/*     */   private static enum ViewFrom
/*     */   {
/*  31 */     LEFT,  RIGHT;
/*     */     
/*     */     private ViewFrom() {}
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  20 */     DEFAULT_PARAMS = new QueryParams();
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
/*  36 */     mapperInstance = new ObjectMapper();
/*  37 */     mapperInstance.configure(com.shaded.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  42 */   private Node indexStartValue = null;
/*  43 */   private ChildKey indexStartName = null;
/*  44 */   private Node indexEndValue = null;
/*  45 */   private ChildKey indexEndName = null;
/*     */   
/*  47 */   private Index index = PriorityIndex.getInstance();
/*     */   
/*  49 */   private String jsonSerialization = null;
/*     */   
/*     */   public boolean hasStart() {
/*  52 */     return this.indexStartValue != null;
/*     */   }
/*     */   
/*     */   public Node getIndexStartValue() {
/*  56 */     if (!hasStart()) {
/*  57 */       throw new IllegalArgumentException("Cannot get index start value if start has not been set");
/*     */     }
/*  59 */     return this.indexStartValue;
/*     */   }
/*     */   
/*     */   public ChildKey getIndexStartName() {
/*  63 */     if (!hasStart()) {
/*  64 */       throw new IllegalArgumentException("Cannot get index start name if start has not been set");
/*     */     }
/*  66 */     if (this.indexStartName != null) {
/*  67 */       return this.indexStartName;
/*     */     }
/*  69 */     return ChildKey.getMinName();
/*     */   }
/*     */   
/*     */   public boolean hasEnd()
/*     */   {
/*  74 */     return this.indexEndValue != null;
/*     */   }
/*     */   
/*     */   public Node getIndexEndValue() {
/*  78 */     if (!hasEnd()) {
/*  79 */       throw new IllegalArgumentException("Cannot get index end value if start has not been set");
/*     */     }
/*  81 */     return this.indexEndValue;
/*     */   }
/*     */   
/*     */   public ChildKey getIndexEndName() {
/*  85 */     if (!hasEnd()) {
/*  86 */       throw new IllegalArgumentException("Cannot get index end name if start has not been set");
/*     */     }
/*  88 */     if (this.indexEndName != null) {
/*  89 */       return this.indexEndName;
/*     */     }
/*  91 */     return ChildKey.getMaxName();
/*     */   }
/*     */   
/*     */   public boolean hasLimit()
/*     */   {
/*  96 */     return this.limit != null;
/*     */   }
/*     */   
/*     */   public boolean hasAnchoredLimit() {
/* 100 */     return (hasLimit()) && (this.viewFrom != null);
/*     */   }
/*     */   
/*     */   public int getLimit() {
/* 104 */     if (!hasLimit()) {
/* 105 */       throw new IllegalArgumentException("Cannot get limit if limit has not been set");
/*     */     }
/* 107 */     return this.limit.intValue();
/*     */   }
/*     */   
/*     */   public Index getIndex() {
/* 111 */     return this.index;
/*     */   }
/*     */   
/*     */   private QueryParams copy() {
/* 115 */     QueryParams params = new QueryParams();
/* 116 */     params.limit = this.limit;
/* 117 */     params.indexStartValue = this.indexStartValue;
/* 118 */     params.indexStartName = this.indexStartName;
/* 119 */     params.indexEndValue = this.indexEndValue;
/* 120 */     params.indexEndName = this.indexEndName;
/* 121 */     params.viewFrom = this.viewFrom;
/* 122 */     params.index = this.index;
/* 123 */     return params;
/*     */   }
/*     */   
/*     */   public QueryParams limit(int limit) {
/* 127 */     QueryParams copy = copy();
/* 128 */     copy.limit = Integer.valueOf(limit);
/* 129 */     copy.viewFrom = null;
/* 130 */     return copy;
/*     */   }
/*     */   
/*     */   public QueryParams limitToFirst(int limit) {
/* 134 */     QueryParams copy = copy();
/* 135 */     copy.limit = Integer.valueOf(limit);
/* 136 */     copy.viewFrom = ViewFrom.LEFT;
/* 137 */     return copy;
/*     */   }
/*     */   
/*     */   public QueryParams limitToLast(int limit) {
/* 141 */     QueryParams copy = copy();
/* 142 */     copy.limit = Integer.valueOf(limit);
/* 143 */     copy.viewFrom = ViewFrom.RIGHT;
/* 144 */     return copy;
/*     */   }
/*     */   
/*     */   public QueryParams startAt(Node indexStartValue, ChildKey indexStartName) {
/* 148 */     assert ((indexStartValue.isLeafNode()) || (indexStartValue.isEmpty()));
/* 149 */     QueryParams copy = copy();
/* 150 */     copy.indexStartValue = indexStartValue;
/* 151 */     copy.indexStartName = indexStartName;
/* 152 */     return copy;
/*     */   }
/*     */   
/*     */   public QueryParams endAt(Node indexEndValue, ChildKey indexEndName) {
/* 156 */     assert ((indexEndValue.isLeafNode()) || (indexEndValue.isEmpty()));
/* 157 */     QueryParams copy = copy();
/* 158 */     copy.indexEndValue = indexEndValue;
/* 159 */     copy.indexEndName = indexEndName;
/* 160 */     return copy;
/*     */   }
/*     */   
/*     */   public QueryParams orderBy(Index index) {
/* 164 */     QueryParams copy = copy();
/* 165 */     copy.index = index;
/* 166 */     return copy;
/*     */   }
/*     */   
/*     */   public boolean isViewFromLeft() {
/* 170 */     return this.viewFrom != null ? false : this.viewFrom == ViewFrom.LEFT ? true : hasStart();
/*     */   }
/*     */   
/*     */   public Map<String, Object> getWireProtocolParams()
/*     */   {
/* 175 */     Map<String, Object> queryObject = new java.util.HashMap();
/* 176 */     if (hasStart()) {
/* 177 */       queryObject.put("sp", this.indexStartValue.getValue());
/* 178 */       if (this.indexStartName != null) {
/* 179 */         queryObject.put("sn", this.indexStartName.asString());
/*     */       }
/*     */     }
/* 182 */     if (hasEnd()) {
/* 183 */       queryObject.put("ep", this.indexEndValue.getValue());
/* 184 */       if (this.indexEndName != null) {
/* 185 */         queryObject.put("en", this.indexEndName.asString());
/*     */       }
/*     */     }
/* 188 */     if (this.limit != null) {
/* 189 */       queryObject.put("l", this.limit);
/* 190 */       ViewFrom viewFromToAdd = this.viewFrom;
/* 191 */       if (viewFromToAdd == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 196 */         if (hasStart()) {
/* 197 */           viewFromToAdd = ViewFrom.LEFT;
/*     */         }
/*     */         else {
/* 200 */           viewFromToAdd = ViewFrom.RIGHT;
/*     */         }
/*     */       }
/* 203 */       switch (viewFromToAdd) {
/*     */       case LEFT: 
/* 205 */         queryObject.put("vf", "l");
/* 206 */         break;
/*     */       case RIGHT: 
/* 208 */         queryObject.put("vf", "r");
/*     */       }
/*     */       
/*     */     }
/* 212 */     if (!this.index.equals(PriorityIndex.getInstance())) {
/* 213 */       queryObject.put("i", this.index.getQueryDefinition());
/*     */     }
/* 215 */     return queryObject;
/*     */   }
/*     */   
/*     */   public boolean loadsAllData() {
/* 219 */     return (!hasStart()) && (!hasEnd()) && (!hasLimit());
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/* 223 */     return (loadsAllData()) && (this.index.equals(PriorityIndex.getInstance()));
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 227 */     return (!hasStart()) || (!hasEnd()) || (!hasLimit()) || (hasAnchoredLimit());
/*     */   }
/*     */   
/*     */   public String toJSON() {
/* 231 */     if (this.jsonSerialization == null) {
/*     */       try {
/* 233 */         this.jsonSerialization = mapperInstance.writeValueAsString(getWireProtocolParams());
/*     */       } catch (IOException e) {
/* 235 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/* 238 */     return this.jsonSerialization;
/*     */   }
/*     */   
/*     */   public static QueryParams fromQueryObject(Map<String, Object> map) {
/* 242 */     QueryParams params = new QueryParams();
/* 243 */     params.limit = ((Integer)map.get("l"));
/*     */     
/* 245 */     if (map.containsKey("sp")) {
/* 246 */       Object indexStartValue = map.get("sp");
/* 247 */       params.indexStartValue = NodeUtilities.NodeFromJSON(indexStartValue);
/* 248 */       String indexStartName = (String)map.get("sn");
/* 249 */       if (indexStartName != null) {
/* 250 */         params.indexStartName = ChildKey.fromString(indexStartName);
/*     */       }
/*     */     }
/*     */     
/* 254 */     if (map.containsKey("ep")) {
/* 255 */       Object indexEndValue = map.get("ep");
/* 256 */       params.indexEndValue = NodeUtilities.NodeFromJSON(indexEndValue);
/* 257 */       String indexEndName = (String)map.get("en");
/* 258 */       if (indexEndName != null) {
/* 259 */         params.indexEndName = ChildKey.fromString(indexEndName);
/*     */       }
/*     */     }
/*     */     
/* 263 */     String viewFrom = (String)map.get("vf");
/* 264 */     if (viewFrom != null) {
/* 265 */       params.viewFrom = (viewFrom.equals("l") ? ViewFrom.LEFT : ViewFrom.RIGHT);
/*     */     }
/*     */     
/* 268 */     String indexStr = (String)map.get("i");
/* 269 */     if (indexStr != null) {
/* 270 */       params.index = Index.fromQueryDefinition(indexStr);
/*     */     }
/*     */     
/* 273 */     return params;
/*     */   }
/*     */   
/*     */   public NodeFilter getNodeFilter()
/*     */   {
/* 278 */     if (loadsAllData())
/* 279 */       return new IndexedFilter(getIndex());
/* 280 */     if (hasLimit()) {
/* 281 */       return new LimitedFilter(this);
/*     */     }
/* 283 */     return new com.firebase.client.core.view.filter.RangedFilter(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 290 */     return getWireProtocolParams().toString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 295 */     if (this == o) return true;
/* 296 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/* 298 */     QueryParams that = (QueryParams)o;
/*     */     
/* 300 */     if (this.limit != null ? !this.limit.equals(that.limit) : that.limit != null) return false;
/* 301 */     if (this.index != null ? !this.index.equals(that.index) : that.index != null) return false;
/* 302 */     if (this.indexEndName != null ? !this.indexEndName.equals(that.indexEndName) : that.indexEndName != null) return false;
/* 303 */     if (this.indexEndValue != null ? !this.indexEndValue.equals(that.indexEndValue) : that.indexEndValue != null)
/* 304 */       return false;
/* 305 */     if (this.indexStartName != null ? !this.indexStartName.equals(that.indexStartName) : that.indexStartName != null)
/* 306 */       return false;
/* 307 */     if (this.indexStartValue != null ? !this.indexStartValue.equals(that.indexStartValue) : that.indexStartValue != null) {
/* 308 */       return false;
/*     */     }
/* 310 */     if (isViewFromLeft() != that.isViewFromLeft()) {
/* 311 */       return false;
/*     */     }
/* 313 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 318 */     int result = this.limit != null ? this.limit.intValue() : 0;
/* 319 */     result = 31 * result + (isViewFromLeft() ? 1231 : 1237);
/* 320 */     result = 31 * result + (this.indexStartValue != null ? this.indexStartValue.hashCode() : 0);
/* 321 */     result = 31 * result + (this.indexStartName != null ? this.indexStartName.hashCode() : 0);
/* 322 */     result = 31 * result + (this.indexEndValue != null ? this.indexEndValue.hashCode() : 0);
/* 323 */     result = 31 * result + (this.indexEndName != null ? this.indexEndName.hashCode() : 0);
/* 324 */     result = 31 * result + (this.index != null ? this.index.hashCode() : 0);
/* 325 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/QueryParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */