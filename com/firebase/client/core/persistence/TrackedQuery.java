/*    */ package com.firebase.client.core.persistence;
/*    */ 
/*    */ import com.firebase.client.core.view.QuerySpec;
/*    */ 
/*    */ public class TrackedQuery {
/*    */   public final long id;
/*    */   public final QuerySpec querySpec;
/*    */   public final long lastUse;
/*    */   public final boolean complete;
/*    */   public final boolean active;
/*    */   
/*    */   public TrackedQuery(long id, QuerySpec querySpec, long lastUse, boolean complete, boolean active) {
/* 13 */     this.id = id;
/* 14 */     if ((querySpec.loadsAllData()) && (!querySpec.isDefault())) {
/* 15 */       throw new IllegalArgumentException("Can't create TrackedQuery for a non-default query that loads all data");
/*    */     }
/* 17 */     this.querySpec = querySpec;
/* 18 */     this.lastUse = lastUse;
/* 19 */     this.complete = complete;
/* 20 */     this.active = active;
/*    */   }
/*    */   
/*    */   public TrackedQuery updateLastUse(long lastUse) {
/* 24 */     return new TrackedQuery(this.id, this.querySpec, lastUse, this.complete, this.active);
/*    */   }
/*    */   
/*    */   public TrackedQuery setComplete() {
/* 28 */     return new TrackedQuery(this.id, this.querySpec, this.lastUse, true, this.active);
/*    */   }
/*    */   
/*    */   public TrackedQuery setActiveState(boolean isActive) {
/* 32 */     return new TrackedQuery(this.id, this.querySpec, this.lastUse, this.complete, isActive);
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 37 */     if (o == this) return true;
/* 38 */     if ((o == null) || (o.getClass() != getClass())) { return false;
/*    */     }
/* 40 */     TrackedQuery query = (TrackedQuery)o;
/* 41 */     return (this.id == query.id) && (this.querySpec.equals(query.querySpec)) && (this.lastUse == query.lastUse) && (this.complete == query.complete) && (this.active == query.active);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 50 */     int result = Long.valueOf(this.id).hashCode();
/* 51 */     result = 31 * result + this.querySpec.hashCode();
/* 52 */     result = 31 * result + Long.valueOf(this.lastUse).hashCode();
/* 53 */     result = 31 * result + Boolean.valueOf(this.complete).hashCode();
/* 54 */     result = 31 * result + Boolean.valueOf(this.active).hashCode();
/* 55 */     return result;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 60 */     return "TrackedQuery{id=" + this.id + ", querySpec=" + this.querySpec + ", lastUse=" + this.lastUse + ", complete=" + this.complete + ", active=" + this.active + "}";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/TrackedQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */