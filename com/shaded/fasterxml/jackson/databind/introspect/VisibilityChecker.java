/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*     */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
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
/*     */ public abstract interface VisibilityChecker<T extends VisibilityChecker<T>>
/*     */ {
/*     */   public abstract T with(JsonAutoDetect paramJsonAutoDetect);
/*     */   
/*     */   public abstract T with(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withGetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withIsGetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withSetterVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withCreatorVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract T withFieldVisibility(JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */   public abstract boolean isGetterVisible(Method paramMethod);
/*     */   
/*     */   public abstract boolean isGetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   public abstract boolean isIsGetterVisible(Method paramMethod);
/*     */   
/*     */   public abstract boolean isIsGetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   public abstract boolean isSetterVisible(Method paramMethod);
/*     */   
/*     */   public abstract boolean isSetterVisible(AnnotatedMethod paramAnnotatedMethod);
/*     */   
/*     */   public abstract boolean isCreatorVisible(Member paramMember);
/*     */   
/*     */   public abstract boolean isCreatorVisible(AnnotatedMember paramAnnotatedMember);
/*     */   
/*     */   public abstract boolean isFieldVisible(Field paramField);
/*     */   
/*     */   public abstract boolean isFieldVisible(AnnotatedField paramAnnotatedField);
/*     */   
/*     */   @JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY, isGetterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY, setterVisibility=JsonAutoDetect.Visibility.ANY, creatorVisibility=JsonAutoDetect.Visibility.ANY, fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
/*     */   public static class Std
/*     */     implements VisibilityChecker<Std>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7073939237187922755L;
/* 172 */     protected static final Std DEFAULT = new Std((JsonAutoDetect)Std.class.getAnnotation(JsonAutoDetect.class));
/*     */     protected final JsonAutoDetect.Visibility _getterMinLevel;
/*     */     protected final JsonAutoDetect.Visibility _isGetterMinLevel;
/*     */     protected final JsonAutoDetect.Visibility _setterMinLevel;
/*     */     protected final JsonAutoDetect.Visibility _creatorMinLevel;
/*     */     protected final JsonAutoDetect.Visibility _fieldMinLevel;
/*     */     
/*     */     public static Std defaultInstance() {
/* 180 */       return DEFAULT;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std(JsonAutoDetect paramJsonAutoDetect)
/*     */     {
/* 191 */       this._getterMinLevel = paramJsonAutoDetect.getterVisibility();
/* 192 */       this._isGetterMinLevel = paramJsonAutoDetect.isGetterVisibility();
/* 193 */       this._setterMinLevel = paramJsonAutoDetect.setterVisibility();
/* 194 */       this._creatorMinLevel = paramJsonAutoDetect.creatorVisibility();
/* 195 */       this._fieldMinLevel = paramJsonAutoDetect.fieldVisibility();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std(JsonAutoDetect.Visibility paramVisibility1, JsonAutoDetect.Visibility paramVisibility2, JsonAutoDetect.Visibility paramVisibility3, JsonAutoDetect.Visibility paramVisibility4, JsonAutoDetect.Visibility paramVisibility5)
/*     */     {
/* 203 */       this._getterMinLevel = paramVisibility1;
/* 204 */       this._isGetterMinLevel = paramVisibility2;
/* 205 */       this._setterMinLevel = paramVisibility3;
/* 206 */       this._creatorMinLevel = paramVisibility4;
/* 207 */       this._fieldMinLevel = paramVisibility5;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 219 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) {
/* 220 */         this._getterMinLevel = DEFAULT._getterMinLevel;
/* 221 */         this._isGetterMinLevel = DEFAULT._isGetterMinLevel;
/* 222 */         this._setterMinLevel = DEFAULT._setterMinLevel;
/* 223 */         this._creatorMinLevel = DEFAULT._creatorMinLevel;
/* 224 */         this._fieldMinLevel = DEFAULT._fieldMinLevel;
/*     */       } else {
/* 226 */         this._getterMinLevel = paramVisibility;
/* 227 */         this._isGetterMinLevel = paramVisibility;
/* 228 */         this._setterMinLevel = paramVisibility;
/* 229 */         this._creatorMinLevel = paramVisibility;
/* 230 */         this._fieldMinLevel = paramVisibility;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std with(JsonAutoDetect paramJsonAutoDetect)
/*     */     {
/* 244 */       Std localStd = this;
/* 245 */       if (paramJsonAutoDetect != null) {
/* 246 */         localStd = localStd.withGetterVisibility(paramJsonAutoDetect.getterVisibility());
/* 247 */         localStd = localStd.withIsGetterVisibility(paramJsonAutoDetect.isGetterVisibility());
/* 248 */         localStd = localStd.withSetterVisibility(paramJsonAutoDetect.setterVisibility());
/* 249 */         localStd = localStd.withCreatorVisibility(paramJsonAutoDetect.creatorVisibility());
/* 250 */         localStd = localStd.withFieldVisibility(paramJsonAutoDetect.fieldVisibility());
/*     */       }
/* 252 */       return localStd;
/*     */     }
/*     */     
/*     */ 
/*     */     public Std with(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 258 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) {
/* 259 */         return DEFAULT;
/*     */       }
/* 261 */       return new Std(paramVisibility);
/*     */     }
/*     */     
/*     */ 
/*     */     public Std withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 267 */       switch (VisibilityChecker.1.$SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[paramPropertyAccessor.ordinal()]) {
/*     */       case 1: 
/* 269 */         return withGetterVisibility(paramVisibility);
/*     */       case 2: 
/* 271 */         return withSetterVisibility(paramVisibility);
/*     */       case 3: 
/* 273 */         return withCreatorVisibility(paramVisibility);
/*     */       case 4: 
/* 275 */         return withFieldVisibility(paramVisibility);
/*     */       case 5: 
/* 277 */         return withIsGetterVisibility(paramVisibility);
/*     */       case 6: 
/* 279 */         return with(paramVisibility);
/*     */       }
/*     */       
/*     */       
/* 283 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */     public Std withGetterVisibility(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 289 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) paramVisibility = DEFAULT._getterMinLevel;
/* 290 */       if (this._getterMinLevel == paramVisibility) return this;
/* 291 */       return new Std(paramVisibility, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */     
/*     */     public Std withIsGetterVisibility(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 296 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) paramVisibility = DEFAULT._isGetterMinLevel;
/* 297 */       if (this._isGetterMinLevel == paramVisibility) return this;
/* 298 */       return new Std(this._getterMinLevel, paramVisibility, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */     
/*     */     public Std withSetterVisibility(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 303 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) paramVisibility = DEFAULT._setterMinLevel;
/* 304 */       if (this._setterMinLevel == paramVisibility) return this;
/* 305 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, paramVisibility, this._creatorMinLevel, this._fieldMinLevel);
/*     */     }
/*     */     
/*     */     public Std withCreatorVisibility(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 310 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) paramVisibility = DEFAULT._creatorMinLevel;
/* 311 */       if (this._creatorMinLevel == paramVisibility) return this;
/* 312 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, paramVisibility, this._fieldMinLevel);
/*     */     }
/*     */     
/*     */     public Std withFieldVisibility(JsonAutoDetect.Visibility paramVisibility)
/*     */     {
/* 317 */       if (paramVisibility == JsonAutoDetect.Visibility.DEFAULT) paramVisibility = DEFAULT._fieldMinLevel;
/* 318 */       if (this._fieldMinLevel == paramVisibility) return this;
/* 319 */       return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, paramVisibility);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isCreatorVisible(Member paramMember)
/*     */     {
/* 330 */       return this._creatorMinLevel.isVisible(paramMember);
/*     */     }
/*     */     
/*     */     public boolean isCreatorVisible(AnnotatedMember paramAnnotatedMember)
/*     */     {
/* 335 */       return isCreatorVisible(paramAnnotatedMember.getMember());
/*     */     }
/*     */     
/*     */     public boolean isFieldVisible(Field paramField)
/*     */     {
/* 340 */       return this._fieldMinLevel.isVisible(paramField);
/*     */     }
/*     */     
/*     */     public boolean isFieldVisible(AnnotatedField paramAnnotatedField)
/*     */     {
/* 345 */       return isFieldVisible(paramAnnotatedField.getAnnotated());
/*     */     }
/*     */     
/*     */     public boolean isGetterVisible(Method paramMethod)
/*     */     {
/* 350 */       return this._getterMinLevel.isVisible(paramMethod);
/*     */     }
/*     */     
/*     */     public boolean isGetterVisible(AnnotatedMethod paramAnnotatedMethod)
/*     */     {
/* 355 */       return isGetterVisible(paramAnnotatedMethod.getAnnotated());
/*     */     }
/*     */     
/*     */     public boolean isIsGetterVisible(Method paramMethod)
/*     */     {
/* 360 */       return this._isGetterMinLevel.isVisible(paramMethod);
/*     */     }
/*     */     
/*     */     public boolean isIsGetterVisible(AnnotatedMethod paramAnnotatedMethod)
/*     */     {
/* 365 */       return isIsGetterVisible(paramAnnotatedMethod.getAnnotated());
/*     */     }
/*     */     
/*     */     public boolean isSetterVisible(Method paramMethod)
/*     */     {
/* 370 */       return this._setterMinLevel.isVisible(paramMethod);
/*     */     }
/*     */     
/*     */     public boolean isSetterVisible(AnnotatedMethod paramAnnotatedMethod)
/*     */     {
/* 375 */       return isSetterVisible(paramAnnotatedMethod.getAnnotated());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 386 */       return "[Visibility:" + " getter: " + this._getterMinLevel + ", isGetter: " + this._isGetterMinLevel + ", setter: " + this._setterMinLevel + ", creator: " + this._creatorMinLevel + ", field: " + this._fieldMinLevel + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/VisibilityChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */