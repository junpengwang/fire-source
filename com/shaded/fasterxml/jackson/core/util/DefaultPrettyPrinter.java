/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*     */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class DefaultPrettyPrinter
/*     */   implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5512586643324525213L;
/*  29 */   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
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
/*  54 */   protected Indenter _arrayIndenter = FixedSpaceIndenter.instance;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  62 */   protected Indenter _objectIndenter = Lf2SpacesIndenter.instance;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializableString _rootSeparator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  76 */   protected boolean _spacesInObjectEntries = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */   protected transient int _nesting = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultPrettyPrinter()
/*     */   {
/*  93 */     this(DEFAULT_ROOT_VALUE_SEPARATOR);
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
/*     */   public DefaultPrettyPrinter(String paramString)
/*     */   {
/* 108 */     this(paramString == null ? null : new SerializedString(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultPrettyPrinter(SerializableString paramSerializableString)
/*     */   {
/* 120 */     this._rootSeparator = paramSerializableString;
/*     */   }
/*     */   
/*     */   public DefaultPrettyPrinter(DefaultPrettyPrinter paramDefaultPrettyPrinter) {
/* 124 */     this(paramDefaultPrettyPrinter, paramDefaultPrettyPrinter._rootSeparator);
/*     */   }
/*     */   
/*     */ 
/*     */   public DefaultPrettyPrinter(DefaultPrettyPrinter paramDefaultPrettyPrinter, SerializableString paramSerializableString)
/*     */   {
/* 130 */     this._arrayIndenter = paramDefaultPrettyPrinter._arrayIndenter;
/* 131 */     this._objectIndenter = paramDefaultPrettyPrinter._objectIndenter;
/* 132 */     this._spacesInObjectEntries = paramDefaultPrettyPrinter._spacesInObjectEntries;
/* 133 */     this._nesting = paramDefaultPrettyPrinter._nesting;
/*     */     
/* 135 */     this._rootSeparator = paramSerializableString;
/*     */   }
/*     */   
/*     */   public DefaultPrettyPrinter withRootSeparator(SerializableString paramSerializableString)
/*     */   {
/* 140 */     if ((this._rootSeparator == paramSerializableString) || ((paramSerializableString != null) && (paramSerializableString.equals(this._rootSeparator))))
/*     */     {
/* 142 */       return this;
/*     */     }
/* 144 */     return new DefaultPrettyPrinter(this, paramSerializableString);
/*     */   }
/*     */   
/*     */   public void indentArraysWith(Indenter paramIndenter)
/*     */   {
/* 149 */     this._arrayIndenter = (paramIndenter == null ? NopIndenter.instance : paramIndenter);
/*     */   }
/*     */   
/*     */   public void indentObjectsWith(Indenter paramIndenter)
/*     */   {
/* 154 */     this._objectIndenter = (paramIndenter == null ? NopIndenter.instance : paramIndenter);
/*     */   }
/*     */   
/* 157 */   public void spacesInObjectEntries(boolean paramBoolean) { this._spacesInObjectEntries = paramBoolean; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultPrettyPrinter createInstance()
/*     */   {
/* 167 */     return new DefaultPrettyPrinter(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeRootValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 180 */     if (this._rootSeparator != null) {
/* 181 */       paramJsonGenerator.writeRaw(this._rootSeparator);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeStartObject(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 189 */     paramJsonGenerator.writeRaw('{');
/* 190 */     if (!this._objectIndenter.isInline()) {
/* 191 */       this._nesting += 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void beforeObjectEntries(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 199 */     this._objectIndenter.writeIndentation(paramJsonGenerator, this._nesting);
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
/*     */   public void writeObjectFieldValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 215 */     if (this._spacesInObjectEntries) {
/* 216 */       paramJsonGenerator.writeRaw(" : ");
/*     */     } else {
/* 218 */       paramJsonGenerator.writeRaw(':');
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeObjectEntrySeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 235 */     paramJsonGenerator.writeRaw(',');
/* 236 */     this._objectIndenter.writeIndentation(paramJsonGenerator, this._nesting);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEndObject(JsonGenerator paramJsonGenerator, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 243 */     if (!this._objectIndenter.isInline()) {
/* 244 */       this._nesting -= 1;
/*     */     }
/* 246 */     if (paramInt > 0) {
/* 247 */       this._objectIndenter.writeIndentation(paramJsonGenerator, this._nesting);
/*     */     } else {
/* 249 */       paramJsonGenerator.writeRaw(' ');
/*     */     }
/* 251 */     paramJsonGenerator.writeRaw('}');
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeStartArray(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 258 */     if (!this._arrayIndenter.isInline()) {
/* 259 */       this._nesting += 1;
/*     */     }
/* 261 */     paramJsonGenerator.writeRaw('[');
/*     */   }
/*     */   
/*     */ 
/*     */   public void beforeArrayValues(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 268 */     this._arrayIndenter.writeIndentation(paramJsonGenerator, this._nesting);
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
/*     */   public void writeArrayValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 284 */     paramJsonGenerator.writeRaw(',');
/* 285 */     this._arrayIndenter.writeIndentation(paramJsonGenerator, this._nesting);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEndArray(JsonGenerator paramJsonGenerator, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 292 */     if (!this._arrayIndenter.isInline()) {
/* 293 */       this._nesting -= 1;
/*     */     }
/* 295 */     if (paramInt > 0) {
/* 296 */       this._arrayIndenter.writeIndentation(paramJsonGenerator, this._nesting);
/*     */     } else {
/* 298 */       paramJsonGenerator.writeRaw(' ');
/*     */     }
/* 300 */     paramJsonGenerator.writeRaw(']');
/*     */   }
/*     */   
/*     */ 
/*     */   public static abstract interface Indenter
/*     */   {
/*     */     public abstract void writeIndentation(JsonGenerator paramJsonGenerator, int paramInt)
/*     */       throws IOException, JsonGenerationException;
/*     */     
/*     */     public abstract boolean isInline();
/*     */   }
/*     */   
/*     */   public static class NopIndenter
/*     */     implements DefaultPrettyPrinter.Indenter, Serializable
/*     */   {
/* 315 */     public static final NopIndenter instance = new NopIndenter();
/*     */     
/*     */     public void writeIndentation(JsonGenerator paramJsonGenerator, int paramInt)
/*     */       throws IOException, JsonGenerationException
/*     */     {}
/*     */     
/*     */     public boolean isInline()
/*     */     {
/* 323 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class FixedSpaceIndenter
/*     */     extends DefaultPrettyPrinter.NopIndenter
/*     */   {
/* 334 */     public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();
/*     */     
/*     */ 
/*     */     public void writeIndentation(JsonGenerator paramJsonGenerator, int paramInt)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 340 */       paramJsonGenerator.writeRaw(' ');
/*     */     }
/*     */     
/*     */     public boolean isInline() {
/* 344 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Lf2SpacesIndenter
/*     */     extends DefaultPrettyPrinter.NopIndenter
/*     */   {
/* 354 */     public static final Lf2SpacesIndenter instance = new Lf2SpacesIndenter();
/*     */     private static final String SYS_LF;
/*     */     
/*     */     static {
/* 358 */       String str = null;
/*     */       try {
/* 360 */         str = System.getProperty("line.separator");
/*     */       } catch (Throwable localThrowable) {}
/* 362 */       SYS_LF = str == null ? "\n" : str;
/*     */       
/*     */ 
/*     */ 
/* 366 */       SPACES = new char[64];
/*     */       
/* 368 */       Arrays.fill(SPACES, ' '); }
/*     */     
/*     */     static final int SPACE_COUNT = 64;
/*     */     static final char[] SPACES;
/* 372 */     public boolean isInline() { return false; }
/*     */     
/*     */ 
/*     */     public void writeIndentation(JsonGenerator paramJsonGenerator, int paramInt)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 378 */       paramJsonGenerator.writeRaw(SYS_LF);
/* 379 */       if (paramInt > 0) {
/* 380 */         paramInt += paramInt;
/* 381 */         while (paramInt > 64) {
/* 382 */           paramJsonGenerator.writeRaw(SPACES, 0, 64);
/* 383 */           paramInt -= SPACES.length;
/*     */         }
/* 385 */         paramJsonGenerator.writeRaw(SPACES, 0, paramInt);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/DefaultPrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */