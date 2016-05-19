/*     */ package com.firebase.tubesock;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ class MessageBuilderFactory
/*     */ {
/*     */   static abstract interface Builder
/*     */   {
/*     */     public abstract boolean appendBytes(byte[] paramArrayOfByte);
/*     */     
/*     */     public abstract WebSocketMessage toMessage();
/*     */   }
/*     */   
/*     */   static class BinaryBuilder
/*     */     implements MessageBuilderFactory.Builder
/*     */   {
/*     */     private List<byte[]> pendingBytes;
/*  29 */     private int pendingByteCount = 0;
/*     */     
/*     */     BinaryBuilder() {
/*  32 */       this.pendingBytes = new ArrayList();
/*     */     }
/*     */     
/*     */     public boolean appendBytes(byte[] bytes) {
/*  36 */       this.pendingBytes.add(bytes);
/*  37 */       this.pendingByteCount += bytes.length;
/*  38 */       return true;
/*     */     }
/*     */     
/*     */     public WebSocketMessage toMessage() {
/*  42 */       byte[] payload = new byte[this.pendingByteCount];
/*  43 */       int offset = 0;
/*  44 */       for (int i = 0; i < this.pendingBytes.size(); i++) {
/*  45 */         byte[] segment = (byte[])this.pendingBytes.get(i);
/*  46 */         System.arraycopy(segment, 0, payload, offset, segment.length);
/*  47 */         offset += segment.length;
/*     */       }
/*  49 */       return new WebSocketMessage(payload);
/*     */     }
/*     */   }
/*     */   
/*     */   static class TextBuilder implements MessageBuilderFactory.Builder {
/*  54 */     private static ThreadLocal<CharsetDecoder> localDecoder = new ThreadLocal()
/*     */     {
/*     */       protected CharsetDecoder initialValue() {
/*  57 */         Charset utf8 = Charset.forName("UTF8");
/*  58 */         CharsetDecoder decoder = utf8.newDecoder();
/*  59 */         decoder.onMalformedInput(CodingErrorAction.REPORT);
/*  60 */         decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
/*  61 */         return decoder;
/*     */       }
/*     */     };
/*  64 */     private static ThreadLocal<CharsetEncoder> localEncoder = new ThreadLocal()
/*     */     {
/*     */       protected CharsetEncoder initialValue() {
/*  67 */         Charset utf8 = Charset.forName("UTF8");
/*  68 */         CharsetEncoder encoder = utf8.newEncoder();
/*  69 */         encoder.onMalformedInput(CodingErrorAction.REPORT);
/*  70 */         encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
/*  71 */         return encoder;
/*     */       }
/*     */     };
/*     */     private StringBuilder builder;
/*     */     private ByteBuffer carryOver;
/*     */     
/*     */     TextBuilder()
/*     */     {
/*  79 */       this.builder = new StringBuilder();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean appendBytes(byte[] bytes)
/*     */     {
/*  86 */       String nextFrame = decodeString(bytes);
/*  87 */       if (nextFrame != null) {
/*  88 */         this.builder.append(nextFrame);
/*  89 */         return true;
/*     */       }
/*  91 */       return false;
/*     */     }
/*     */     
/*     */     public WebSocketMessage toMessage() {
/*  95 */       if (this.carryOver != null) {
/*  96 */         return null;
/*     */       }
/*  98 */       return new WebSocketMessage(this.builder.toString());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private String decodeString(byte[] bytes)
/*     */     {
/*     */       try
/*     */       {
/* 108 */         ByteBuffer input = ByteBuffer.wrap(bytes);
/* 109 */         CharBuffer buf = ((CharsetDecoder)localDecoder.get()).decode(input);
/* 110 */         return buf.toString();
/*     */       }
/*     */       catch (CharacterCodingException e) {}
/* 113 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private String decodeStringStreaming(byte[] bytes)
/*     */     {
/*     */       try
/*     */       {
/* 126 */         ByteBuffer input = getBuffer(bytes);
/* 127 */         int bufSize = (int)(input.remaining() * ((CharsetDecoder)localDecoder.get()).averageCharsPerByte());
/* 128 */         CharBuffer output = CharBuffer.allocate(bufSize);
/*     */         for (;;) {
/* 130 */           CoderResult result = ((CharsetDecoder)localDecoder.get()).decode(input, output, false);
/* 131 */           if (result.isError()) {
/* 132 */             return null;
/*     */           }
/* 134 */           if (result.isUnderflow()) {
/*     */             break;
/*     */           }
/* 137 */           if (result.isOverflow())
/*     */           {
/* 139 */             bufSize = 2 * bufSize + 1;
/* 140 */             CharBuffer o = CharBuffer.allocate(bufSize);
/* 141 */             output.flip();
/* 142 */             o.put(output);
/* 143 */             output = o;
/*     */           }
/*     */         }
/* 146 */         if (input.remaining() > 0) {
/* 147 */           this.carryOver = input;
/*     */         }
/*     */         
/* 150 */         CharBuffer test = CharBuffer.wrap(output);
/* 151 */         ((CharsetEncoder)localEncoder.get()).encode(test);
/* 152 */         output.flip();
/* 153 */         return output.toString();
/*     */       }
/*     */       catch (CharacterCodingException e) {}
/* 156 */       return null;
/*     */     }
/*     */     
/*     */     private ByteBuffer getBuffer(byte[] bytes)
/*     */     {
/* 161 */       if (this.carryOver != null) {
/* 162 */         ByteBuffer buffer = ByteBuffer.allocate(bytes.length + this.carryOver.remaining());
/* 163 */         buffer.put(this.carryOver);
/* 164 */         this.carryOver = null;
/* 165 */         buffer.put(bytes);
/* 166 */         buffer.flip();
/* 167 */         return buffer;
/*     */       }
/* 169 */       return ByteBuffer.wrap(bytes);
/*     */     }
/*     */   }
/*     */   
/*     */   static Builder builder(byte opcode)
/*     */   {
/* 175 */     if (opcode == 2) {
/* 176 */       return new BinaryBuilder();
/*     */     }
/*     */     
/* 179 */     return new TextBuilder();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/MessageBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */