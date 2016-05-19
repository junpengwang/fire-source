package com.firebase.client.core.persistence;

import com.firebase.client.core.CompoundWrite;
import com.firebase.client.core.Path;
import com.firebase.client.core.UserWriteRecord;
import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.Node;
import java.util.List;
import java.util.Set;

public abstract interface PersistenceStorageEngine
{
  public abstract void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong);
  
  public abstract void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong);
  
  public abstract void removeUserWrite(long paramLong);
  
  public abstract List<UserWriteRecord> loadUserWrites();
  
  public abstract void removeAllUserWrites();
  
  public abstract Node serverCache(Path paramPath);
  
  public abstract void overwriteServerCache(Path paramPath, Node paramNode);
  
  public abstract void mergeIntoServerCache(Path paramPath, Node paramNode);
  
  public abstract void mergeIntoServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract long serverCacheEstimatedSizeInBytes();
  
  public abstract void saveTrackedQuery(TrackedQuery paramTrackedQuery);
  
  public abstract void deleteTrackedQuery(long paramLong);
  
  public abstract List<TrackedQuery> loadTrackedQueries();
  
  public abstract void resetPreviouslyActiveTrackedQueries(long paramLong);
  
  public abstract void saveTrackedQueryKeys(long paramLong, Set<ChildKey> paramSet);
  
  public abstract void updateTrackedQueryKeys(long paramLong, Set<ChildKey> paramSet1, Set<ChildKey> paramSet2);
  
  public abstract Set<ChildKey> loadTrackedQueryKeys(long paramLong);
  
  public abstract Set<ChildKey> loadTrackedQueryKeys(Set<Long> paramSet);
  
  public abstract void pruneCache(Path paramPath, PruneForest paramPruneForest);
  
  public abstract void beginTransaction();
  
  public abstract void endTransaction();
  
  public abstract void setTransactionSuccessful();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/PersistenceStorageEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */