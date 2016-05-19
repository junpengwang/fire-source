package org.shaded.apache.http.message;

import org.shaded.apache.http.Header;
import org.shaded.apache.http.ProtocolVersion;
import org.shaded.apache.http.RequestLine;
import org.shaded.apache.http.StatusLine;
import org.shaded.apache.http.util.CharArrayBuffer;

public abstract interface LineFormatter
{
  public abstract CharArrayBuffer appendProtocolVersion(CharArrayBuffer paramCharArrayBuffer, ProtocolVersion paramProtocolVersion);
  
  public abstract CharArrayBuffer formatRequestLine(CharArrayBuffer paramCharArrayBuffer, RequestLine paramRequestLine);
  
  public abstract CharArrayBuffer formatStatusLine(CharArrayBuffer paramCharArrayBuffer, StatusLine paramStatusLine);
  
  public abstract CharArrayBuffer formatHeader(CharArrayBuffer paramCharArrayBuffer, Header paramHeader);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/LineFormatter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */