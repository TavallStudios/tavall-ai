package org.tavall.ai.memory;

import java.util.List;
import java.util.function.Function;

/** Canonical durable record/transaction authority, normally backed by Postgres. */
public interface TavallMemoryRecordStore {
    <T> T transact(Function<TavallMemoryTransaction, T> work);

    List<TavallMemoryRecord> loadExactState(TavallMemoryIdentity identity, int limit);
}
