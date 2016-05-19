package org.shaded.apache.http.message;

import org.shaded.apache.http.HeaderElement;
import org.shaded.apache.http.NameValuePair;
import org.shaded.apache.http.util.CharArrayBuffer;

public abstract interface HeaderValueFormatter
{
  public abstract CharArrayBuffer formatElements(CharArrayBuffer paramCharArrayBuffer, HeaderElement[] paramArrayOfHeaderElement, boolean paramBoolean);
  
  public abstract CharArrayBuffer formatHeaderElement(CharArrayBuffer paramCharArrayBuffer, HeaderElement paramHeaderElement, boolean paramBoolean);
  
  public abstract CharArrayBuffer formatParameters(CharArrayBuffer paramCharArrayBuffer, NameValuePair[] paramArrayOfNameValuePair, boolean paramBoolean);
  
  public abstract CharArrayBuffer formatNameValuePair(CharArrayBuffer paramCharArrayBuffer, NameValuePair paramNameValuePair, boolean paramBoolean);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/HeaderValueFormatter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */