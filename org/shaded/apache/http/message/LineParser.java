package org.shaded.apache.http.message;

import org.shaded.apache.http.Header;
import org.shaded.apache.http.ParseException;
import org.shaded.apache.http.ProtocolVersion;
import org.shaded.apache.http.RequestLine;
import org.shaded.apache.http.StatusLine;
import org.shaded.apache.http.util.CharArrayBuffer;

public abstract interface LineParser
{
  public abstract ProtocolVersion parseProtocolVersion(CharArrayBuffer paramCharArrayBuffer, ParserCursor paramParserCursor)
    throws ParseException;
  
  public abstract boolean hasProtocolVersion(CharArrayBuffer paramCharArrayBuffer, ParserCursor paramParserCursor);
  
  public abstract RequestLine parseRequestLine(CharArrayBuffer paramCharArrayBuffer, ParserCursor paramParserCursor)
    throws ParseException;
  
  public abstract StatusLine parseStatusLine(CharArrayBuffer paramCharArrayBuffer, ParserCursor paramParserCursor)
    throws ParseException;
  
  public abstract Header parseHeader(CharArrayBuffer paramCharArrayBuffer)
    throws ParseException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/LineParser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */