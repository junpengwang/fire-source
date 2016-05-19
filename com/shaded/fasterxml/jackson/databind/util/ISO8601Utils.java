/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ISO8601Utils
/*     */ {
/*     */   private static final String GMT_ID = "GMT";
/*  19 */   private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone("GMT");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TimeZone timeZoneGMT()
/*     */   {
/*  31 */     return TIMEZONE_GMT;
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
/*     */   public static String format(Date paramDate)
/*     */   {
/*  47 */     return format(paramDate, false, TIMEZONE_GMT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String format(Date paramDate, boolean paramBoolean)
/*     */   {
/*  58 */     return format(paramDate, paramBoolean, TIMEZONE_GMT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String format(Date paramDate, boolean paramBoolean, TimeZone paramTimeZone)
/*     */   {
/*  70 */     GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramTimeZone, Locale.US);
/*  71 */     localGregorianCalendar.setTime(paramDate);
/*     */     
/*     */ 
/*  74 */     int i = "yyyy-MM-ddThh:mm:ss".length();
/*  75 */     i += (paramBoolean ? ".sss".length() : 0);
/*  76 */     i += (paramTimeZone.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length());
/*  77 */     StringBuilder localStringBuilder = new StringBuilder(i);
/*     */     
/*  79 */     padInt(localStringBuilder, localGregorianCalendar.get(1), "yyyy".length());
/*  80 */     localStringBuilder.append('-');
/*  81 */     padInt(localStringBuilder, localGregorianCalendar.get(2) + 1, "MM".length());
/*  82 */     localStringBuilder.append('-');
/*  83 */     padInt(localStringBuilder, localGregorianCalendar.get(5), "dd".length());
/*  84 */     localStringBuilder.append('T');
/*  85 */     padInt(localStringBuilder, localGregorianCalendar.get(11), "hh".length());
/*  86 */     localStringBuilder.append(':');
/*  87 */     padInt(localStringBuilder, localGregorianCalendar.get(12), "mm".length());
/*  88 */     localStringBuilder.append(':');
/*  89 */     padInt(localStringBuilder, localGregorianCalendar.get(13), "ss".length());
/*  90 */     if (paramBoolean) {
/*  91 */       localStringBuilder.append('.');
/*  92 */       padInt(localStringBuilder, localGregorianCalendar.get(14), "sss".length());
/*     */     }
/*     */     
/*  95 */     int j = paramTimeZone.getOffset(localGregorianCalendar.getTimeInMillis());
/*  96 */     if (j != 0) {
/*  97 */       int k = Math.abs(j / 60000 / 60);
/*  98 */       int m = Math.abs(j / 60000 % 60);
/*  99 */       localStringBuilder.append(j < 0 ? '-' : '+');
/* 100 */       padInt(localStringBuilder, k, "hh".length());
/* 101 */       localStringBuilder.append(':');
/* 102 */       padInt(localStringBuilder, m, "mm".length());
/*     */     } else {
/* 104 */       localStringBuilder.append('Z');
/*     */     }
/*     */     
/* 107 */     return localStringBuilder.toString();
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
/*     */   public static Date parse(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 125 */       int i = 0;
/*     */       
/*     */ 
/* 128 */       int j = parseInt(paramString, , i);
/* 129 */       checkOffset(paramString, i, '-');
/*     */       
/*     */ 
/* 132 */       i += 2;int k = parseInt(paramString, ++i, i);
/* 133 */       checkOffset(paramString, i, '-');
/*     */       
/*     */ 
/* 136 */       i += 2;int m = parseInt(paramString, ++i, i);
/* 137 */       checkOffset(paramString, i, 'T');
/*     */       
/*     */ 
/* 140 */       i += 2;int n = parseInt(paramString, ++i, i);
/* 141 */       checkOffset(paramString, i, ':');
/*     */       
/* 143 */       i += 2;int i1 = parseInt(paramString, ++i, i);
/* 144 */       checkOffset(paramString, i, ':');
/*     */       
/* 146 */       i += 2;int i2 = parseInt(paramString, ++i, i);
/*     */       
/* 148 */       int i3 = 0;
/* 149 */       if (paramString.charAt(i) == '.') {
/* 150 */         checkOffset(paramString, i, '.');
/* 151 */         i += 3;i3 = parseInt(paramString, ++i, i);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 156 */       char c = paramString.charAt(i);
/* 157 */       String str; if ((c == '+') || (c == '-')) {
/* 158 */         str = "GMT" + paramString.substring(i);
/* 159 */       } else if (c == 'Z') {
/* 160 */         str = "GMT";
/*     */       } else {
/* 162 */         throw new IndexOutOfBoundsException("Invalid time zone indicator " + c);
/*     */       }
/* 164 */       TimeZone localTimeZone = TimeZone.getTimeZone(str);
/* 165 */       if (!localTimeZone.getID().equals(str)) {
/* 166 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */       
/* 169 */       GregorianCalendar localGregorianCalendar = new GregorianCalendar(localTimeZone);
/* 170 */       localGregorianCalendar.setLenient(false);
/* 171 */       localGregorianCalendar.set(1, j);
/* 172 */       localGregorianCalendar.set(2, k - 1);
/* 173 */       localGregorianCalendar.set(5, m);
/* 174 */       localGregorianCalendar.set(11, n);
/* 175 */       localGregorianCalendar.set(12, i1);
/* 176 */       localGregorianCalendar.set(13, i2);
/* 177 */       localGregorianCalendar.set(14, i3);
/*     */       
/* 179 */       return localGregorianCalendar.getTime();
/*     */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 181 */       throw new IllegalArgumentException("Failed to parse date " + paramString, localIndexOutOfBoundsException);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/* 183 */       throw new IllegalArgumentException("Failed to parse date " + paramString, localNumberFormatException);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 185 */       throw new IllegalArgumentException("Failed to parse date " + paramString, localIllegalArgumentException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void checkOffset(String paramString, int paramInt, char paramChar)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/* 198 */     char c = paramString.charAt(paramInt);
/* 199 */     if (c != paramChar) {
/* 200 */       throw new IndexOutOfBoundsException("Expected '" + paramChar + "' character but found '" + c + "'");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int parseInt(String paramString, int paramInt1, int paramInt2)
/*     */     throws NumberFormatException
/*     */   {
/* 214 */     if ((paramInt1 < 0) || (paramInt2 > paramString.length()) || (paramInt1 > paramInt2)) {
/* 215 */       throw new NumberFormatException(paramString);
/*     */     }
/*     */     
/* 218 */     int i = paramInt1;
/* 219 */     int j = 0;
/*     */     int k;
/* 221 */     if (i < paramInt2) {
/* 222 */       k = Character.digit(paramString.charAt(i++), 10);
/* 223 */       if (k < 0) {
/* 224 */         throw new NumberFormatException("Invalid number: " + paramString);
/*     */       }
/* 226 */       j = -k;
/*     */     }
/* 228 */     while (i < paramInt2) {
/* 229 */       k = Character.digit(paramString.charAt(i++), 10);
/* 230 */       if (k < 0) {
/* 231 */         throw new NumberFormatException("Invalid number: " + paramString);
/*     */       }
/* 233 */       j *= 10;
/* 234 */       j -= k;
/*     */     }
/* 236 */     return -j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void padInt(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*     */   {
/* 247 */     String str = Integer.toString(paramInt1);
/* 248 */     for (int i = paramInt2 - str.length(); i > 0; i--) {
/* 249 */       paramStringBuilder.append('0');
/*     */     }
/* 251 */     paramStringBuilder.append(str);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ISO8601Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */