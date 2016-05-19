/*     */ package com.firebase.client;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public class FirebaseError
/*     */ {
/*     */   public static final int DATA_STALE = -1;
/*     */   public static final int OPERATION_FAILED = -2;
/*     */   public static final int PERMISSION_DENIED = -3;
/*     */   public static final int DISCONNECTED = -4;
/*     */   public static final int PREEMPTED = -5;
/*     */   public static final int EXPIRED_TOKEN = -6;
/*     */   public static final int INVALID_TOKEN = -7;
/*     */   public static final int MAX_RETRIES = -8;
/*     */   public static final int OVERRIDDEN_BY_SET = -9;
/*     */   public static final int UNAVAILABLE = -10;
/*     */   public static final int USER_CODE_EXCEPTION = -11;
/*     */   public static final int AUTHENTICATION_PROVIDER_DISABLED = -12;
/*     */   public static final int INVALID_CONFIGURATION = -13;
/*     */   public static final int INVALID_PROVIDER = -14;
/*     */   public static final int INVALID_EMAIL = -15;
/*     */   public static final int INVALID_PASSWORD = -16;
/*     */   public static final int USER_DOES_NOT_EXIST = -17;
/*     */   public static final int EMAIL_TAKEN = -18;
/*     */   public static final int DENIED_BY_USER = -19;
/*     */   public static final int INVALID_CREDENTIALS = -20;
/*     */   public static final int INVALID_AUTH_ARGUMENTS = -21;
/*     */   public static final int PROVIDER_ERROR = -22;
/*     */   public static final int LIMITS_EXCEEDED = -23;
/*     */   public static final int NETWORK_ERROR = -24;
/*     */   public static final int WRITE_CANCELED = -25;
/*     */   public static final int UNKNOWN_ERROR = -999;
/* 137 */   private static final Map<Integer, String> errorReasons = new HashMap();
/*     */   private static final Map<String, Integer> errorCodes;
/*     */   
/* 140 */   static { errorReasons.put(Integer.valueOf(-1), "The transaction needs to be run again with current data");
/* 141 */     errorReasons.put(Integer.valueOf(-2), "The server indicated that this operation failed");
/* 142 */     errorReasons.put(Integer.valueOf(-3), "This client does not have permission to perform this operation");
/* 143 */     errorReasons.put(Integer.valueOf(-4), "The operation had to be aborted due to a network disconnect");
/* 144 */     errorReasons.put(Integer.valueOf(-5), "The active or pending auth credentials were superseded by another call to auth");
/* 145 */     errorReasons.put(Integer.valueOf(-6), "The supplied auth token has expired");
/* 146 */     errorReasons.put(Integer.valueOf(-7), "The supplied auth token was invalid");
/* 147 */     errorReasons.put(Integer.valueOf(-8), "The transaction had too many retries");
/* 148 */     errorReasons.put(Integer.valueOf(-9), "The transaction was overridden by a subsequent set");
/* 149 */     errorReasons.put(Integer.valueOf(-10), "The service is unavailable");
/* 150 */     errorReasons.put(Integer.valueOf(-11), "User code called from the Firebase runloop threw an exception:\n");
/*     */     
/*     */ 
/* 153 */     errorReasons.put(Integer.valueOf(-12), "The specified authentication type is not enabled for this Firebase.");
/* 154 */     errorReasons.put(Integer.valueOf(-13), "The specified authentication type is not properly configured for this Firebase.");
/* 155 */     errorReasons.put(Integer.valueOf(-14), "Invalid provider specified, please check application code.");
/*     */     
/* 157 */     errorReasons.put(Integer.valueOf(-15), "The specified email address is incorrect.");
/* 158 */     errorReasons.put(Integer.valueOf(-16), "The specified password is incorrect.");
/* 159 */     errorReasons.put(Integer.valueOf(-17), "The specified user does not exist.");
/*     */     
/* 161 */     errorReasons.put(Integer.valueOf(-18), "The specified email address is already in use.");
/*     */     
/* 163 */     errorReasons.put(Integer.valueOf(-19), "User denied authentication request.");
/* 164 */     errorReasons.put(Integer.valueOf(-20), "Invalid authentication credentials provided.");
/* 165 */     errorReasons.put(Integer.valueOf(-21), "Invalid authentication arguments provided.");
/* 166 */     errorReasons.put(Integer.valueOf(-22), "A third-party provider error occurred. See data for details.");
/* 167 */     errorReasons.put(Integer.valueOf(-23), "Limited exceeded. Please contact support@firebase.com.");
/*     */     
/*     */ 
/* 170 */     errorReasons.put(Integer.valueOf(-24), "The operation could not be performed due to a network error");
/* 171 */     errorReasons.put(Integer.valueOf(-25), "The write was canceled by the user.");
/* 172 */     errorReasons.put(Integer.valueOf(64537), "An unknown error ocurred");
/*     */     
/*     */ 
/* 175 */     errorCodes = new HashMap();
/*     */     
/*     */ 
/*     */ 
/* 179 */     errorCodes.put("datastale", Integer.valueOf(-1));
/* 180 */     errorCodes.put("failure", Integer.valueOf(-2));
/* 181 */     errorCodes.put("permission_denied", Integer.valueOf(-3));
/* 182 */     errorCodes.put("disconnected", Integer.valueOf(-4));
/* 183 */     errorCodes.put("preempted", Integer.valueOf(-5));
/* 184 */     errorCodes.put("expired_token", Integer.valueOf(-6));
/* 185 */     errorCodes.put("invalid_token", Integer.valueOf(-7));
/* 186 */     errorCodes.put("maxretries", Integer.valueOf(-8));
/* 187 */     errorCodes.put("overriddenbyset", Integer.valueOf(-9));
/* 188 */     errorCodes.put("unavailable", Integer.valueOf(-10));
/*     */     
/*     */ 
/* 191 */     errorCodes.put("authentication_disabled", Integer.valueOf(-12));
/* 192 */     errorCodes.put("invalid_configuration", Integer.valueOf(-13));
/* 193 */     errorCodes.put("invalid_provider", Integer.valueOf(-14));
/*     */     
/* 195 */     errorCodes.put("invalid_email", Integer.valueOf(-15));
/* 196 */     errorCodes.put("invalid_password", Integer.valueOf(-16));
/* 197 */     errorCodes.put("invalid_user", Integer.valueOf(-17));
/*     */     
/* 199 */     errorCodes.put("email_taken", Integer.valueOf(-18));
/*     */     
/* 201 */     errorCodes.put("user_denied", Integer.valueOf(-19));
/* 202 */     errorCodes.put("invalid_credentials", Integer.valueOf(-20));
/* 203 */     errorCodes.put("invalid_arguments", Integer.valueOf(-21));
/* 204 */     errorCodes.put("provider_error", Integer.valueOf(-22));
/* 205 */     errorCodes.put("limits_exceeded", Integer.valueOf(-23));
/*     */     
/*     */ 
/* 208 */     errorCodes.put("network_error", Integer.valueOf(-24));
/* 209 */     errorCodes.put("write_canceled", Integer.valueOf(-25));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FirebaseError fromStatus(String status)
/*     */   {
/* 218 */     return fromStatus(status, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final int code;
/*     */   
/*     */ 
/*     */   public static FirebaseError fromStatus(String status, String reason)
/*     */   {
/* 228 */     return fromStatus(status, reason, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FirebaseError fromCode(int code)
/*     */   {
/* 237 */     if (!errorReasons.containsKey(Integer.valueOf(code))) {
/* 238 */       throw new IllegalArgumentException("Invalid Firebase error code: " + code);
/*     */     }
/* 240 */     String message = (String)errorReasons.get(Integer.valueOf(code));
/* 241 */     return new FirebaseError(code, message, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final String message;
/*     */   
/*     */   private final String details;
/*     */   
/*     */   public static FirebaseError fromStatus(String status, String reason, String details)
/*     */   {
/* 252 */     Integer code = (Integer)errorCodes.get(status.toLowerCase());
/* 253 */     if (code == null) {
/* 254 */       code = Integer.valueOf(64537);
/*     */     }
/*     */     
/* 257 */     String message = reason == null ? (String)errorReasons.get(code) : reason;
/* 258 */     return new FirebaseError(code.intValue(), message, details);
/*     */   }
/*     */   
/*     */   public static FirebaseError fromException(Throwable e) {
/* 262 */     StringWriter stringWriter = new StringWriter();
/* 263 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/* 264 */     e.printStackTrace(printWriter);
/* 265 */     String reason = (String)errorReasons.get(Integer.valueOf(-11)) + stringWriter.toString();
/* 266 */     return new FirebaseError(-11, reason);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FirebaseError(int code, String message)
/*     */   {
/* 274 */     this(code, message, null);
/*     */   }
/*     */   
/*     */   public FirebaseError(int code, String message, String details) {
/* 278 */     this.code = code;
/* 279 */     this.message = message;
/* 280 */     this.details = (details == null ? "" : details);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getCode()
/*     */   {
/* 287 */     return this.code;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 294 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDetails()
/*     */   {
/* 301 */     return this.details;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 306 */     return "FirebaseError: " + this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FirebaseException toException()
/*     */   {
/* 314 */     return new FirebaseException("Firebase error: " + this.message);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/FirebaseError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */