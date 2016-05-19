package com.firebase.client.core.view.filter;

import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.Index;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.NamedNode;
import com.firebase.client.snapshot.Node;

public abstract interface NodeFilter
{
  public abstract IndexedNode updateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator);
  
  public abstract IndexedNode updateFullNode(IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2, ChildChangeAccumulator paramChildChangeAccumulator);
  
  public abstract IndexedNode updatePriority(IndexedNode paramIndexedNode, Node paramNode);
  
  public abstract boolean filtersNodes();
  
  public abstract NodeFilter getIndexedFilter();
  
  public abstract Index getIndex();
  
  public static abstract interface CompleteChildSource
  {
    public abstract Node getCompleteChild(ChildKey paramChildKey);
    
    public abstract NamedNode getChildAfterChild(Index paramIndex, NamedNode paramNamedNode, boolean paramBoolean);
  }
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/filter/NodeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */