/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class SerializedString
/*     */   implements SerializableString, Serializable
/*     */ {
/*     */   protected final String _value;
/*     */   protected byte[] _quotedUTF8Ref;
/*     */   protected byte[] _unquotedUTF8Ref;
/*     */   protected char[] _quotedChars;
/*     */   protected transient String _jdkSerializeValue;
/*     */   
/*     */   public SerializedString(String paramString)
/*     */   {
/*  39 */     if (paramString == null) {
/*  40 */       throw new IllegalStateException("Null String illegal for SerializedString");
/*     */     }
/*  42 */     this._value = paramString;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException
/*     */   {
/*  60 */     this._jdkSerializeValue = paramObjectInputStream.readUTF();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  64 */     paramObjectOutputStream.writeUTF(this._value);
/*     */   }
/*     */   
/*     */   protected Object readResolve() {
/*  68 */     return new SerializedString(this._jdkSerializeValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getValue()
/*     */   {
/*  78 */     return this._value;
/*     */   }
/*     */   
/*     */ 
/*     */   public final int charLength()
/*     */   {
/*  84 */     return this._value.length();
/*     */   }
/*     */   
/*     */   public final char[] asQuotedChars()
/*     */   {
/*  89 */     char[] arrayOfChar = this._quotedChars;
/*  90 */     if (arrayOfChar == null) {
/*  91 */       arrayOfChar = JsonStringEncoder.getInstance().quoteAsString(this._value);
/*  92 */       this._quotedChars = arrayOfChar;
/*     */     }
/*  94 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final byte[] asUnquotedUTF8()
/*     */   {
/* 104 */     byte[] arrayOfByte = this._unquotedUTF8Ref;
/* 105 */     if (arrayOfByte == null) {
/* 106 */       arrayOfByte = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 107 */       this._unquotedUTF8Ref = arrayOfByte;
/*     */     }
/* 109 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final byte[] asQuotedUTF8()
/*     */   {
/* 119 */     byte[] arrayOfByte = this._quotedUTF8Ref;
/* 120 */     if (arrayOfByte == null) {
/* 121 */       arrayOfByte = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 122 */       this._quotedUTF8Ref = arrayOfByte;
/*     */     }
/* 124 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int appendQuotedUTF8(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 136 */     byte[] arrayOfByte = this._quotedUTF8Ref;
/* 137 */     if (arrayOfByte == null) {
/* 138 */       arrayOfByte = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 139 */       this._quotedUTF8Ref = arrayOfByte;
/*     */     }
/* 141 */     int i = arrayOfByte.length;
/* 142 */     if (paramInt + i > paramArrayOfByte.length) {
/* 143 */       return -1;
/*     */     }
/* 145 */     System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt, i);
/* 146 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public int appendQuoted(char[] paramArrayOfChar, int paramInt)
/*     */   {
/* 152 */     char[] arrayOfChar = this._quotedChars;
/* 153 */     if (arrayOfChar == null) {
/* 154 */       arrayOfChar = JsonStringEncoder.getInstance().quoteAsString(this._value);
/* 155 */       this._quotedChars = arrayOfChar;
/*     */     }
/* 157 */     int i = arrayOfChar.length;
/* 158 */     if (paramInt + i > paramArrayOfChar.length) {
/* 159 */       return -1;
/*     */     }
/* 161 */     System.arraycopy(arrayOfChar, 0, paramArrayOfChar, paramInt, i);
/* 162 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public int appendUnquotedUTF8(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 168 */     byte[] arrayOfByte = this._unquotedUTF8Ref;
/* 169 */     if (arrayOfByte == null) {
/* 170 */       arrayOfByte = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 171 */       this._unquotedUTF8Ref = arrayOfByte;
/*     */     }
/* 173 */     int i = arrayOfByte.length;
/* 174 */     if (paramInt + i > paramArrayOfByte.length) {
/* 175 */       return -1;
/*     */     }
/* 177 */     System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt, i);
/* 178 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public int appendUnquoted(char[] paramArrayOfChar, int paramInt)
/*     */   {
/* 184 */     String str = this._value;
/* 185 */     int i = str.length();
/* 186 */     if (paramInt + i > paramArrayOfChar.length) {
/* 187 */       return -1;
/*     */     }
/* 189 */     str.getChars(0, i, paramArrayOfChar, paramInt);
/* 190 */     return i;
/*     */   }
/*     */   
/*     */   public int writeQuotedUTF8(OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 196 */     byte[] arrayOfByte = this._quotedUTF8Ref;
/* 197 */     if (arrayOfByte == null) {
/* 198 */       arrayOfByte = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 199 */       this._quotedUTF8Ref = arrayOfByte;
/*     */     }
/* 201 */     int i = arrayOfByte.length;
/* 202 */     paramOutputStream.write(arrayOfByte, 0, i);
/* 203 */     return i;
/*     */   }
/*     */   
/*     */   public int writeUnquotedUTF8(OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 209 */     byte[] arrayOfByte = this._unquotedUTF8Ref;
/* 210 */     if (arrayOfByte == null) {
/* 211 */       arrayOfByte = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 212 */       this._unquotedUTF8Ref = arrayOfByte;
/*     */     }
/* 214 */     int i = arrayOfByte.length;
/* 215 */     paramOutputStream.write(arrayOfByte, 0, i);
/* 216 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public int putQuotedUTF8(ByteBuffer paramByteBuffer)
/*     */   {
/* 222 */     byte[] arrayOfByte = this._quotedUTF8Ref;
/* 223 */     if (arrayOfByte == null) {
/* 224 */       arrayOfByte = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 225 */       this._quotedUTF8Ref = arrayOfByte;
/*     */     }
/* 227 */     int i = arrayOfByte.length;
/* 228 */     if (i > paramByteBuffer.remaining()) {
/* 229 */       return -1;
/*     */     }
/* 231 */     paramByteBuffer.put(arrayOfByte, 0, i);
/* 232 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public int putUnquotedUTF8(ByteBuffer paramByteBuffer)
/*     */   {
/* 238 */     byte[] arrayOfByte = this._unquotedUTF8Ref;
/* 239 */     if (arrayOfByte == null) {
/* 240 */       arrayOfByte = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 241 */       this._unquotedUTF8Ref = arrayOfByte;
/*     */     }
/* 243 */     int i = arrayOfByte.length;
/* 244 */     if (i > paramByteBuffer.remaining()) {
/* 245 */       return -1;
/*     */     }
/* 247 */     paramByteBuffer.put(arrayOfByte, 0, i);
/* 248 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 259 */     return this._value;
/*     */   }
/*     */   
/* 262 */   public final int hashCode() { return this._value.hashCode(); }
/*     */   
/*     */ 
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/* 267 */     if (paramObject == this) return true;
/* 268 */     if ((paramObject == null) || (paramObject.getClass() != getClass())) return false;
/* 269 */     SerializedString localSerializedString = (SerializedString)paramObject;
/* 270 */     return this._value.equals(localSerializedString._value);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/SerializedString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */