/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ public class UserWriteRecord
/*    */ {
/*    */   private final long writeId;
/*    */   private final Path path;
/*    */   private final Node overwrite;
/*    */   private final CompoundWrite merge;
/*    */   private final boolean visible;
/*    */   
/*    */   public UserWriteRecord(long writeId, Path path, Node overwrite, boolean visible) {
/* 14 */     this.writeId = writeId;
/* 15 */     this.path = path;
/* 16 */     this.overwrite = overwrite;
/* 17 */     this.merge = null;
/* 18 */     this.visible = visible;
/*    */   }
/*    */   
/*    */   public UserWriteRecord(long writeId, Path path, CompoundWrite merge) {
/* 22 */     this.writeId = writeId;
/* 23 */     this.path = path;
/* 24 */     this.overwrite = null;
/* 25 */     this.merge = merge;
/* 26 */     this.visible = true;
/*    */   }
/*    */   
/*    */   public long getWriteId() {
/* 30 */     return this.writeId;
/*    */   }
/*    */   
/*    */   public Path getPath() {
/* 34 */     return this.path;
/*    */   }
/*    */   
/*    */   public Node getOverwrite() {
/* 38 */     if (this.overwrite == null) {
/* 39 */       throw new IllegalArgumentException("Can't access overwrite when write is a merge!");
/*    */     }
/* 41 */     return this.overwrite;
/*    */   }
/*    */   
/*    */   public CompoundWrite getMerge() {
/* 45 */     if (this.merge == null) {
/* 46 */       throw new IllegalArgumentException("Can't access merge when write is an overwrite!");
/*    */     }
/* 48 */     return this.merge;
/*    */   }
/*    */   
/*    */   public boolean isMerge() {
/* 52 */     return this.merge != null;
/*    */   }
/*    */   
/*    */   public boolean isOverwrite() {
/* 56 */     return this.overwrite != null;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 60 */     return this.visible;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 65 */     if (this == o) return true;
/* 66 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*    */     }
/* 68 */     UserWriteRecord record = (UserWriteRecord)o;
/*    */     
/* 70 */     if (this.writeId != record.writeId) return false;
/* 71 */     if (!this.path.equals(record.path)) return false;
/* 72 */     if (this.visible != record.visible) return false;
/* 73 */     if (this.overwrite != null ? !this.overwrite.equals(record.overwrite) : record.overwrite != null) return false;
/* 74 */     if (this.merge != null ? !this.merge.equals(record.merge) : record.merge != null) { return false;
/*    */     }
/* 76 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 81 */     int result = Long.valueOf(this.writeId).hashCode();
/* 82 */     result = 31 * result + Boolean.valueOf(this.visible).hashCode();
/* 83 */     result = 31 * result + this.path.hashCode();
/* 84 */     result = 31 * result + (this.overwrite != null ? this.overwrite.hashCode() : 0);
/* 85 */     result = 31 * result + (this.merge != null ? this.merge.hashCode() : 0);
/*    */     
/* 87 */     return result;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 92 */     return "UserWriteRecord{id=" + this.writeId + " path=" + this.path + " visible=" + this.visible + " overwrite=" + this.overwrite + " merge=" + this.merge + "}";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/UserWriteRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */