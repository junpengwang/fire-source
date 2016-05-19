/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ 
/*     */ public class Path implements Iterable<ChildKey>
/*     */ {
/*     */   private final ChildKey[] pieces;
/*     */   private final int start;
/*     */   private final int end;
/*     */   
/*     */   public static Path getRelative(Path from, Path to)
/*     */   {
/*  13 */     ChildKey outerFront = from.getFront();
/*  14 */     ChildKey innerFront = to.getFront();
/*  15 */     if (outerFront == null)
/*  16 */       return to;
/*  17 */     if (outerFront.equals(innerFront)) {
/*  18 */       return getRelative(from.popFront(), to.popFront());
/*     */     }
/*  20 */     throw new com.firebase.client.FirebaseException("INTERNAL ERROR: " + to + " is not contained in " + from);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static final Path EMPTY_PATH = new Path("");
/*     */   
/*     */   public static Path getEmptyPath() {
/*  31 */     return EMPTY_PATH;
/*     */   }
/*     */   
/*     */   public Path(ChildKey... segments) {
/*  35 */     this.pieces = ((ChildKey[])java.util.Arrays.copyOf(segments, segments.length));
/*  36 */     this.start = 0;
/*  37 */     this.end = segments.length;
/*  38 */     for (ChildKey name : segments) {
/*  39 */       assert (name != null) : "Can't construct a path with a null value!";
/*     */     }
/*     */   }
/*     */   
/*     */   public Path(String pathString) {
/*  44 */     String[] segments = pathString.split("/");
/*  45 */     int count = 0;
/*  46 */     for (String segment : segments) {
/*  47 */       if (segment.length() > 0) {
/*  48 */         count++;
/*     */       }
/*     */     }
/*  51 */     this.pieces = new ChildKey[count];
/*  52 */     int j = 0;
/*  53 */     for (String segment : segments) {
/*  54 */       if (segment.length() > 0) {
/*  55 */         this.pieces[(j++)] = ChildKey.fromString(segment);
/*     */       }
/*     */     }
/*  58 */     this.start = 0;
/*  59 */     this.end = this.pieces.length;
/*     */   }
/*     */   
/*     */   private Path(ChildKey[] pieces, int start, int end) {
/*  63 */     this.pieces = pieces;
/*  64 */     this.start = start;
/*  65 */     this.end = end;
/*     */   }
/*     */   
/*     */   public Path child(Path path) {
/*  69 */     int newSize = size() + path.size();
/*  70 */     ChildKey[] newPieces = new ChildKey[newSize];
/*  71 */     System.arraycopy(this.pieces, this.start, newPieces, 0, size());
/*  72 */     System.arraycopy(path.pieces, path.start, newPieces, size(), path.size());
/*  73 */     return new Path(newPieces, 0, newSize);
/*     */   }
/*     */   
/*     */   public Path child(ChildKey child) {
/*  77 */     int size = size();
/*  78 */     ChildKey[] newPieces = new ChildKey[size + 1];
/*  79 */     System.arraycopy(this.pieces, this.start, newPieces, 0, size);
/*  80 */     newPieces[size] = child;
/*  81 */     return new Path(newPieces, 0, size + 1);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  85 */     if (isEmpty()) {
/*  86 */       return "/";
/*     */     }
/*  88 */     StringBuilder builder = new StringBuilder();
/*  89 */     for (int i = this.start; i < this.end; i++) {
/*  90 */       builder.append("/");
/*  91 */       builder.append(this.pieces[i].asString());
/*     */     }
/*  93 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public String wireFormat()
/*     */   {
/*  98 */     if (isEmpty()) {
/*  99 */       return "/";
/*     */     }
/* 101 */     StringBuilder builder = new StringBuilder();
/* 102 */     for (int i = this.start; i < this.end; i++) {
/* 103 */       if (i > this.start) {
/* 104 */         builder.append("/");
/*     */       }
/* 106 */       builder.append(this.pieces[i].asString());
/*     */     }
/* 108 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public ChildKey getFront()
/*     */   {
/* 113 */     if (isEmpty()) {
/* 114 */       return null;
/*     */     }
/* 116 */     return this.pieces[this.start];
/*     */   }
/*     */   
/*     */   public Path popFront()
/*     */   {
/* 121 */     int newStart = this.start;
/* 122 */     if (!isEmpty()) {
/* 123 */       newStart++;
/*     */     }
/* 125 */     return new Path(this.pieces, newStart, this.end);
/*     */   }
/*     */   
/*     */   public Path getParent() {
/* 129 */     if (isEmpty()) {
/* 130 */       return null;
/*     */     }
/* 132 */     return new Path(this.pieces, this.start, this.end - 1);
/*     */   }
/*     */   
/*     */   public ChildKey getBack()
/*     */   {
/* 137 */     if (!isEmpty()) {
/* 138 */       return this.pieces[(this.end - 1)];
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 145 */     return this.start >= this.end;
/*     */   }
/*     */   
/*     */   public int size() {
/* 149 */     return this.end - this.start;
/*     */   }
/*     */   
/*     */   public java.util.Iterator<ChildKey> iterator()
/*     */   {
/* 154 */     new java.util.Iterator() {
/* 155 */       int offset = Path.this.start;
/*     */       
/*     */       public boolean hasNext() {
/* 158 */         return this.offset < Path.this.end;
/*     */       }
/*     */       
/*     */       public ChildKey next()
/*     */       {
/* 163 */         if (!hasNext()) {
/* 164 */           throw new java.util.NoSuchElementException("No more elements.");
/*     */         }
/* 166 */         ChildKey child = Path.this.pieces[this.offset];
/* 167 */         this.offset += 1;
/* 168 */         return child;
/*     */       }
/*     */       
/*     */       public void remove()
/*     */       {
/* 173 */         throw new UnsupportedOperationException("Can't remove component from immutable Path!");
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public boolean contains(Path other) {
/* 179 */     if (size() > other.size()) {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     int i = this.start;
/* 184 */     int j = other.start;
/* 185 */     while (i < this.end) {
/* 186 */       if (!this.pieces[i].equals(other.pieces[j])) {
/* 187 */         return false;
/*     */       }
/* 189 */       i++;
/* 190 */       j++;
/*     */     }
/*     */     
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   public boolean equals(Object other)
/*     */   {
/* 198 */     if (!(other instanceof Path))
/* 199 */       return false;
/* 200 */     if (this == other)
/* 201 */       return true;
/* 202 */     Path otherPath = (Path)other;
/* 203 */     if (size() != otherPath.size()) {
/* 204 */       return false;
/*     */     }
/* 206 */     int i = this.start; for (int j = otherPath.start; (i < this.end) && (j < otherPath.end); j++) {
/* 207 */       if (!this.pieces[i].equals(otherPath.pieces[j])) {
/* 208 */         return false;
/*     */       }
/* 206 */       i++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 211 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 216 */     int hashCode = 0;
/* 217 */     for (int i = this.start; i < this.end; i++) {
/* 218 */       hashCode = hashCode * 37 + this.pieces[i].hashCode();
/*     */     }
/* 220 */     return hashCode;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/Path.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */