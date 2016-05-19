/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import org.shaded.apache.http.annotation.Immutable;
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
/*     */ @Immutable
/*     */ public final class DateUtils
/*     */ {
/*     */   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/*     */   public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
/*     */   public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
/*  69 */   private static final String[] DEFAULT_PATTERNS = { "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
/*     */   
/*     */ 
/*     */ 
/*     */   private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
/*     */   
/*     */ 
/*     */ 
/*  77 */   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*     */   
/*     */   static {
/*  80 */     Calendar calendar = Calendar.getInstance();
/*  81 */     calendar.setTimeZone(GMT);
/*  82 */     calendar.set(2000, 0, 1, 0, 0, 0);
/*  83 */     calendar.set(14, 0);
/*  84 */     DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
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
/*     */   public static Date parseDate(String dateValue)
/*     */     throws DateParseException
/*     */   {
/*  99 */     return parseDate(dateValue, null, null);
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
/*     */   public static Date parseDate(String dateValue, String[] dateFormats)
/*     */     throws DateParseException
/*     */   {
/* 114 */     return parseDate(dateValue, dateFormats, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Date parseDate(String dateValue, String[] dateFormats, Date startDate)
/*     */     throws DateParseException
/*     */   {
/* 137 */     if (dateValue == null) {
/* 138 */       throw new IllegalArgumentException("dateValue is null");
/*     */     }
/* 140 */     if (dateFormats == null) {
/* 141 */       dateFormats = DEFAULT_PATTERNS;
/*     */     }
/* 143 */     if (startDate == null) {
/* 144 */       startDate = DEFAULT_TWO_DIGIT_YEAR_START;
/*     */     }
/*     */     
/*     */ 
/* 148 */     if ((dateValue.length() > 1) && (dateValue.startsWith("'")) && (dateValue.endsWith("'")))
/*     */     {
/*     */ 
/*     */ 
/* 152 */       dateValue = dateValue.substring(1, dateValue.length() - 1);
/*     */     }
/*     */     
/* 155 */     for (String dateFormat : dateFormats) {
/* 156 */       SimpleDateFormat dateParser = DateFormatHolder.formatFor(dateFormat);
/* 157 */       dateParser.set2DigitYearStart(startDate);
/*     */       try
/*     */       {
/* 160 */         return dateParser.parse(dateValue);
/*     */       }
/*     */       catch (ParseException pe) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 167 */     throw new DateParseException("Unable to parse the date " + dateValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatDate(Date date)
/*     */   {
/* 179 */     return formatDate(date, "EEE, dd MMM yyyy HH:mm:ss zzz");
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
/*     */   public static String formatDate(Date date, String pattern)
/*     */   {
/* 196 */     if (date == null) throw new IllegalArgumentException("date is null");
/* 197 */     if (pattern == null) { throw new IllegalArgumentException("pattern is null");
/*     */     }
/* 199 */     SimpleDateFormat formatter = DateFormatHolder.formatFor(pattern);
/* 200 */     return formatter.format(date);
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
/*     */   static final class DateFormatHolder
/*     */   {
/* 216 */     private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal()
/*     */     {
/*     */       protected SoftReference<Map<String, SimpleDateFormat>> initialValue()
/*     */       {
/* 220 */         return new SoftReference(new HashMap());
/*     */       }
/*     */     };
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
/*     */     public static SimpleDateFormat formatFor(String pattern)
/*     */     {
/* 239 */       SoftReference<Map<String, SimpleDateFormat>> ref = (SoftReference)THREADLOCAL_FORMATS.get();
/* 240 */       Map<String, SimpleDateFormat> formats = (Map)ref.get();
/* 241 */       if (formats == null) {
/* 242 */         formats = new HashMap();
/* 243 */         THREADLOCAL_FORMATS.set(new SoftReference(formats));
/*     */       }
/*     */       
/*     */ 
/* 247 */       SimpleDateFormat format = (SimpleDateFormat)formats.get(pattern);
/* 248 */       if (format == null) {
/* 249 */         format = new SimpleDateFormat(pattern, Locale.US);
/* 250 */         format.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 251 */         formats.put(pattern, format);
/*     */       }
/*     */       
/* 254 */       return format;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/DateUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */