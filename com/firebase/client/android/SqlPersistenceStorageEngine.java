/*     */ package com.firebase.client.android;
/*     */ 
/*     */ import android.content.ContentValues;
/*     */ import android.database.Cursor;
/*     */ import android.database.sqlite.SQLiteDatabase;
/*     */ import android.database.sqlite.SQLiteOpenHelper;
/*     */ import com.firebase.client.core.CompoundWrite;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.UserWriteRecord;
/*     */ import com.firebase.client.core.persistence.PersistenceStorageEngine;
/*     */ import com.firebase.client.core.persistence.PruneForest;
/*     */ import com.firebase.client.core.persistence.TrackedQuery;
/*     */ import com.firebase.client.core.utilities.ImmutableTree;
/*     */ import com.firebase.client.core.utilities.ImmutableTree.TreeVisitor;
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.ChildrenNode;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import com.firebase.client.utilities.NodeSizeEstimator;
/*     */ import com.firebase.client.utilities.Pair;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public class SqlPersistenceStorageEngine
/*     */   implements PersistenceStorageEngine
/*     */ {
/*     */   private static final String createServerCache = "CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);";
/*     */   private static final String SERVER_CACHE_TABLE = "serverCache";
/*     */   private static final String PATH_COLUMN_NAME = "path";
/*     */   private static final String VALUE_COLUMN_NAME = "value";
/*     */   private static final String createWrites = "CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));";
/*     */   private static final String WRITES_TABLE = "writes";
/*     */   private static final String WRITE_ID_COLUMN_NAME = "id";
/*     */   private static final String WRITE_NODE_COLUMN_NAME = "node";
/*     */   private static final String WRITE_PART_COLUMN_NAME = "part";
/*     */   private static final String WRITE_TYPE_COLUMN_NAME = "type";
/*     */   private static final String WRITE_TYPE_OVERWRITE = "o";
/*     */   private static final String WRITE_TYPE_MERGE = "m";
/*     */   private static final String createTrackedQueries = "CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);";
/*     */   private static final String TRACKED_QUERY_TABLE = "trackedQueries";
/*     */   private static final String TRACKED_QUERY_ID_COLUMN_NAME = "id";
/*     */   private static final String TRACKED_QUERY_PATH_COLUMN_NAME = "path";
/*     */   private static final String TRACKED_QUERY_PARAMS_COLUMN_NAME = "queryParams";
/*     */   private static final String TRACKED_QUERY_LAST_USE_COLUMN_NAME = "lastUse";
/*     */   private static final String TRACKED_QUERY_COMPLETE_COLUMN_NAME = "complete";
/*     */   private static final String TRACKED_QUERY_ACTIVE_COLUMN_NAME = "active";
/*     */   private static final String createTrackedKeys = "CREATE TABLE trackedKeys (id INTEGER, key TEXT);";
/*     */   private static final String TRACKED_KEYS_TABLE = "trackedKeys";
/*     */   private static final String TRACKED_KEYS_ID_COLUMN_NAME = "id";
/*     */   private static final String TRACKED_KEYS_KEY_COLUMN_NAME = "key";
/*     */   private static final String ROW_ID_COLUMN_NAME = "rowid";
/*     */   private static final int CHILDREN_NODE_SPLIT_SIZE_THRESHOLD = 16384;
/*     */   private static final int ROW_SPLIT_SIZE = 262144;
/*     */   private static final String PART_KEY_FORMAT = ".part-%04d";
/*     */   private static final String FIRST_PART_KEY = ".part-0000";
/*     */   private static final String PART_KEY_PREFIX = ".part-";
/*     */   private static final String LOGGER_COMPONENT = "Persistence";
/*     */   private final SQLiteDatabase database;
/*     */   private final ObjectMapper jsonMapper;
/*     */   private final LogWrapper logger;
/*     */   private boolean insideTransaction;
/*     */   
/*     */   private static class PersistentCacheOpenHelper
/*     */     extends SQLiteOpenHelper
/*     */   {
/*     */     private static final int DATABASE_VERSION = 2;
/*     */     
/*     */     public PersistentCacheOpenHelper(android.content.Context context, String cacheId)
/*     */     {
/* 143 */       super(cacheId, null, 2);
/*     */     }
/*     */     
/*     */     public void onCreate(SQLiteDatabase db)
/*     */     {
/* 148 */       db.execSQL("CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);");
/* 149 */       db.execSQL("CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));");
/* 150 */       db.execSQL("CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);");
/* 151 */       db.execSQL("CREATE TABLE trackedKeys (id INTEGER, key TEXT);");
/*     */     }
/*     */     
/*     */     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
/*     */     {
/* 156 */       assert (newVersion == 2) : "Why is onUpgrade() called with a different version?";
/* 157 */       if (oldVersion <= 1)
/*     */       {
/*     */ 
/*     */ 
/* 161 */         dropTable(db, "serverCache");
/* 162 */         db.execSQL("CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);");
/*     */         
/*     */ 
/* 165 */         dropTable(db, "complete");
/*     */         
/*     */ 
/* 168 */         db.execSQL("CREATE TABLE trackedKeys (id INTEGER, key TEXT);");
/* 169 */         db.execSQL("CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);");
/*     */       } else {
/* 171 */         throw new AssertionError("We don't handle upgrading to " + newVersion);
/*     */       }
/*     */     }
/*     */     
/*     */     private void dropTable(SQLiteDatabase db, String table) {
/* 176 */       db.execSQL("DROP TABLE IF EXISTS " + table);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */   private long transactionStart = 0L;
/*     */   
/*     */   public SqlPersistenceStorageEngine(android.content.Context context, com.firebase.client.core.Context firebaseContext, String cacheId) {
/*     */     String sanitizedCacheId;
/*     */     try {
/* 191 */       sanitizedCacheId = URLEncoder.encode(cacheId, "utf-8");
/*     */     } catch (IOException e) {
/* 193 */       throw new RuntimeException(e);
/*     */     }
/* 195 */     PersistentCacheOpenHelper helper = new PersistentCacheOpenHelper(context, sanitizedCacheId);
/*     */     
/*     */ 
/*     */ 
/* 199 */     this.database = helper.getWritableDatabase();
/* 200 */     this.jsonMapper = new ObjectMapper();
/* 201 */     this.logger = firebaseContext.getLogger("Persistence");
/*     */   }
/*     */   
/*     */   public void saveUserOverwrite(Path path, Node node, long writeId)
/*     */   {
/* 206 */     verifyInsideTransaction();
/* 207 */     long start = System.currentTimeMillis();
/* 208 */     byte[] serializedNode = serializeObject(node.getValue(true));
/* 209 */     saveWrite(path, writeId, "o", serializedNode);
/* 210 */     long duration = System.currentTimeMillis() - start;
/* 211 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Persisted user overwrite in %dms", new Object[] { Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void saveUserMerge(Path path, CompoundWrite children, long writeId)
/*     */   {
/* 216 */     verifyInsideTransaction();
/* 217 */     long start = System.currentTimeMillis();
/* 218 */     byte[] serializedNode = serializeObject(children.getValue(true));
/* 219 */     saveWrite(path, writeId, "m", serializedNode);
/* 220 */     long duration = System.currentTimeMillis() - start;
/* 221 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Persisted user merge in %dms", new Object[] { Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void removeUserWrite(long writeId)
/*     */   {
/* 226 */     verifyInsideTransaction();
/* 227 */     long start = System.currentTimeMillis();
/* 228 */     int count = this.database.delete("writes", "id = ?", new String[] { String.valueOf(writeId) });
/* 229 */     long duration = System.currentTimeMillis() - start;
/* 230 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[] { Integer.valueOf(count), Long.valueOf(writeId), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public List<UserWriteRecord> loadUserWrites()
/*     */   {
/* 235 */     String[] columns = { "id", "path", "type", "part", "node" };
/* 236 */     long start = System.currentTimeMillis();
/* 237 */     Cursor cursor = this.database.query("writes", columns, null, null, null, null, "id, part");
/*     */     
/* 239 */     List<UserWriteRecord> writes = new ArrayList();
/*     */     try { Path path;
/* 241 */       while (cursor.moveToNext()) {
/* 242 */         long writeId = cursor.getLong(0);
/* 243 */         path = new Path(cursor.getString(1));
/* 244 */         String type = cursor.getString(2);
/*     */         byte[] serialized;
/* 246 */         byte[] serialized; if (cursor.isNull(3))
/*     */         {
/* 248 */           serialized = cursor.getBlob(4);
/*     */         }
/*     */         else {
/* 251 */           List<byte[]> parts = new ArrayList();
/*     */           do {
/* 253 */             parts.add(cursor.getBlob(4));
/* 254 */           } while ((cursor.moveToNext()) && (cursor.getLong(0) == writeId));
/*     */           
/* 256 */           cursor.moveToPrevious();
/* 257 */           serialized = joinBytes(parts);
/*     */         }
/* 259 */         Object writeValue = this.jsonMapper.readValue(serialized, Object.class);
/*     */         UserWriteRecord record;
/* 261 */         if ("o".equals(type)) {
/* 262 */           Node set = NodeUtilities.NodeFromJSON(writeValue);
/* 263 */           record = new UserWriteRecord(writeId, path, set, true); } else { UserWriteRecord record;
/* 264 */           if ("m".equals(type))
/*     */           {
/* 266 */             CompoundWrite merge = CompoundWrite.fromValue((Map)writeValue);
/* 267 */             record = new UserWriteRecord(writeId, path, merge);
/*     */           } else {
/* 269 */             throw new IllegalStateException("Got invalid write type: " + type); } }
/*     */         UserWriteRecord record;
/* 271 */         writes.add(record);
/*     */       }
/* 273 */       long duration = System.currentTimeMillis() - start;
/* 274 */       if (this.logger.logsDebug()) this.logger.debug(String.format("Loaded %d writes in %dms", new Object[] { Integer.valueOf(writes.size()), Long.valueOf(duration) }));
/* 275 */       return writes;
/*     */     } catch (IOException e) {
/* 277 */       throw new RuntimeException("Failed to load writes", e);
/*     */     } finally {
/* 279 */       cursor.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveWrite(Path path, long writeId, String type, byte[] serializedWrite) {
/* 284 */     verifyInsideTransaction();
/* 285 */     this.database.delete("writes", "id = ?", new String[] { String.valueOf(writeId) });
/* 286 */     if (serializedWrite.length >= 262144) {
/* 287 */       List<byte[]> parts = splitBytes(serializedWrite, 262144);
/* 288 */       for (int i = 0; i < parts.size(); i++) {
/* 289 */         ContentValues values = new ContentValues();
/* 290 */         values.put("id", Long.valueOf(writeId));
/* 291 */         values.put("path", pathToKey(path));
/* 292 */         values.put("type", type);
/* 293 */         values.put("part", Integer.valueOf(i));
/* 294 */         values.put("node", (byte[])parts.get(i));
/* 295 */         this.database.insertWithOnConflict("writes", null, values, 5);
/*     */       }
/*     */     } else {
/* 298 */       ContentValues values = new ContentValues();
/* 299 */       values.put("id", Long.valueOf(writeId));
/* 300 */       values.put("path", pathToKey(path));
/* 301 */       values.put("type", type);
/* 302 */       values.put("part", (Integer)null);
/* 303 */       values.put("node", serializedWrite);
/* 304 */       this.database.insertWithOnConflict("writes", null, values, 5);
/*     */     }
/*     */   }
/*     */   
/*     */   public Node serverCache(Path path)
/*     */   {
/* 310 */     return loadNested(path);
/*     */   }
/*     */   
/*     */   public void overwriteServerCache(Path path, Node node)
/*     */   {
/* 315 */     verifyInsideTransaction();
/* 316 */     updateServerCache(path, node, false);
/*     */   }
/*     */   
/*     */   public void mergeIntoServerCache(Path path, Node node)
/*     */   {
/* 321 */     verifyInsideTransaction();
/* 322 */     updateServerCache(path, node, true);
/*     */   }
/*     */   
/*     */   private void updateServerCache(Path path, Node node, boolean merge) {
/* 326 */     long start = System.currentTimeMillis();
/*     */     int savedRows;
/*     */     int removedRows;
/* 329 */     int savedRows; if (!merge) {
/* 330 */       int removedRows = removeNested("serverCache", path);
/* 331 */       savedRows = saveNested(path, node);
/*     */     } else {
/* 333 */       removedRows = 0;
/* 334 */       savedRows = 0;
/* 335 */       for (NamedNode child : node) {
/* 336 */         removedRows += removeNested("serverCache", path.child(child.getName()));
/* 337 */         savedRows += saveNested(path.child(child.getName()), child.getNode());
/*     */       }
/*     */     }
/* 340 */     long duration = System.currentTimeMillis() - start;
/* 341 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[] { Integer.valueOf(savedRows), Integer.valueOf(removedRows), path.toString(), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void mergeIntoServerCache(Path path, CompoundWrite children)
/*     */   {
/* 346 */     verifyInsideTransaction();
/* 347 */     long start = System.currentTimeMillis();
/* 348 */     int savedRows = 0;
/* 349 */     int removedRows = 0;
/* 350 */     for (Map.Entry<Path, Node> entry : children) {
/* 351 */       removedRows += removeNested("serverCache", path.child((Path)entry.getKey()));
/* 352 */       savedRows += saveNested(path.child((Path)entry.getKey()), (Node)entry.getValue());
/*     */     }
/* 354 */     long duration = System.currentTimeMillis() - start;
/* 355 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[] { Integer.valueOf(savedRows), Integer.valueOf(removedRows), path.toString(), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public long serverCacheEstimatedSizeInBytes()
/*     */   {
/* 360 */     String query = String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[] { "value", "path", "serverCache" });
/* 361 */     Cursor cursor = this.database.rawQuery(query, null);
/*     */     try {
/* 363 */       if (cursor.moveToFirst()) {
/* 364 */         return cursor.getLong(0);
/*     */       }
/* 366 */       throw new IllegalStateException("Couldn't read database result!");
/*     */     }
/*     */     finally {
/* 369 */       cursor.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveTrackedQuery(TrackedQuery trackedQuery)
/*     */   {
/* 375 */     verifyInsideTransaction();
/* 376 */     long start = System.currentTimeMillis();
/* 377 */     ContentValues values = new ContentValues();
/* 378 */     values.put("id", Long.valueOf(trackedQuery.id));
/* 379 */     values.put("path", pathToKey(trackedQuery.querySpec.getPath()));
/* 380 */     values.put("queryParams", trackedQuery.querySpec.getParams().toJSON());
/* 381 */     values.put("lastUse", Long.valueOf(trackedQuery.lastUse));
/* 382 */     values.put("complete", Boolean.valueOf(trackedQuery.complete));
/* 383 */     values.put("active", Boolean.valueOf(trackedQuery.active));
/* 384 */     this.database.insertWithOnConflict("trackedQueries", null, values, 5);
/* 385 */     long duration = System.currentTimeMillis() - start;
/* 386 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Saved new tracked query in %dms", new Object[] { Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void deleteTrackedQuery(long trackedQueryId)
/*     */   {
/* 391 */     verifyInsideTransaction();
/* 392 */     String trackedQueryIdStr = String.valueOf(trackedQueryId);
/* 393 */     String queriesWhereClause = "id = ?";
/* 394 */     this.database.delete("trackedQueries", queriesWhereClause, new String[] { trackedQueryIdStr });
/*     */     
/* 396 */     String keysWhereClause = "id = ?";
/* 397 */     this.database.delete("trackedKeys", keysWhereClause, new String[] { trackedQueryIdStr });
/*     */   }
/*     */   
/*     */   public List<TrackedQuery> loadTrackedQueries()
/*     */   {
/* 402 */     String[] columns = { "id", "path", "queryParams", "lastUse", "complete", "active" };
/*     */     
/* 404 */     long start = System.currentTimeMillis();
/* 405 */     Cursor cursor = this.database.query("trackedQueries", columns, null, null, null, null, "id");
/*     */     
/* 407 */     List<TrackedQuery> queries = new ArrayList();
/*     */     try { Path path;
/* 409 */       while (cursor.moveToNext()) {
/* 410 */         long id = cursor.getLong(0);
/* 411 */         path = new Path(cursor.getString(1));
/* 412 */         String paramsStr = cursor.getString(2);
/*     */         Object paramsObject;
/*     */         try {
/* 415 */           paramsObject = this.jsonMapper.readValue(paramsStr, Object.class);
/*     */         } catch (IOException e) {
/* 417 */           throw new RuntimeException(e);
/*     */         }
/*     */         
/* 420 */         QuerySpec query = QuerySpec.fromPathAndQueryObject(path, (Map)paramsObject);
/* 421 */         long lastUse = cursor.getLong(3);
/* 422 */         boolean complete = cursor.getInt(4) != 0;
/* 423 */         boolean active = cursor.getInt(5) != 0;
/* 424 */         TrackedQuery trackedQuery = new TrackedQuery(id, query, lastUse, complete, active);
/* 425 */         queries.add(trackedQuery);
/*     */       }
/* 427 */       long duration = System.currentTimeMillis() - start;
/* 428 */       if (this.logger.logsDebug()) this.logger.debug(String.format("Loaded %d tracked queries in %dms", new Object[] { Integer.valueOf(queries.size()), Long.valueOf(duration) }));
/* 429 */       return queries;
/*     */     } finally {
/* 431 */       cursor.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetPreviouslyActiveTrackedQueries(long lastUse)
/*     */   {
/* 437 */     verifyInsideTransaction();
/* 438 */     long start = System.currentTimeMillis();
/*     */     
/* 440 */     String whereClause = "active = 1";
/*     */     
/* 442 */     ContentValues values = new ContentValues();
/* 443 */     values.put("active", Boolean.valueOf(false));
/* 444 */     values.put("lastUse", Long.valueOf(lastUse));
/*     */     
/* 446 */     this.database.updateWithOnConflict("trackedQueries", values, whereClause, new String[0], 5);
/* 447 */     long duration = System.currentTimeMillis() - start;
/* 448 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Reset active tracked queries in %dms", new Object[] { Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void saveTrackedQueryKeys(long trackedQueryId, Set<ChildKey> keys)
/*     */   {
/* 453 */     verifyInsideTransaction();
/* 454 */     long start = System.currentTimeMillis();
/*     */     
/* 456 */     String trackedQueryIdStr = String.valueOf(trackedQueryId);
/* 457 */     String keysWhereClause = "id = ?";
/* 458 */     this.database.delete("trackedKeys", keysWhereClause, new String[] { trackedQueryIdStr });
/*     */     
/* 460 */     for (ChildKey addedKey : keys) {
/* 461 */       ContentValues values = new ContentValues();
/* 462 */       values.put("id", Long.valueOf(trackedQueryId));
/* 463 */       values.put("key", addedKey.asString());
/* 464 */       this.database.insertWithOnConflict("trackedKeys", null, values, 5);
/*     */     }
/*     */     
/* 467 */     long duration = System.currentTimeMillis() - start;
/* 468 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[] { Integer.valueOf(keys.size()), Long.valueOf(trackedQueryId), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void updateTrackedQueryKeys(long trackedQueryId, Set<ChildKey> added, Set<ChildKey> removed)
/*     */   {
/* 473 */     verifyInsideTransaction();
/* 474 */     long start = System.currentTimeMillis();
/* 475 */     String whereClause = "id = ? AND key = ?";
/* 476 */     String trackedQueryIdStr = String.valueOf(trackedQueryId);
/* 477 */     for (ChildKey removedKey : removed) {
/* 478 */       this.database.delete("trackedKeys", whereClause, new String[] { trackedQueryIdStr, removedKey.asString() });
/*     */     }
/* 480 */     for (ChildKey addedKey : added) {
/* 481 */       ContentValues values = new ContentValues();
/* 482 */       values.put("id", Long.valueOf(trackedQueryId));
/* 483 */       values.put("key", addedKey.asString());
/* 484 */       this.database.insertWithOnConflict("trackedKeys", null, values, 5);
/*     */     }
/* 486 */     long duration = System.currentTimeMillis() - start;
/* 487 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[] { Integer.valueOf(added.size()), Integer.valueOf(removed.size()), Long.valueOf(trackedQueryId), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public Set<ChildKey> loadTrackedQueryKeys(long trackedQueryId)
/*     */   {
/* 492 */     return loadTrackedQueryKeys(Collections.singleton(Long.valueOf(trackedQueryId)));
/*     */   }
/*     */   
/*     */   public Set<ChildKey> loadTrackedQueryKeys(Set<Long> trackedQueryIds)
/*     */   {
/* 497 */     String[] columns = { "key" };
/* 498 */     long start = System.currentTimeMillis();
/* 499 */     String whereClause = "id IN (" + commaSeparatedList(trackedQueryIds) + ")";
/* 500 */     Cursor cursor = this.database.query(true, "trackedKeys", columns, whereClause, null, null, null, null, null);
/*     */     
/* 502 */     Set<ChildKey> keys = new HashSet();
/*     */     try {
/* 504 */       while (cursor.moveToNext()) {
/* 505 */         String key = cursor.getString(0);
/* 506 */         keys.add(ChildKey.fromString(key));
/*     */       }
/* 508 */       long duration = System.currentTimeMillis() - start;
/* 509 */       if (this.logger.logsDebug()) this.logger.debug(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[] { Integer.valueOf(keys.size()), trackedQueryIds.toString(), Long.valueOf(duration) }));
/* 510 */       return keys;
/*     */     } finally {
/* 512 */       cursor.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void pruneCache(Path root, PruneForest pruneForest)
/*     */   {
/* 518 */     if (!pruneForest.prunesAnything())
/* 519 */       return;
/* 520 */     verifyInsideTransaction();
/* 521 */     long start = System.currentTimeMillis();
/* 522 */     Cursor cursor = loadNestedQuery(root, new String[] { "rowid", "path" });
/* 523 */     ImmutableTree<Long> rowIdsToPrune = new ImmutableTree(null);
/* 524 */     ImmutableTree<Long> rowIdsToKeep = new ImmutableTree(null);
/* 525 */     while (cursor.moveToNext()) {
/* 526 */       long rowId = cursor.getLong(0);
/* 527 */       Path rowPath = new Path(cursor.getString(1));
/* 528 */       if (!root.contains(rowPath)) {
/* 529 */         this.logger.warn("We are pruning at " + root + " but we have data stored higher up at " + rowPath + ". Ignoring.");
/*     */       } else {
/* 531 */         Path relativePath = Path.getRelative(root, rowPath);
/* 532 */         if (pruneForest.shouldPruneUnkeptDescendants(relativePath)) {
/* 533 */           rowIdsToPrune = rowIdsToPrune.set(relativePath, Long.valueOf(rowId));
/* 534 */         } else if (pruneForest.shouldKeep(relativePath)) {
/* 535 */           rowIdsToKeep = rowIdsToKeep.set(relativePath, Long.valueOf(rowId));
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 541 */           this.logger.warn("We are pruning at " + root + " and have data at " + rowPath + " that isn't marked for pruning or keeping. Ignoring.");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 546 */     int prunedCount = 0;int resavedCount = 0;
/* 547 */     if (!rowIdsToPrune.isEmpty()) {
/* 548 */       List<Pair<Path, Node>> rowsToResave = new ArrayList();
/* 549 */       pruneTreeRecursive(root, Path.getEmptyPath(), rowIdsToPrune, rowIdsToKeep, pruneForest, rowsToResave);
/*     */       
/* 551 */       Collection<Long> rowIdsToDelete = rowIdsToPrune.values();
/* 552 */       String whereClause = "rowid IN (" + commaSeparatedList(rowIdsToDelete) + ")";
/* 553 */       this.database.delete("serverCache", whereClause, null);
/*     */       
/* 555 */       for (Pair<Path, Node> node : rowsToResave) {
/* 556 */         saveNested(root.child((Path)node.getFirst()), (Node)node.getSecond());
/*     */       }
/*     */       
/* 559 */       prunedCount = rowIdsToDelete.size();
/* 560 */       resavedCount = rowsToResave.size();
/*     */     }
/* 562 */     long duration = System.currentTimeMillis() - start;
/* 563 */     if (this.logger.logsDebug()) { this.logger.debug(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[] { Integer.valueOf(prunedCount), Integer.valueOf(resavedCount), Long.valueOf(duration) }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void pruneTreeRecursive(Path pruneRoot, final Path relativePath, ImmutableTree<Long> rowIdsToPrune, final ImmutableTree<Long> rowIdsToKeep, PruneForest pruneForest, final List<Pair<Path, Node>> rowsToResaveAccumulator)
/*     */   {
/* 570 */     if (rowIdsToPrune.getValue() != null)
/*     */     {
/* 572 */       int nodesToResave = ((Integer)pruneForest.foldKeptNodes(Integer.valueOf(0), new ImmutableTree.TreeVisitor()
/*     */       {
/*     */         public Integer onNodeValue(Path keepPath, Void ignore, Integer nodesToResave)
/*     */         {
/* 576 */           return Integer.valueOf(rowIdsToKeep.get(keepPath) == null ? nodesToResave.intValue() + 1 : nodesToResave.intValue());
/*     */         }
/*     */       })).intValue();
/* 579 */       if (nodesToResave > 0) {
/* 580 */         Path absolutePath = pruneRoot.child(relativePath);
/* 581 */         if (this.logger.logsDebug()) this.logger.debug(String.format("Need to rewrite %d nodes below path %s", new Object[] { Integer.valueOf(nodesToResave), absolutePath }));
/* 582 */         final Node currentNode = loadNested(absolutePath);
/* 583 */         pruneForest.foldKeptNodes(null, new ImmutableTree.TreeVisitor()
/*     */         {
/*     */           public Void onNodeValue(Path keepPath, Void ignore, Void ignore2)
/*     */           {
/* 587 */             if (rowIdsToKeep.get(keepPath) == null) {
/* 588 */               rowsToResaveAccumulator.add(new Pair(relativePath.child(keepPath), currentNode.getChild(keepPath)));
/*     */             }
/* 590 */             return null;
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */     else {
/* 596 */       for (Map.Entry<ChildKey, ImmutableTree<Long>> entry : rowIdsToPrune.getChildren()) {
/* 597 */         ChildKey childKey = (ChildKey)entry.getKey();
/* 598 */         PruneForest childPruneForest = pruneForest.child((ChildKey)entry.getKey());
/* 599 */         pruneTreeRecursive(pruneRoot, relativePath.child(childKey), (ImmutableTree)entry.getValue(), rowIdsToKeep.getChild(childKey), childPruneForest, rowsToResaveAccumulator);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeAllUserWrites()
/*     */   {
/* 607 */     verifyInsideTransaction();
/* 608 */     long start = System.currentTimeMillis();
/* 609 */     int count = this.database.delete("writes", null, null);
/* 610 */     long duration = System.currentTimeMillis() - start;
/* 611 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Deleted %d (all) write(s) in %dms", new Object[] { Integer.valueOf(count), Long.valueOf(duration) }));
/*     */   }
/*     */   
/*     */   public void purgeCache()
/*     */   {
/* 616 */     verifyInsideTransaction();
/* 617 */     this.database.delete("serverCache", null, null);
/* 618 */     this.database.delete("writes", null, null);
/* 619 */     this.database.delete("trackedQueries", null, null);
/* 620 */     this.database.delete("trackedKeys", null, null);
/*     */   }
/*     */   
/*     */   public void beginTransaction() {
/* 624 */     Utilities.hardAssert(!this.insideTransaction, "runInTransaction called when an existing transaction is already in progress.");
/* 625 */     if (this.logger.logsDebug()) this.logger.debug("Starting transaction.");
/* 626 */     this.database.beginTransaction();
/* 627 */     this.insideTransaction = true;
/* 628 */     this.transactionStart = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void endTransaction() {
/* 632 */     this.database.endTransaction();
/* 633 */     this.insideTransaction = false;
/* 634 */     long elapsed = System.currentTimeMillis() - this.transactionStart;
/* 635 */     if (this.logger.logsDebug()) this.logger.debug(String.format("Transaction completed. Elapsed: %dms", new Object[] { Long.valueOf(elapsed) }));
/*     */   }
/*     */   
/*     */   public void setTransactionSuccessful() {
/* 639 */     this.database.setTransactionSuccessful();
/*     */   }
/*     */   
/*     */   private void verifyInsideTransaction() {
/* 643 */     Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int saveNested(Path path, Node node)
/*     */   {
/* 655 */     long estimatedSize = NodeSizeEstimator.estimateSerializedNodeSize(node);
/* 656 */     if (((node instanceof ChildrenNode)) && (estimatedSize > 16384L)) {
/* 657 */       if (this.logger.logsDebug()) { this.logger.debug(String.format("Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[] { path, Long.valueOf(estimatedSize), Integer.valueOf(16384) }));
/*     */       }
/* 659 */       int sum = 0;
/* 660 */       for (NamedNode child : node) {
/* 661 */         sum += saveNested(path.child(child.getName()), child.getNode());
/*     */       }
/* 663 */       if (!node.getPriority().isEmpty()) {
/* 664 */         saveNode(path.child(ChildKey.getPriorityKey()), node.getPriority());
/* 665 */         sum++;
/*     */       }
/*     */       
/*     */ 
/* 669 */       saveNode(path, EmptyNode.Empty());
/* 670 */       sum++;
/*     */       
/* 672 */       return sum;
/*     */     }
/* 674 */     saveNode(path, node);
/* 675 */     return 1;
/*     */   }
/*     */   
/*     */   private String partKey(Path path, int i)
/*     */   {
/* 680 */     return pathToKey(path) + String.format(".part-%04d", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */   private void saveNode(Path path, Node node) {
/* 684 */     byte[] serialized = serializeObject(node.getValue(true));
/* 685 */     if (serialized.length >= 262144) {
/* 686 */       List<byte[]> parts = splitBytes(serialized, 262144);
/* 687 */       if (this.logger.logsDebug()) this.logger.debug("Saving huge leaf node with " + parts.size() + " parts.");
/* 688 */       for (int i = 0; i < parts.size(); i++) {
/* 689 */         ContentValues values = new ContentValues();
/* 690 */         values.put("path", partKey(path, i));
/* 691 */         values.put("value", (byte[])parts.get(i));
/* 692 */         this.database.insertWithOnConflict("serverCache", null, values, 5);
/*     */       }
/*     */     } else {
/* 695 */       ContentValues values = new ContentValues();
/* 696 */       values.put("path", pathToKey(path));
/* 697 */       values.put("value", serialized);
/* 698 */       this.database.insertWithOnConflict("serverCache", null, values, 5);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Node loadNested(Path path)
/*     */   {
/* 709 */     List<String> pathStrings = new ArrayList();
/* 710 */     List<byte[]> payloads = new ArrayList();
/*     */     
/* 712 */     long queryStart = System.currentTimeMillis();
/* 713 */     Cursor cursor = loadNestedQuery(path, new String[] { "path", "value" });
/* 714 */     long queryDuration = System.currentTimeMillis() - queryStart;
/* 715 */     long loadingStart = System.currentTimeMillis();
/*     */     try {
/* 717 */       while (cursor.moveToNext()) {
/* 718 */         pathStrings.add(cursor.getString(0));
/* 719 */         payloads.add(cursor.getBlob(1));
/*     */       }
/*     */     } finally {
/* 722 */       cursor.close();
/*     */     }
/* 724 */     long loadingDuration = System.currentTimeMillis() - loadingStart;
/* 725 */     long serializingStart = System.currentTimeMillis();
/*     */     
/* 727 */     Node node = EmptyNode.Empty();
/* 728 */     boolean sawDescendant = false;
/* 729 */     Map<Path, Node> priorities = new HashMap();
/* 730 */     for (int i = 0; i < payloads.size(); i++) {
/*     */       Node savedNode;
/*     */       Path savedPath;
/* 733 */       if (((String)pathStrings.get(i)).endsWith(".part-0000"))
/*     */       {
/*     */ 
/* 736 */         String pathString = (String)pathStrings.get(i);
/* 737 */         Path savedPath = new Path(pathString.substring(0, pathString.length() - ".part-0000".length()));
/* 738 */         int splitNodeRunLength = splitNodeRunLength(savedPath, pathStrings, i);
/* 739 */         if (this.logger.logsDebug()) this.logger.debug("Loading split node with " + splitNodeRunLength + " parts.");
/* 740 */         Node savedNode = deserializeNode(joinBytes(payloads.subList(i, i + splitNodeRunLength)));
/*     */         
/* 742 */         i = i + splitNodeRunLength - 1;
/*     */       } else {
/* 744 */         savedNode = deserializeNode((byte[])payloads.get(i));
/* 745 */         savedPath = new Path((String)pathStrings.get(i));
/*     */       }
/* 747 */       if ((savedPath.getBack() != null) && (savedPath.getBack().isPriorityChildName()))
/*     */       {
/* 749 */         priorities.put(savedPath, savedNode);
/* 750 */       } else if (savedPath.contains(path)) {
/* 751 */         Utilities.hardAssert(!sawDescendant, "Descendants of path must come after ancestors.");
/* 752 */         node = savedNode.getChild(Path.getRelative(savedPath, path));
/* 753 */       } else if (path.contains(savedPath)) {
/* 754 */         sawDescendant = true;
/* 755 */         Path childPath = Path.getRelative(path, savedPath);
/* 756 */         node = node.updateChild(childPath, savedNode);
/*     */       } else {
/* 758 */         throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[] { savedPath, path }));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 763 */     for (Map.Entry<Path, Node> entry : priorities.entrySet()) {
/* 764 */       Path priorityPath = (Path)entry.getKey();
/* 765 */       node = node.updateChild(Path.getRelative(path, priorityPath), (Node)entry.getValue());
/*     */     }
/*     */     
/* 768 */     long serializeDuration = System.currentTimeMillis() - serializingStart;
/* 769 */     long duration = System.currentTimeMillis() - queryStart;
/* 770 */     if (this.logger.logsDebug()) {
/* 771 */       this.logger.debug(String.format("Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[] { Integer.valueOf(payloads.size()), Integer.valueOf(NodeSizeEstimator.nodeCount(node)), path, Long.valueOf(duration), Long.valueOf(queryDuration), Long.valueOf(loadingDuration), Long.valueOf(serializeDuration) }));
/*     */     }
/*     */     
/*     */ 
/* 775 */     return node;
/*     */   }
/*     */   
/*     */   private int splitNodeRunLength(Path path, List<String> pathStrings, int startPosition) {
/* 779 */     int endPosition = startPosition + 1;
/* 780 */     String pathPrefix = pathToKey(path);
/* 781 */     if (!((String)pathStrings.get(startPosition)).startsWith(pathPrefix)) {
/* 782 */       throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
/*     */     }
/* 784 */     while ((endPosition < pathStrings.size()) && (((String)pathStrings.get(endPosition)).equals(partKey(path, endPosition - startPosition))))
/*     */     {
/* 786 */       endPosition++;
/*     */     }
/* 788 */     if ((endPosition < pathStrings.size()) && (((String)pathStrings.get(endPosition)).startsWith(pathPrefix + ".part-")))
/*     */     {
/* 790 */       throw new IllegalStateException("Run did not finish with all parts");
/*     */     }
/* 792 */     return endPosition - startPosition;
/*     */   }
/*     */   
/*     */   private Cursor loadNestedQuery(Path path, String[] columns) {
/* 796 */     String pathPrefixStart = pathToKey(path);
/* 797 */     String pathPrefixEnd = pathPrefixStartToPrefixEnd(pathPrefixStart);
/*     */     
/* 799 */     String[] arguments = new String[path.size() + 3];
/* 800 */     String whereClause = buildAncestorWhereClause(path, arguments);
/* 801 */     whereClause = whereClause + " OR (path > ? AND path < ?)";
/* 802 */     arguments[(path.size() + 1)] = pathPrefixStart;
/* 803 */     arguments[(path.size() + 2)] = pathPrefixEnd;
/* 804 */     String orderBy = "path";
/*     */     
/* 806 */     return this.database.query("serverCache", columns, whereClause, arguments, null, null, orderBy);
/*     */   }
/*     */   
/*     */   private static String pathToKey(Path path) {
/* 810 */     if (path.isEmpty()) {
/* 811 */       return "/";
/*     */     }
/* 813 */     return path.toString() + "/";
/*     */   }
/*     */   
/*     */   private static String pathPrefixStartToPrefixEnd(String prefix)
/*     */   {
/* 818 */     assert (prefix.endsWith("/")) : "Path keys must end with a '/'";
/* 819 */     return prefix.substring(0, prefix.length() - 1) + '0';
/*     */   }
/*     */   
/*     */   private static String buildAncestorWhereClause(Path path, String[] arguments) {
/* 823 */     assert (arguments.length >= path.size() + 1);
/* 824 */     int count = 0;
/* 825 */     StringBuilder whereClause = new StringBuilder("(");
/* 826 */     while (!path.isEmpty()) {
/* 827 */       whereClause.append("path");
/* 828 */       whereClause.append(" = ? OR ");
/* 829 */       arguments[count] = pathToKey(path);
/* 830 */       path = path.getParent();
/* 831 */       count++;
/*     */     }
/* 833 */     whereClause.append("path");
/* 834 */     whereClause.append(" = ?)");
/* 835 */     arguments[count] = pathToKey(Path.getEmptyPath());
/* 836 */     return whereClause.toString();
/*     */   }
/*     */   
/*     */   private int removeNested(String table, Path path) {
/* 840 */     String pathPrefixQuery = "path >= ? AND path < ?";
/* 841 */     String pathPrefixStart = pathToKey(path);
/* 842 */     String pathPrefixEnd = pathPrefixStartToPrefixEnd(pathPrefixStart);
/* 843 */     return this.database.delete(table, pathPrefixQuery, new String[] { pathPrefixStart, pathPrefixEnd });
/*     */   }
/*     */   
/*     */   private static List<byte[]> splitBytes(byte[] bytes, int size) {
/* 847 */     int parts = (bytes.length - 1) / size + 1;
/* 848 */     List<byte[]> partList = new ArrayList(parts);
/* 849 */     for (int i = 0; i < parts; i++) {
/* 850 */       int length = Math.min(size, bytes.length - i * size);
/* 851 */       byte[] part = new byte[length];
/* 852 */       System.arraycopy(bytes, i * size, part, 0, length);
/* 853 */       partList.add(part);
/*     */     }
/* 855 */     return partList;
/*     */   }
/*     */   
/*     */   private byte[] joinBytes(List<byte[]> payloads) {
/* 859 */     int totalSize = 0;
/* 860 */     for (byte[] payload : payloads) {
/* 861 */       totalSize += payload.length;
/*     */     }
/* 863 */     byte[] buffer = new byte[totalSize];
/* 864 */     int currentBytePosition = 0;
/* 865 */     for (byte[] payload : payloads) {
/* 866 */       System.arraycopy(payload, 0, buffer, currentBytePosition, payload.length);
/* 867 */       currentBytePosition += payload.length;
/*     */     }
/* 869 */     return buffer;
/*     */   }
/*     */   
/*     */   private byte[] serializeObject(Object object) {
/*     */     try {
/* 874 */       return this.jsonMapper.writeValueAsBytes(object);
/*     */     } catch (IOException e) {
/* 876 */       throw new RuntimeException("Could not serialize leaf node", e);
/*     */     }
/*     */   }
/*     */   
/*     */   private Node deserializeNode(byte[] value) {
/*     */     try {
/* 882 */       Object o = this.jsonMapper.readValue(value, Object.class);
/* 883 */       return NodeUtilities.NodeFromJSON(o);
/*     */     } catch (IOException e) {
/*     */       try {
/* 886 */         String stringValue = new String(value, "UTF-8");
/* 887 */         throw new RuntimeException("Could not deserialize node: " + stringValue, e);
/*     */       } catch (UnsupportedEncodingException e1) {
/* 889 */         throw new RuntimeException("Failed to serialize values to utf-8: " + Arrays.toString(value), e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String commaSeparatedList(Collection<Long> items) {
/* 895 */     StringBuilder list = new StringBuilder();
/* 896 */     boolean first = true;
/* 897 */     for (Iterator i$ = items.iterator(); i$.hasNext();) { long item = ((Long)i$.next()).longValue();
/* 898 */       if (!first)
/* 899 */         list.append(",");
/* 900 */       first = false;
/* 901 */       list.append(item);
/*     */     }
/* 903 */     return list.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/android/SqlPersistenceStorageEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */