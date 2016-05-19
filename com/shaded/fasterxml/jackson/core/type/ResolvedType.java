package com.shaded.fasterxml.jackson.core.type;

public abstract class ResolvedType
{
  public abstract Class<?> getRawClass();
  
  public abstract boolean hasRawClass(Class<?> paramClass);
  
  public abstract boolean isAbstract();
  
  public abstract boolean isConcrete();
  
  public abstract boolean isThrowable();
  
  public abstract boolean isArrayType();
  
  public abstract boolean isEnumType();
  
  public abstract boolean isInterface();
  
  public abstract boolean isPrimitive();
  
  public abstract boolean isFinal();
  
  public abstract boolean isContainerType();
  
  public abstract boolean isCollectionLikeType();
  
  public abstract boolean isMapLikeType();
  
  public abstract boolean hasGenericTypes();
  
  public abstract ResolvedType getKeyType();
  
  public abstract ResolvedType getContentType();
  
  public abstract int containedTypeCount();
  
  public abstract ResolvedType containedType(int paramInt);
  
  public abstract String containedTypeName(int paramInt);
  
  public abstract String toCanonical();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/type/ResolvedType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */