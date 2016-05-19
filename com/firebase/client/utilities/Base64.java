/*      */ package com.firebase.client.utilities;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamClass;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int NO_OPTIONS = 0;
/*      */   public static final int ENCODE = 1;
/*      */   public static final int DECODE = 0;
/*      */   public static final int GZIP = 2;
/*      */   public static final int DONT_GUNZIP = 4;
/*      */   public static final int DO_BREAK_LINES = 8;
/*      */   public static final int URL_SAFE = 16;
/*      */   public static final int ORDERED = 32;
/*      */   private static final int MAX_LINE_LENGTH = 76;
/*      */   private static final byte EQUALS_SIGN = 61;
/*      */   private static final byte NEW_LINE = 10;
/*      */   private static final String PREFERRED_ENCODING = "US-ASCII";
/*      */   private static final byte WHITE_SPACE_ENC = -5;
/*      */   private static final byte EQUALS_SIGN_ENC = -1;
/*  222 */   private static final byte[] _STANDARD_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  240 */   private static final byte[] _STANDARD_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  282 */   private static final byte[] _URL_SAFE_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  298 */   private static final byte[] _URL_SAFE_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  345 */   private static final byte[] _ORDERED_ALPHABET = { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  363 */   private static final byte[] _ORDERED_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final byte[] getAlphabet(int options)
/*      */   {
/*  413 */     if ((options & 0x10) == 16)
/*  414 */       return _URL_SAFE_ALPHABET;
/*  415 */     if ((options & 0x20) == 32) {
/*  416 */       return _ORDERED_ALPHABET;
/*      */     }
/*  418 */     return _STANDARD_ALPHABET;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final byte[] getDecodabet(int options)
/*      */   {
/*  431 */     if ((options & 0x10) == 16)
/*  432 */       return _URL_SAFE_DECODABET;
/*  433 */     if ((options & 0x20) == 32) {
/*  434 */       return _ORDERED_DECODABET;
/*      */     }
/*  436 */     return _STANDARD_DECODABET;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options)
/*      */   {
/*  467 */     encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
/*  468 */     return b4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options)
/*      */   {
/*  499 */     byte[] ALPHABET = getAlphabet(options);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  512 */     int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0) | (numSigBytes > 1 ? source[(srcOffset + 1)] << 24 >>> 16 : 0) | (numSigBytes > 2 ? source[(srcOffset + 2)] << 24 >>> 24 : 0);
/*      */     
/*      */ 
/*      */ 
/*  516 */     switch (numSigBytes)
/*      */     {
/*      */     case 3: 
/*  519 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  520 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  521 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  522 */       destination[(destOffset + 3)] = ALPHABET[(inBuff & 0x3F)];
/*  523 */       return destination;
/*      */     
/*      */     case 2: 
/*  526 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  527 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  528 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  529 */       destination[(destOffset + 3)] = 61;
/*  530 */       return destination;
/*      */     
/*      */     case 1: 
/*  533 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  534 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  535 */       destination[(destOffset + 2)] = 61;
/*  536 */       destination[(destOffset + 3)] = 61;
/*  537 */       return destination;
/*      */     }
/*      */     
/*  540 */     return destination;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void encode(ByteBuffer raw, ByteBuffer encoded)
/*      */   {
/*  558 */     byte[] raw3 = new byte[3];
/*  559 */     byte[] enc4 = new byte[4];
/*      */     
/*  561 */     while (raw.hasRemaining()) {
/*  562 */       int rem = Math.min(3, raw.remaining());
/*  563 */       raw.get(raw3, 0, rem);
/*  564 */       encode3to4(enc4, raw3, rem, 0);
/*  565 */       encoded.put(enc4);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void encode(ByteBuffer raw, CharBuffer encoded)
/*      */   {
/*  582 */     byte[] raw3 = new byte[3];
/*  583 */     byte[] enc4 = new byte[4];
/*      */     
/*  585 */     while (raw.hasRemaining()) {
/*  586 */       int rem = Math.min(3, raw.remaining());
/*  587 */       raw.get(raw3, 0, rem);
/*  588 */       encode3to4(enc4, raw3, rem, 0);
/*  589 */       for (int i = 0; i < 4; i++) {
/*  590 */         encoded.put((char)(enc4[i] & 0xFF));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject)
/*      */     throws IOException
/*      */   {
/*  618 */     return encodeObject(serializableObject, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject, int options)
/*      */     throws IOException
/*      */   {
/*  655 */     if (serializableObject == null) {
/*  656 */       throw new NullPointerException("Cannot serialize a null object.");
/*      */     }
/*      */     
/*      */ 
/*  660 */     ByteArrayOutputStream baos = null;
/*  661 */     OutputStream b64os = null;
/*  662 */     GZIPOutputStream gzos = null;
/*  663 */     ObjectOutputStream oos = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  668 */       baos = new ByteArrayOutputStream();
/*  669 */       b64os = new OutputStream(baos, 0x1 | options);
/*  670 */       if ((options & 0x2) != 0)
/*      */       {
/*  672 */         gzos = new GZIPOutputStream(b64os);
/*  673 */         oos = new ObjectOutputStream(gzos);
/*      */       }
/*      */       else {
/*  676 */         oos = new ObjectOutputStream(b64os);
/*      */       }
/*  678 */       oos.writeObject(serializableObject);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  686 */         oos.close(); } catch (Exception e) {}
/*  687 */       try { gzos.close(); } catch (Exception e) {}
/*  688 */       try { b64os.close(); } catch (Exception e) {}
/*  689 */       try { baos.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */       try
/*      */       {
/*  694 */         return new String(baos.toByteArray(), "US-ASCII");
/*      */       }
/*      */       catch (UnsupportedEncodingException uue) {}
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  683 */       throw e;
/*      */     } finally {
/*      */       try {
/*  686 */         oos.close(); } catch (Exception e) {}
/*  687 */       try { gzos.close(); } catch (Exception e) {}
/*  688 */       try { b64os.close(); } catch (Exception e) {}
/*  689 */       try { baos.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  698 */     return new String(baos.toByteArray());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeBytes(byte[] source)
/*      */   {
/*  718 */     String encoded = null;
/*      */     try {
/*  720 */       encoded = encodeBytes(source, 0, source.length, 0);
/*      */     } catch (IOException ex) {
/*  722 */       if (!$assertionsDisabled) throw new AssertionError(ex.getMessage());
/*      */     }
/*  724 */     assert (encoded != null);
/*  725 */     return encoded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int options)
/*      */     throws IOException
/*      */   {
/*  760 */     return encodeBytes(source, 0, source.length, options);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len)
/*      */   {
/*  786 */     String encoded = null;
/*      */     try {
/*  788 */       encoded = encodeBytes(source, off, len, 0);
/*      */     } catch (IOException ex) {
/*  790 */       if (!$assertionsDisabled) throw new AssertionError(ex.getMessage());
/*      */     }
/*  792 */     assert (encoded != null);
/*  793 */     return encoded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len, int options)
/*      */     throws IOException
/*      */   {
/*  831 */     byte[] encoded = encodeBytesToBytes(source, off, len, options);
/*      */     
/*      */     try
/*      */     {
/*  835 */       return new String(encoded, "US-ASCII");
/*      */     }
/*      */     catch (UnsupportedEncodingException uue) {}
/*  838 */     return new String(encoded);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBytesToBytes(byte[] source)
/*      */   {
/*  858 */     byte[] encoded = null;
/*      */     try {
/*  860 */       encoded = encodeBytesToBytes(source, 0, source.length, 0);
/*      */     } catch (IOException ex) {
/*  862 */       if (!$assertionsDisabled) throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + ex.getMessage());
/*      */     }
/*  864 */     return encoded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options)
/*      */     throws IOException
/*      */   {
/*  888 */     if (source == null) {
/*  889 */       throw new NullPointerException("Cannot serialize a null array.");
/*      */     }
/*      */     
/*  892 */     if (off < 0) {
/*  893 */       throw new IllegalArgumentException("Cannot have negative offset: " + off);
/*      */     }
/*      */     
/*  896 */     if (len < 0) {
/*  897 */       throw new IllegalArgumentException("Cannot have length offset: " + len);
/*      */     }
/*      */     
/*  900 */     if (off + len > source.length) {
/*  901 */       throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[] { Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(source.length) }));
/*      */     }
/*      */     
/*      */ 
/*      */     ByteArrayOutputStream baos;
/*      */     
/*      */ 
/*  908 */     if ((options & 0x2) != 0) {
/*  909 */       baos = null;
/*  910 */       GZIPOutputStream gzos = null;
/*  911 */       OutputStream b64os = null;
/*      */       
/*      */       try
/*      */       {
/*  915 */         baos = new ByteArrayOutputStream();
/*  916 */         b64os = new OutputStream(baos, 0x1 | options);
/*  917 */         gzos = new GZIPOutputStream(b64os);
/*      */         
/*  919 */         gzos.write(source, off, len);
/*  920 */         gzos.close();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  933 */         return baos.toByteArray();
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/*  925 */         throw e;
/*      */       } finally {
/*      */         try {
/*  928 */           gzos.close(); } catch (Exception e) {}
/*  929 */         try { b64os.close(); } catch (Exception e) {}
/*  930 */         try { baos.close();
/*      */         }
/*      */         catch (Exception e) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  938 */     boolean breakLines = (options & 0x8) != 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  947 */     int encLen = len / 3 * 4 + (len % 3 > 0 ? 4 : 0);
/*  948 */     if (breakLines) {
/*  949 */       encLen += encLen / 76;
/*      */     }
/*  951 */     byte[] outBuff = new byte[encLen];
/*      */     
/*      */ 
/*  954 */     int d = 0;
/*  955 */     int e = 0;
/*  956 */     int len2 = len - 2;
/*  957 */     int lineLength = 0;
/*  958 */     for (; d < len2; e += 4) {
/*  959 */       encode3to4(source, d + off, 3, outBuff, e, options);
/*      */       
/*  961 */       lineLength += 4;
/*  962 */       if ((breakLines) && (lineLength >= 76))
/*      */       {
/*  964 */         outBuff[(e + 4)] = 10;
/*  965 */         e++;
/*  966 */         lineLength = 0;
/*      */       }
/*  958 */       d += 3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  970 */     if (d < len) {
/*  971 */       encode3to4(source, d + off, len - d, outBuff, e, options);
/*  972 */       e += 4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  977 */     if (e <= outBuff.length - 1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  982 */       byte[] finalOut = new byte[e];
/*  983 */       System.arraycopy(outBuff, 0, finalOut, 0, e);
/*      */       
/*  985 */       return finalOut;
/*      */     }
/*      */     
/*  988 */     return outBuff;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options)
/*      */   {
/* 1035 */     if (source == null) {
/* 1036 */       throw new NullPointerException("Source array was null.");
/*      */     }
/* 1038 */     if (destination == null) {
/* 1039 */       throw new NullPointerException("Destination array was null.");
/*      */     }
/* 1041 */     if ((srcOffset < 0) || (srcOffset + 3 >= source.length)) {
/* 1042 */       throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[] { Integer.valueOf(source.length), Integer.valueOf(srcOffset) }));
/*      */     }
/*      */     
/* 1045 */     if ((destOffset < 0) || (destOffset + 2 >= destination.length)) {
/* 1046 */       throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[] { Integer.valueOf(destination.length), Integer.valueOf(destOffset) }));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1051 */     byte[] DECODABET = getDecodabet(options);
/*      */     
/*      */ 
/* 1054 */     if (source[(srcOffset + 2)] == 61)
/*      */     {
/*      */ 
/*      */ 
/* 1058 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12;
/*      */       
/*      */ 
/* 1061 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/* 1062 */       return 1;
/*      */     }
/*      */     
/*      */ 
/* 1066 */     if (source[(srcOffset + 3)] == 61)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1071 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6;
/*      */       
/*      */ 
/*      */ 
/* 1075 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/* 1076 */       destination[(destOffset + 1)] = ((byte)(outBuff >>> 8));
/* 1077 */       return 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1087 */     int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6 | DECODABET[source[(srcOffset + 3)]] & 0xFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1093 */     destination[destOffset] = ((byte)(outBuff >> 16));
/* 1094 */     destination[(destOffset + 1)] = ((byte)(outBuff >> 8));
/* 1095 */     destination[(destOffset + 2)] = ((byte)outBuff);
/*      */     
/* 1097 */     return 3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decode(byte[] source)
/*      */     throws IOException
/*      */   {
/* 1120 */     byte[] decoded = null;
/*      */     
/* 1122 */     decoded = decode(source, 0, source.length, 0);
/*      */     
/*      */ 
/*      */ 
/* 1126 */     return decoded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decode(byte[] source, int off, int len, int options)
/*      */     throws IOException
/*      */   {
/* 1152 */     if (source == null) {
/* 1153 */       throw new NullPointerException("Cannot decode null source array.");
/*      */     }
/* 1155 */     if ((off < 0) || (off + len > source.length)) {
/* 1156 */       throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[] { Integer.valueOf(source.length), Integer.valueOf(off), Integer.valueOf(len) }));
/*      */     }
/*      */     
/*      */ 
/* 1160 */     if (len == 0)
/* 1161 */       return new byte[0];
/* 1162 */     if (len < 4) {
/* 1163 */       throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + len);
/*      */     }
/*      */     
/*      */ 
/* 1167 */     byte[] DECODABET = getDecodabet(options);
/*      */     
/* 1169 */     int len34 = len * 3 / 4;
/* 1170 */     byte[] outBuff = new byte[len34];
/* 1171 */     int outBuffPosn = 0;
/*      */     
/* 1173 */     byte[] b4 = new byte[4];
/* 1174 */     int b4Posn = 0;
/* 1175 */     int i = 0;
/* 1176 */     byte sbiDecode = 0;
/*      */     
/* 1178 */     for (i = off; i < off + len; i++)
/*      */     {
/* 1180 */       sbiDecode = DECODABET[(source[i] & 0xFF)];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1185 */       if (sbiDecode >= -5) {
/* 1186 */         if (sbiDecode >= -1) {
/* 1187 */           b4[(b4Posn++)] = source[i];
/* 1188 */           if (b4Posn > 3) {
/* 1189 */             outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
/* 1190 */             b4Posn = 0;
/*      */             
/*      */ 
/* 1193 */             if (source[i] == 61) {
/*      */               break;
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/* 1201 */         throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[] { Integer.valueOf(source[i] & 0xFF), Integer.valueOf(i) }));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1206 */     byte[] out = new byte[outBuffPosn];
/* 1207 */     System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
/* 1208 */     return out;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decode(String s)
/*      */     throws IOException
/*      */   {
/* 1224 */     return decode(s, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decode(String s, int options)
/*      */     throws IOException
/*      */   {
/* 1242 */     if (s == null) {
/* 1243 */       throw new NullPointerException("Input string was null.");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1248 */       bytes = s.getBytes("US-ASCII");
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {
/* 1251 */       bytes = s.getBytes();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1256 */     bytes = decode(bytes, 0, bytes.length, options);
/*      */     
/*      */ 
/*      */ 
/* 1260 */     boolean dontGunzip = (options & 0x4) != 0;
/* 1261 */     if ((bytes != null) && (bytes.length >= 4) && (!dontGunzip))
/*      */     {
/* 1263 */       int head = bytes[0] & 0xFF | bytes[1] << 8 & 0xFF00;
/* 1264 */       if (35615 == head) {
/* 1265 */         ByteArrayInputStream bais = null;
/* 1266 */         GZIPInputStream gzis = null;
/* 1267 */         ByteArrayOutputStream baos = null;
/* 1268 */         byte[] buffer = new byte['ࠀ'];
/* 1269 */         int length = 0;
/*      */         try
/*      */         {
/* 1272 */           baos = new ByteArrayOutputStream();
/* 1273 */           bais = new ByteArrayInputStream(bytes);
/* 1274 */           gzis = new GZIPInputStream(bais);
/*      */           
/* 1276 */           while ((length = gzis.read(buffer)) >= 0) {
/* 1277 */             baos.write(buffer, 0, length);
/*      */           }
/*      */           
/*      */ 
/* 1281 */           return baos.toByteArray();
/*      */ 
/*      */         }
/*      */         catch (IOException e) {}finally
/*      */         {
/*      */           try
/*      */           {
/* 1288 */             baos.close(); } catch (Exception e) {}
/* 1289 */           try { gzis.close(); } catch (Exception e) {}
/* 1290 */           try { bais.close();
/*      */           }
/*      */           catch (Exception e) {}
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Object decodeToObject(String encodedObject)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1315 */     return decodeToObject(encodedObject, 0, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Object decodeToObject(String encodedObject, int options, final ClassLoader loader)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1340 */     byte[] objBytes = decode(encodedObject, options);
/*      */     
/* 1342 */     ByteArrayInputStream bais = null;
/* 1343 */     ObjectInputStream ois = null;
/* 1344 */     obj = null;
/*      */     try
/*      */     {
/* 1347 */       bais = new ByteArrayInputStream(objBytes);
/*      */       
/*      */ 
/* 1350 */       if (loader == null) {
/* 1351 */         ois = new ObjectInputStream(bais);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1357 */         ois = new ObjectInputStream(bais)
/*      */         {
/*      */           public Class<?> resolveClass(ObjectStreamClass streamClass) throws IOException, ClassNotFoundException
/*      */           {
/* 1361 */             Class<?> c = Class.forName(streamClass.getName(), false, loader);
/* 1362 */             if (c == null) {
/* 1363 */               return super.resolveClass(streamClass);
/*      */             }
/* 1365 */             return c;
/*      */           }
/*      */         };
/*      */       }
/*      */       
/*      */ 
/* 1371 */       return ois.readObject();
/*      */     }
/*      */     catch (IOException e) {
/* 1374 */       throw e;
/*      */     }
/*      */     catch (ClassNotFoundException e) {
/* 1377 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1380 */         bais.close(); } catch (Exception e) {}
/* 1381 */       try { ois.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void encodeToFile(byte[] dataToEncode, String filename)
/*      */     throws IOException
/*      */   {
/* 1406 */     if (dataToEncode == null) {
/* 1407 */       throw new NullPointerException("Data to encode was null.");
/*      */     }
/*      */     
/* 1410 */     OutputStream bos = null;
/*      */     try {
/* 1412 */       bos = new OutputStream(new FileOutputStream(filename), 1);
/*      */       
/* 1414 */       bos.write(dataToEncode); return;
/*      */     }
/*      */     catch (IOException e) {
/* 1417 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1420 */         bos.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void decodeToFile(String dataToDecode, String filename)
/*      */     throws IOException
/*      */   {
/* 1442 */     OutputStream bos = null;
/*      */     try {
/* 1444 */       bos = new OutputStream(new FileOutputStream(filename), 0);
/*      */       
/* 1446 */       bos.write(dataToDecode.getBytes("US-ASCII")); return;
/*      */     }
/*      */     catch (IOException e) {
/* 1449 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1452 */         bos.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decodeFromFile(String filename)
/*      */     throws IOException
/*      */   {
/* 1477 */     decodedData = null;
/* 1478 */     InputStream bis = null;
/*      */     
/*      */     try
/*      */     {
/* 1482 */       File file = new File(filename);
/* 1483 */       byte[] buffer = null;
/* 1484 */       int length = 0;
/* 1485 */       int numBytes = 0;
/*      */       
/*      */ 
/* 1488 */       if (file.length() > 2147483647L)
/*      */       {
/* 1490 */         throw new IOException("File is too big for this convenience method (" + file.length() + " bytes).");
/*      */       }
/* 1492 */       buffer = new byte[(int)file.length()];
/*      */       
/*      */ 
/* 1495 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1500 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1501 */         length += numBytes;
/*      */       }
/*      */       
/*      */ 
/* 1505 */       decodedData = new byte[length];
/* 1506 */       System.arraycopy(buffer, 0, decodedData, 0, length);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1516 */       return decodedData;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1510 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1513 */         bis.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeFromFile(String filename)
/*      */     throws IOException
/*      */   {
/* 1538 */     encodedData = null;
/* 1539 */     InputStream bis = null;
/*      */     
/*      */     try
/*      */     {
/* 1543 */       File file = new File(filename);
/* 1544 */       byte[] buffer = new byte[Math.max((int)(file.length() * 1.4D + 1.0D), 40)];
/* 1545 */       int length = 0;
/* 1546 */       int numBytes = 0;
/*      */       
/*      */ 
/* 1549 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1554 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/* 1555 */         length += numBytes;
/*      */       }
/*      */       
/*      */ 
/* 1559 */       return new String(buffer, 0, length, "US-ASCII");
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1563 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1566 */         bis.close();
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void encodeFileToFile(String infile, String outfile)
/*      */     throws IOException
/*      */   {
/* 1583 */     String encoded = encodeFromFile(infile);
/* 1584 */     OutputStream out = null;
/*      */     try {
/* 1586 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */       
/* 1588 */       out.write(encoded.getBytes("US-ASCII")); return;
/*      */     }
/*      */     catch (IOException e) {
/* 1591 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1594 */         out.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void decodeFileToFile(String infile, String outfile)
/*      */     throws IOException
/*      */   {
/* 1611 */     byte[] decoded = decodeFromFile(infile);
/* 1612 */     OutputStream out = null;
/*      */     try {
/* 1614 */       out = new BufferedOutputStream(new FileOutputStream(outfile));
/*      */       
/* 1616 */       out.write(decoded); return;
/*      */     }
/*      */     catch (IOException e) {
/* 1619 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1622 */         out.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class InputStream
/*      */     extends FilterInputStream
/*      */   {
/*      */     private boolean encode;
/*      */     
/*      */ 
/*      */     private int position;
/*      */     
/*      */ 
/*      */     private byte[] buffer;
/*      */     
/*      */ 
/*      */     private int bufferLength;
/*      */     
/*      */ 
/*      */     private int numSigBytes;
/*      */     
/*      */ 
/*      */     private int lineLength;
/*      */     
/*      */ 
/*      */     private boolean breakLines;
/*      */     
/*      */     private int options;
/*      */     
/*      */     private byte[] decodabet;
/*      */     
/*      */ 
/*      */     public InputStream(InputStream in)
/*      */     {
/* 1660 */       this(in, 0);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public InputStream(InputStream in, int options)
/*      */     {
/* 1686 */       super();
/* 1687 */       this.options = options;
/* 1688 */       this.breakLines = ((options & 0x8) > 0);
/* 1689 */       this.encode = ((options & 0x1) > 0);
/* 1690 */       this.bufferLength = (this.encode ? 4 : 3);
/* 1691 */       this.buffer = new byte[this.bufferLength];
/* 1692 */       this.position = -1;
/* 1693 */       this.lineLength = 0;
/* 1694 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int read()
/*      */       throws IOException
/*      */     {
/* 1708 */       if (this.position < 0) {
/* 1709 */         if (this.encode) {
/* 1710 */           byte[] b3 = new byte[3];
/* 1711 */           int numBinaryBytes = 0;
/* 1712 */           for (int i = 0; i < 3; i++) {
/* 1713 */             int b = this.in.read();
/*      */             
/*      */ 
/* 1716 */             if (b < 0) break;
/* 1717 */             b3[i] = ((byte)b);
/* 1718 */             numBinaryBytes++;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1725 */           if (numBinaryBytes > 0) {
/* 1726 */             Base64.encode3to4(b3, 0, numBinaryBytes, this.buffer, 0, this.options);
/* 1727 */             this.position = 0;
/* 1728 */             this.numSigBytes = 4;
/*      */           }
/*      */           else {
/* 1731 */             return -1;
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1737 */           byte[] b4 = new byte[4];
/* 1738 */           int i = 0;
/* 1739 */           for (i = 0; i < 4; i++)
/*      */           {
/* 1741 */             int b = 0;
/* 1742 */             do { b = this.in.read();
/* 1743 */             } while ((b >= 0) && (this.decodabet[(b & 0x7F)] <= -5));
/*      */             
/* 1745 */             if (b < 0) {
/*      */               break;
/*      */             }
/*      */             
/* 1749 */             b4[i] = ((byte)b);
/*      */           }
/*      */           
/* 1752 */           if (i == 4) {
/* 1753 */             this.numSigBytes = Base64.decode4to3(b4, 0, this.buffer, 0, this.options);
/* 1754 */             this.position = 0;
/*      */           } else {
/* 1756 */             if (i == 0) {
/* 1757 */               return -1;
/*      */             }
/*      */             
/*      */ 
/* 1761 */             throw new IOException("Improperly padded Base64 input.");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1768 */       if (this.position >= 0)
/*      */       {
/* 1770 */         if (this.position >= this.numSigBytes) {
/* 1771 */           return -1;
/*      */         }
/*      */         
/* 1774 */         if ((this.encode) && (this.breakLines) && (this.lineLength >= 76)) {
/* 1775 */           this.lineLength = 0;
/* 1776 */           return 10;
/*      */         }
/*      */         
/* 1779 */         this.lineLength += 1;
/*      */         
/*      */ 
/*      */ 
/* 1783 */         int b = this.buffer[(this.position++)];
/*      */         
/* 1785 */         if (this.position >= this.bufferLength) {
/* 1786 */           this.position = -1;
/*      */         }
/*      */         
/* 1789 */         return b & 0xFF;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1796 */       throw new IOException("Error in Base64 code reading stream.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int read(byte[] dest, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1818 */       for (int i = 0; i < len; i++) {
/* 1819 */         int b = read();
/*      */         
/* 1821 */         if (b >= 0) {
/* 1822 */           dest[(off + i)] = ((byte)b);
/*      */         } else {
/* 1824 */           if (i != 0) break;
/* 1825 */           return -1;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1831 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class OutputStream
/*      */     extends FilterOutputStream
/*      */   {
/*      */     private boolean encode;
/*      */     
/*      */ 
/*      */     private int position;
/*      */     
/*      */ 
/*      */     private byte[] buffer;
/*      */     
/*      */ 
/*      */     private int bufferLength;
/*      */     
/*      */ 
/*      */     private int lineLength;
/*      */     
/*      */ 
/*      */     private boolean breakLines;
/*      */     
/*      */ 
/*      */     private byte[] b4;
/*      */     
/*      */ 
/*      */     private boolean suspendEncoding;
/*      */     
/*      */ 
/*      */     private int options;
/*      */     
/*      */ 
/*      */     private byte[] decodabet;
/*      */     
/*      */ 
/*      */ 
/*      */     public OutputStream(OutputStream out)
/*      */     {
/* 1873 */       this(out, 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public OutputStream(OutputStream out, int options)
/*      */     {
/* 1897 */       super();
/* 1898 */       this.breakLines = ((options & 0x8) != 0);
/* 1899 */       this.encode = ((options & 0x1) != 0);
/* 1900 */       this.bufferLength = (this.encode ? 3 : 4);
/* 1901 */       this.buffer = new byte[this.bufferLength];
/* 1902 */       this.position = 0;
/* 1903 */       this.lineLength = 0;
/* 1904 */       this.suspendEncoding = false;
/* 1905 */       this.b4 = new byte[4];
/* 1906 */       this.options = options;
/* 1907 */       this.decodabet = Base64.getDecodabet(options);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void write(int theByte)
/*      */       throws IOException
/*      */     {
/* 1927 */       if (this.suspendEncoding) {
/* 1928 */         this.out.write(theByte);
/* 1929 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1933 */       if (this.encode) {
/* 1934 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1935 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1937 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
/*      */           
/* 1939 */           this.lineLength += 4;
/* 1940 */           if ((this.breakLines) && (this.lineLength >= 76)) {
/* 1941 */             this.out.write(10);
/* 1942 */             this.lineLength = 0;
/*      */           }
/*      */           
/* 1945 */           this.position = 0;
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/* 1952 */       else if (this.decodabet[(theByte & 0x7F)] > -5) {
/* 1953 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1954 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1956 */           int len = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
/* 1957 */           this.out.write(this.b4, 0, len);
/* 1958 */           this.position = 0;
/*      */         }
/*      */       }
/* 1961 */       else if (this.decodabet[(theByte & 0x7F)] != -5) {
/* 1962 */         throw new IOException("Invalid character in Base64 data.");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void write(byte[] theBytes, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1982 */       if (this.suspendEncoding) {
/* 1983 */         this.out.write(theBytes, off, len);
/* 1984 */         return;
/*      */       }
/*      */       
/* 1987 */       for (int i = 0; i < len; i++) {
/* 1988 */         write(theBytes[(off + i)]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void flushBase64()
/*      */       throws IOException
/*      */     {
/* 2001 */       if (this.position > 0) {
/* 2002 */         if (this.encode) {
/* 2003 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
/* 2004 */           this.position = 0;
/*      */         }
/*      */         else {
/* 2007 */           throw new IOException("Base64 input not properly padded.");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void close()
/*      */       throws IOException
/*      */     {
/* 2022 */       flushBase64();
/*      */       
/*      */ 
/*      */ 
/* 2026 */       super.close();
/*      */       
/* 2028 */       this.buffer = null;
/* 2029 */       this.out = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void suspendEncoding()
/*      */       throws IOException
/*      */     {
/* 2043 */       flushBase64();
/* 2044 */       this.suspendEncoding = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void resumeEncoding()
/*      */     {
/* 2056 */       this.suspendEncoding = false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */