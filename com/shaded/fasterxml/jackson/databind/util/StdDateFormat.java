/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
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
/*     */ public class StdDateFormat
/*     */   extends DateFormat
/*     */ {
/*     */   protected static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
/*     */   protected static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
/*     */   protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
/*     */   protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/*  55 */   protected static final String[] ALL_FORMATS = { "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd" };
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
/*  67 */   private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT");
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
/*  85 */   protected static final DateFormat DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
/*  86 */   static { DATE_FORMAT_RFC1123.setTimeZone(DEFAULT_TIMEZONE);
/*  87 */     DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
/*  88 */     DATE_FORMAT_ISO8601.setTimeZone(DEFAULT_TIMEZONE);
/*  89 */     DATE_FORMAT_ISO8601_Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
/*  90 */     DATE_FORMAT_ISO8601_Z.setTimeZone(DEFAULT_TIMEZONE);
/*  91 */     DATE_FORMAT_PLAIN = new SimpleDateFormat("yyyy-MM-dd");
/*  92 */     DATE_FORMAT_PLAIN.setTimeZone(DEFAULT_TIMEZONE);
/*     */   }
/*     */   
/*     */   protected static final DateFormat DATE_FORMAT_ISO8601;
/*     */   protected static final DateFormat DATE_FORMAT_ISO8601_Z;
/*     */   protected static final DateFormat DATE_FORMAT_PLAIN;
/*  98 */   public static final StdDateFormat instance = new StdDateFormat();
/*     */   
/*     */ 
/*     */   protected transient TimeZone _timezone;
/*     */   
/*     */ 
/*     */   protected transient DateFormat _formatRFC1123;
/*     */   
/*     */ 
/*     */   protected transient DateFormat _formatISO8601;
/*     */   
/*     */ 
/*     */   protected transient DateFormat _formatISO8601_z;
/*     */   
/*     */ 
/*     */   protected transient DateFormat _formatPlain;
/*     */   
/*     */ 
/*     */ 
/*     */   public StdDateFormat(TimeZone paramTimeZone)
/*     */   {
/* 119 */     this._timezone = paramTimeZone;
/*     */   }
/*     */   
/*     */   public static TimeZone getDefaultTimeZone() {
/* 123 */     return DEFAULT_TIMEZONE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdDateFormat withTimeZone(TimeZone paramTimeZone)
/*     */   {
/* 131 */     if (paramTimeZone == null) {
/* 132 */       paramTimeZone = DEFAULT_TIMEZONE;
/*     */     }
/* 134 */     return new StdDateFormat(paramTimeZone);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdDateFormat clone()
/*     */   {
/* 142 */     return new StdDateFormat();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DateFormat getBlueprintISO8601Format()
/*     */   {
/* 151 */     return DATE_FORMAT_ISO8601;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DateFormat getISO8601Format(TimeZone paramTimeZone)
/*     */   {
/* 160 */     return _cloneFormat(DATE_FORMAT_ISO8601, paramTimeZone);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DateFormat getBlueprintRFC1123Format()
/*     */   {
/* 169 */     return DATE_FORMAT_RFC1123;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DateFormat getRFC1123Format(TimeZone paramTimeZone)
/*     */   {
/* 179 */     return _cloneFormat(DATE_FORMAT_RFC1123, paramTimeZone);
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
/*     */   public void setTimeZone(TimeZone paramTimeZone)
/*     */   {
/* 194 */     if (paramTimeZone != this._timezone) {
/* 195 */       this._formatRFC1123 = null;
/* 196 */       this._formatISO8601 = null;
/* 197 */       this._formatISO8601_z = null;
/* 198 */       this._formatPlain = null;
/* 199 */       this._timezone = paramTimeZone;
/*     */     }
/*     */   }
/*     */   
/*     */   public Date parse(String paramString)
/*     */     throws ParseException
/*     */   {
/* 206 */     paramString = paramString.trim();
/* 207 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 208 */     Date localDate = parse(paramString, localParsePosition);
/* 209 */     if (localDate != null) {
/* 210 */       return localDate;
/*     */     }
/*     */     
/* 213 */     StringBuilder localStringBuilder = new StringBuilder();
/* 214 */     for (String str : ALL_FORMATS) {
/* 215 */       if (localStringBuilder.length() > 0) {
/* 216 */         localStringBuilder.append("\", \"");
/*     */       } else {
/* 218 */         localStringBuilder.append('"');
/*     */       }
/* 220 */       localStringBuilder.append(str);
/*     */     }
/* 222 */     localStringBuilder.append('"');
/* 223 */     throw new ParseException(String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", new Object[] { paramString, localStringBuilder.toString() }), localParsePosition.getErrorIndex());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 231 */     if (looksLikeISO8601(paramString)) {
/* 232 */       return parseAsISO8601(paramString, paramParsePosition);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 237 */     int i = paramString.length();
/* 238 */     for (;;) { i--; if (i < 0) break;
/* 239 */       int j = paramString.charAt(i);
/* 240 */       if ((j < 48) || (j > 57)) break;
/*     */     }
/* 242 */     if ((i < 0) && 
/* 243 */       (NumberInput.inLongRange(paramString, false))) {
/* 244 */       return new Date(Long.parseLong(paramString));
/*     */     }
/*     */     
/*     */ 
/* 248 */     return parseAsRFC1123(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/* 255 */     if (this._formatISO8601 == null) {
/* 256 */       this._formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601);
/*     */     }
/* 258 */     return this._formatISO8601.format(paramDate, paramStringBuffer, paramFieldPosition);
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
/*     */   protected boolean looksLikeISO8601(String paramString)
/*     */   {
/* 273 */     if ((paramString.length() >= 5) && (Character.isDigit(paramString.charAt(0))) && (Character.isDigit(paramString.charAt(3))) && (paramString.charAt(4) == '-'))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 278 */       return true;
/*     */     }
/* 280 */     return false;
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
/*     */   protected Date parseAsISO8601(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 293 */     int i = paramString.length();
/* 294 */     char c = paramString.charAt(i - 1);
/*     */     
/*     */     DateFormat localDateFormat;
/*     */     
/* 298 */     if ((i <= 10) && (Character.isDigit(c))) {
/* 299 */       localDateFormat = this._formatPlain;
/* 300 */       if (localDateFormat == null)
/* 301 */         localDateFormat = this._formatPlain = _cloneFormat(DATE_FORMAT_PLAIN);
/*     */     } else { StringBuilder localStringBuilder;
/* 303 */       if (c == 'Z') {
/* 304 */         localDateFormat = this._formatISO8601_z;
/* 305 */         if (localDateFormat == null) {
/* 306 */           localDateFormat = this._formatISO8601_z = _cloneFormat(DATE_FORMAT_ISO8601_Z);
/*     */         }
/*     */         
/* 309 */         if (paramString.charAt(i - 4) == ':') {
/* 310 */           localStringBuilder = new StringBuilder(paramString);
/* 311 */           localStringBuilder.insert(i - 1, ".000");
/* 312 */           paramString = localStringBuilder.toString();
/*     */         }
/*     */         
/*     */       }
/* 316 */       else if (hasTimeZone(paramString)) {
/* 317 */         c = paramString.charAt(i - 3);
/* 318 */         if (c == ':')
/*     */         {
/* 320 */           localStringBuilder = new StringBuilder(paramString);
/* 321 */           localStringBuilder.delete(i - 3, i - 2);
/* 322 */           paramString = localStringBuilder.toString();
/* 323 */         } else if ((c == '+') || (c == '-'))
/*     */         {
/* 325 */           paramString = paramString + "00";
/*     */         }
/*     */         
/* 328 */         i = paramString.length();
/*     */         
/* 330 */         c = paramString.charAt(i - 9);
/* 331 */         if (Character.isDigit(c)) {
/* 332 */           localStringBuilder = new StringBuilder(paramString);
/* 333 */           localStringBuilder.insert(i - 5, ".000");
/* 334 */           paramString = localStringBuilder.toString();
/*     */         }
/*     */         
/* 337 */         localDateFormat = this._formatISO8601;
/* 338 */         if (this._formatISO8601 == null) {
/* 339 */           localDateFormat = this._formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 347 */         localStringBuilder = new StringBuilder(paramString);
/*     */         
/* 349 */         int j = i - paramString.lastIndexOf('T') - 1;
/* 350 */         if (j <= 8) {
/* 351 */           localStringBuilder.append(".000");
/*     */         }
/* 353 */         localStringBuilder.append('Z');
/* 354 */         paramString = localStringBuilder.toString();
/* 355 */         localDateFormat = this._formatISO8601_z;
/* 356 */         if (localDateFormat == null) {
/* 357 */           localDateFormat = this._formatISO8601_z = _cloneFormat(DATE_FORMAT_ISO8601_Z);
/*     */         }
/*     */       }
/*     */     }
/* 361 */     return localDateFormat.parse(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */   protected Date parseAsRFC1123(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 366 */     if (this._formatRFC1123 == null) {
/* 367 */       this._formatRFC1123 = _cloneFormat(DATE_FORMAT_RFC1123);
/*     */     }
/* 369 */     return this._formatRFC1123.parse(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */   private static final boolean hasTimeZone(String paramString)
/*     */   {
/* 375 */     int i = paramString.length();
/* 376 */     if (i >= 6) {
/* 377 */       int j = paramString.charAt(i - 6);
/* 378 */       if ((j == 43) || (j == 45)) return true;
/* 379 */       j = paramString.charAt(i - 5);
/* 380 */       if ((j == 43) || (j == 45)) return true;
/* 381 */       j = paramString.charAt(i - 3);
/* 382 */       if ((j == 43) || (j == 45)) return true;
/*     */     }
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private final DateFormat _cloneFormat(DateFormat paramDateFormat) {
/* 388 */     return _cloneFormat(paramDateFormat, this._timezone);
/*     */   }
/*     */   
/*     */   private static final DateFormat _cloneFormat(DateFormat paramDateFormat, TimeZone paramTimeZone)
/*     */   {
/* 393 */     paramDateFormat = (DateFormat)paramDateFormat.clone();
/* 394 */     if (paramTimeZone != null) {
/* 395 */       paramDateFormat.setTimeZone(paramTimeZone);
/*     */     }
/* 397 */     return paramDateFormat;
/*     */   }
/*     */   
/*     */   public StdDateFormat() {}
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/StdDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */