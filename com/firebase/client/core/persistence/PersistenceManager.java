package com.firebase.client.core.persistence;

import com.firebase.client.core.CompoundWrite;
import com.firebase.client.core.Path;
import com.firebase.client.core.UserWriteRecord;
import com.firebase.client.core.view.CacheNode;
import com.firebase.client.core.view.QuerySpec;
import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.Node;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public abstract interface PersistenceManager
{
  public abstract void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong);
  
  public abstract void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong);
  
  public abstract void removeUserWrite(long paramLong);
  
  public abstract void removeAllUserWrites();
  
  public abstract void applyUserWriteToServerCache(Path paramPath, Node paramNode);
  
  public abstract void applyUserWriteToServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract List<UserWriteRecord> loadUserWrites();
  
  public abstract CacheNode serverCache(QuerySpec paramQuerySpec);
  
  public abstract void updateServerCache(QuerySpec paramQuerySpec, Node paramNode);
  
  public abstract void updateServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract void setQueryActive(QuerySpec paramQuerySpec);
  
  public abstract void setQueryInactive(QuerySpec paramQuerySpec);
  
  public abstract void setQueryComplete(QuerySpec paramQuerySpec);
  
  public abstract void setTrackedQueryKeys(QuerySpec paramQuerySpec, Set<ChildKey> paramSet);
  
  public abstract void updateTrackedQueryKeys(QuerySpec paramQuerySpec, Set<ChildKey> paramSet1, Set<ChildKey> paramSet2);
  
  public abstract <T> T runInTransaction(Callable<T> paramCallable);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/PersistenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */