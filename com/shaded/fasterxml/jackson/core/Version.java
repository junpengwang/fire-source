/*     */ package com.shaded.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class Version
/*     */   implements Comparable<Version>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
/*     */   
/*     */ 
/*     */ 
/*     */   protected final int _majorVersion;
/*     */   
/*     */ 
/*     */   protected final int _minorVersion;
/*     */   
/*     */ 
/*     */   protected final int _patchLevel;
/*     */   
/*     */ 
/*     */   protected final String _groupId;
/*     */   
/*     */ 
/*     */   protected final String _artifactId;
/*     */   
/*     */ 
/*     */   protected final String _snapshotInfo;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Version(int paramInt1, int paramInt2, int paramInt3, String paramString)
/*     */   {
/*  48 */     this(paramInt1, paramInt2, paramInt3, paramString, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public Version(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3)
/*     */   {
/*  54 */     this._majorVersion = paramInt1;
/*  55 */     this._minorVersion = paramInt2;
/*  56 */     this._patchLevel = paramInt3;
/*  57 */     this._snapshotInfo = paramString1;
/*  58 */     this._groupId = (paramString2 == null ? "" : paramString2);
/*  59 */     this._artifactId = (paramString3 == null ? "" : paramString3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  66 */   public static Version unknownVersion() { return UNKNOWN_VERSION; }
/*     */   
/*  68 */   public boolean isUknownVersion() { return this == UNKNOWN_VERSION; }
/*  69 */   public boolean isSnapshot() { return (this._snapshotInfo != null) && (this._snapshotInfo.length() > 0); }
/*     */   
/*  71 */   public int getMajorVersion() { return this._majorVersion; }
/*  72 */   public int getMinorVersion() { return this._minorVersion; }
/*  73 */   public int getPatchLevel() { return this._patchLevel; }
/*     */   
/*  75 */   public String getGroupId() { return this._groupId; }
/*  76 */   public String getArtifactId() { return this._artifactId; }
/*     */   
/*     */   public String toFullString() {
/*  79 */     return this._groupId + '/' + this._artifactId + '/' + toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  91 */     StringBuilder localStringBuilder = new StringBuilder();
/*  92 */     localStringBuilder.append(this._majorVersion).append('.');
/*  93 */     localStringBuilder.append(this._minorVersion).append('.');
/*  94 */     localStringBuilder.append(this._patchLevel);
/*  95 */     if (isSnapshot()) {
/*  96 */       localStringBuilder.append('-').append(this._snapshotInfo);
/*     */     }
/*  98 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 103 */     return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 109 */     if (paramObject == this) return true;
/* 110 */     if (paramObject == null) return false;
/* 111 */     if (paramObject.getClass() != getClass()) return false;
/* 112 */     Version localVersion = (Version)paramObject;
/* 113 */     return (localVersion._majorVersion == this._majorVersion) && (localVersion._minorVersion == this._minorVersion) && (localVersion._patchLevel == this._patchLevel) && (localVersion._artifactId.equals(this._artifactId)) && (localVersion._groupId.equals(this._groupId));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int compareTo(Version paramVersion)
/*     */   {
/* 124 */     if (paramVersion == this) { return 0;
/*     */     }
/* 126 */     int i = this._groupId.compareTo(paramVersion._groupId);
/* 127 */     if (i == 0) {
/* 128 */       i = this._artifactId.compareTo(paramVersion._artifactId);
/* 129 */       if (i == 0) {
/* 130 */         i = this._majorVersion - paramVersion._majorVersion;
/* 131 */         if (i == 0) {
/* 132 */           i = this._minorVersion - paramVersion._minorVersion;
/* 133 */           if (i == 0) {
/* 134 */             i = this._patchLevel - paramVersion._patchLevel;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 139 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */