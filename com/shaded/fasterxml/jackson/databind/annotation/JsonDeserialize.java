package com.shaded.fasterxml.jackson.databind.annotation;

import com.shaded.fasterxml.jackson.annotation.JacksonAnnotation;
import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
import com.shaded.fasterxml.jackson.databind.JsonDeserializer.None;
import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
import com.shaded.fasterxml.jackson.databind.KeyDeserializer.None;
import com.shaded.fasterxml.jackson.databind.util.Converter;
import com.shaded.fasterxml.jackson.databind.util.Converter.None;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonDeserialize
{
  Class<? extends JsonDeserializer<?>> using() default JsonDeserializer.None.class;
  
  Class<? extends JsonDeserializer<?>> contentUsing() default JsonDeserializer.None.class;
  
  Class<? extends KeyDeserializer> keyUsing() default KeyDeserializer.None.class;
  
  Class<?> builder() default NoClass.class;
  
  Class<? extends Converter<?, ?>> converter() default Converter.None.class;
  
  Class<? extends Converter<?, ?>> contentConverter() default Converter.None.class;
  
  Class<?> as() default NoClass.class;
  
  Class<?> keyAs() default NoClass.class;
  
  Class<?> contentAs() default NoClass.class;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/annotation/JsonDeserialize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */