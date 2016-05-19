/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.Versioned;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class VersionUtil
/*     */ {
/*     */   @Deprecated
/*     */   public static final String VERSION_FILE = "VERSION.txt";
/*     */   public static final String PACKAGE_VERSION_CLASS_NAME = "PackageVersion";
/*  34 */   private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Version _version;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected VersionUtil()
/*     */   {
/*  46 */     Version localVersion = null;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  51 */       localVersion = versionFor(getClass());
/*     */     } catch (Exception localException) {
/*  53 */       System.err.println("ERROR: Failed to load Version information for bundle (via " + getClass().getName() + ").");
/*     */     }
/*  55 */     if (localVersion == null) {
/*  56 */       localVersion = Version.unknownVersion();
/*     */     }
/*  58 */     this._version = localVersion;
/*     */   }
/*     */   
/*  61 */   public Version version() { return this._version; }
/*     */   
/*     */   /* Error */
/*     */   public static Version versionFor(Class<?> paramClass)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokestatic 79	com/shaded/fasterxml/jackson/core/util/VersionUtil:packageVersionFor	(Ljava/lang/Class;)Lcom/shaded/fasterxml/jackson/core/Version;
/*     */     //   4: astore_1
/*     */     //   5: aload_1
/*     */     //   6: ifnull +5 -> 11
/*     */     //   9: aload_1
/*     */     //   10: areturn
/*     */     //   11: aload_0
/*     */     //   12: ldc 9
/*     */     //   14: invokevirtual 83	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
/*     */     //   17: astore_2
/*     */     //   18: aload_2
/*     */     //   19: ifnonnull +7 -> 26
/*     */     //   22: invokestatic 69	com/shaded/fasterxml/jackson/core/Version:unknownVersion	()Lcom/shaded/fasterxml/jackson/core/Version;
/*     */     //   25: areturn
/*     */     //   26: new 87	java/io/InputStreamReader
/*     */     //   29: dup
/*     */     //   30: aload_2
/*     */     //   31: ldc 89
/*     */     //   33: invokespecial 92	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/*     */     //   36: astore_3
/*     */     //   37: aload_3
/*     */     //   38: invokestatic 96	com/shaded/fasterxml/jackson/core/util/VersionUtil:doReadVersion	(Ljava/io/Reader;)Lcom/shaded/fasterxml/jackson/core/Version;
/*     */     //   41: astore 4
/*     */     //   43: aload_3
/*     */     //   44: invokevirtual 99	java/io/InputStreamReader:close	()V
/*     */     //   47: goto +5 -> 52
/*     */     //   50: astore 5
/*     */     //   52: aload_2
/*     */     //   53: invokevirtual 100	java/io/InputStream:close	()V
/*     */     //   56: goto +15 -> 71
/*     */     //   59: astore 5
/*     */     //   61: new 102	java/lang/RuntimeException
/*     */     //   64: dup
/*     */     //   65: aload 5
/*     */     //   67: invokespecial 105	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
/*     */     //   70: athrow
/*     */     //   71: aload 4
/*     */     //   73: areturn
/*     */     //   74: astore 6
/*     */     //   76: aload_3
/*     */     //   77: invokevirtual 99	java/io/InputStreamReader:close	()V
/*     */     //   80: goto +5 -> 85
/*     */     //   83: astore 7
/*     */     //   85: aload 6
/*     */     //   87: athrow
/*     */     //   88: astore_3
/*     */     //   89: invokestatic 69	com/shaded/fasterxml/jackson/core/Version:unknownVersion	()Lcom/shaded/fasterxml/jackson/core/Version;
/*     */     //   92: astore 4
/*     */     //   94: aload_2
/*     */     //   95: invokevirtual 100	java/io/InputStream:close	()V
/*     */     //   98: goto +15 -> 113
/*     */     //   101: astore 5
/*     */     //   103: new 102	java/lang/RuntimeException
/*     */     //   106: dup
/*     */     //   107: aload 5
/*     */     //   109: invokespecial 105	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
/*     */     //   112: athrow
/*     */     //   113: aload 4
/*     */     //   115: areturn
/*     */     //   116: astore 8
/*     */     //   118: aload_2
/*     */     //   119: invokevirtual 100	java/io/InputStream:close	()V
/*     */     //   122: goto +15 -> 137
/*     */     //   125: astore 9
/*     */     //   127: new 102	java/lang/RuntimeException
/*     */     //   130: dup
/*     */     //   131: aload 9
/*     */     //   133: invokespecial 105	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
/*     */     //   136: athrow
/*     */     //   137: aload 8
/*     */     //   139: athrow
/*     */     // Line number table:
/*     */     //   Java source line #84	-> byte code offset #0
/*     */     //   Java source line #85	-> byte code offset #5
/*     */     //   Java source line #86	-> byte code offset #9
/*     */     //   Java source line #89	-> byte code offset #11
/*     */     //   Java source line #91	-> byte code offset #18
/*     */     //   Java source line #92	-> byte code offset #22
/*     */     //   Java source line #95	-> byte code offset #26
/*     */     //   Java source line #97	-> byte code offset #37
/*     */     //   Java source line #100	-> byte code offset #43
/*     */     //   Java source line #102	-> byte code offset #47
/*     */     //   Java source line #101	-> byte code offset #50
/*     */     //   Java source line #108	-> byte code offset #52
/*     */     //   Java source line #111	-> byte code offset #56
/*     */     //   Java source line #109	-> byte code offset #59
/*     */     //   Java source line #110	-> byte code offset #61
/*     */     //   Java source line #99	-> byte code offset #74
/*     */     //   Java source line #100	-> byte code offset #76
/*     */     //   Java source line #102	-> byte code offset #80
/*     */     //   Java source line #101	-> byte code offset #83
/*     */     //   Java source line #102	-> byte code offset #85
/*     */     //   Java source line #104	-> byte code offset #88
/*     */     //   Java source line #105	-> byte code offset #89
/*     */     //   Java source line #108	-> byte code offset #94
/*     */     //   Java source line #111	-> byte code offset #98
/*     */     //   Java source line #109	-> byte code offset #101
/*     */     //   Java source line #110	-> byte code offset #103
/*     */     //   Java source line #107	-> byte code offset #116
/*     */     //   Java source line #108	-> byte code offset #118
/*     */     //   Java source line #111	-> byte code offset #122
/*     */     //   Java source line #109	-> byte code offset #125
/*     */     //   Java source line #110	-> byte code offset #127
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	140	0	paramClass	Class<?>
/*     */     //   4	6	1	localVersion1	Version
/*     */     //   17	102	2	localInputStream	InputStream
/*     */     //   36	41	3	localInputStreamReader	java.io.InputStreamReader
/*     */     //   88	1	3	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
/*     */     //   41	73	4	localVersion2	Version
/*     */     //   50	1	5	localIOException1	IOException
/*     */     //   59	7	5	localIOException2	IOException
/*     */     //   101	7	5	localIOException3	IOException
/*     */     //   74	12	6	localObject1	Object
/*     */     //   83	1	7	localIOException4	IOException
/*     */     //   116	22	8	localObject2	Object
/*     */     //   125	7	9	localIOException5	IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   43	47	50	java/io/IOException
/*     */     //   52	56	59	java/io/IOException
/*     */     //   37	43	74	finally
/*     */     //   74	76	74	finally
/*     */     //   76	80	83	java/io/IOException
/*     */     //   26	52	88	java/io/UnsupportedEncodingException
/*     */     //   74	88	88	java/io/UnsupportedEncodingException
/*     */     //   94	98	101	java/io/IOException
/*     */     //   26	52	116	finally
/*     */     //   74	94	116	finally
/*     */     //   116	118	116	finally
/*     */     //   118	122	125	java/io/IOException
/*     */   }
/*     */   
/*     */   public static Version packageVersionFor(Class<?> paramClass)
/*     */   {
/* 124 */     Class localClass = null;
/*     */     try {
/* 126 */       Package localPackage = paramClass.getPackage();
/* 127 */       String str = localPackage.getName() + "." + "PackageVersion";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 132 */       localClass = Class.forName(str, true, paramClass.getClassLoader());
/*     */     } catch (Exception localException1) {
/* 134 */       return null;
/*     */     }
/* 136 */     if (localClass == null) {
/* 137 */       return null;
/*     */     }
/*     */     Object localObject;
/*     */     try
/*     */     {
/* 142 */       localObject = localClass.newInstance();
/*     */     } catch (RuntimeException localRuntimeException) {
/* 144 */       throw localRuntimeException;
/*     */     } catch (Exception localException2) {
/* 146 */       throw new IllegalArgumentException("Failed to instantiate " + localClass.getName() + " to find version information, problem: " + localException2.getMessage(), localException2);
/*     */     }
/*     */     
/* 149 */     if (!(localObject instanceof Versioned)) {
/* 150 */       throw new IllegalArgumentException("Bad version class " + localClass.getName() + ": does not implement " + Versioned.class.getName());
/*     */     }
/*     */     
/* 153 */     return ((Versioned)localObject).version();
/*     */   }
/*     */   
/*     */   private static Version doReadVersion(Reader paramReader)
/*     */   {
/* 158 */     String str1 = null;String str2 = null;String str3 = null;
/*     */     
/* 160 */     BufferedReader localBufferedReader = new BufferedReader(paramReader);
/*     */     try {
/* 162 */       str1 = localBufferedReader.readLine();
/* 163 */       if (str1 != null) {
/* 164 */         str2 = localBufferedReader.readLine();
/* 165 */         if (str2 != null) {
/* 166 */           str3 = localBufferedReader.readLine();
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 171 */         localBufferedReader.close();
/*     */       }
/*     */       catch (IOException localIOException1) {}
/*     */       
/*     */ 
/*     */ 
/* 177 */       if (str2 == null) {
/*     */         break label94;
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException2) {}finally
/*     */     {
/*     */       try
/*     */       {
/* 171 */         localBufferedReader.close();
/*     */       }
/*     */       catch (IOException localIOException4) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 178 */     str2 = str2.trim();
/* 179 */     label94: if (str3 != null)
/* 180 */       str3 = str3.trim();
/* 181 */     return parseVersion(str1, str2, str3);
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
/*     */   public static Version mavenVersionFor(ClassLoader paramClassLoader, String paramString1, String paramString2)
/*     */   {
/* 196 */     InputStream localInputStream = paramClassLoader.getResourceAsStream("META-INF/maven/" + paramString1.replaceAll("\\.", "/") + "/" + paramString2 + "/pom.properties");
/*     */     
/* 198 */     if (localInputStream != null) {
/*     */       try {
/* 200 */         Properties localProperties = new Properties();
/* 201 */         localProperties.load(localInputStream);
/* 202 */         String str1 = localProperties.getProperty("version");
/* 203 */         String str2 = localProperties.getProperty("artifactId");
/* 204 */         String str3 = localProperties.getProperty("groupId");
/* 205 */         return parseVersion(str1, str3, str2);
/*     */       }
/*     */       catch (IOException localIOException1) {}finally
/*     */       {
/*     */         try {
/* 210 */           localInputStream.close();
/*     */         }
/*     */         catch (IOException localIOException4) {}
/*     */       }
/*     */     }
/*     */     
/* 216 */     return Version.unknownVersion();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Version parseVersion(String paramString)
/*     */   {
/* 226 */     return parseVersion(paramString, null, null);
/*     */   }
/*     */   
/*     */   public static Version parseVersion(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 231 */     if (paramString1 == null) {
/* 232 */       return null;
/*     */     }
/* 234 */     paramString1 = paramString1.trim();
/* 235 */     if (paramString1.length() == 0) {
/* 236 */       return null;
/*     */     }
/* 238 */     String[] arrayOfString = VERSION_SEPARATOR.split(paramString1);
/* 239 */     int i = parseVersionPart(arrayOfString[0]);
/* 240 */     int j = arrayOfString.length > 1 ? parseVersionPart(arrayOfString[1]) : 0;
/* 241 */     int k = arrayOfString.length > 2 ? parseVersionPart(arrayOfString[2]) : 0;
/* 242 */     String str = arrayOfString.length > 3 ? arrayOfString[3] : null;
/*     */     
/* 244 */     return new Version(i, j, k, str, paramString2, paramString3);
/*     */   }
/*     */   
/*     */ 
/*     */   protected static int parseVersionPart(String paramString)
/*     */   {
/* 250 */     paramString = paramString.toString();
/* 251 */     int i = paramString.length();
/* 252 */     int j = 0;
/* 253 */     for (int k = 0; k < i; k++) {
/* 254 */       int m = paramString.charAt(k);
/* 255 */       if ((m > 57) || (m < 48)) break;
/* 256 */       j = j * 10 + (m - 48);
/*     */     }
/* 258 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final void throwInternal()
/*     */   {
/* 268 */     throw new RuntimeException("Internal error: this code path should never get executed");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/VersionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */