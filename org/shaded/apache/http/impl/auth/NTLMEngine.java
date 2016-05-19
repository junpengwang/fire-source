package org.shaded.apache.http.impl.auth;

public abstract interface NTLMEngine
{
  public abstract String generateType1Msg(String paramString1, String paramString2)
    throws NTLMEngineException;
  
  public abstract String generateType3Msg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws NTLMEngineException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/NTLMEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */