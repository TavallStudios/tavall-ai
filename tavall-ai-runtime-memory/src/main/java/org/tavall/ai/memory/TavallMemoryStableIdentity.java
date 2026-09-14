package org.tavall.ai.memory;

import java.util.Comparator;

/** Stable durable-memory identity used for deterministic transaction locking. */
public record TavallMemoryStableIdentity(
        String userId,
        String workspaceId,
        String projectId,
        String chatId,
        String threadKey,
        TavallMemoryScope scope,
        TavallMemoryKind kind,
        String titleKey
) implements Comparable<TavallMemoryStableIdentity> {
    private static final Comparator<TavallMemoryStableIdentity> ORDER = Comparator.comparing(TavallMemoryStableIdentity::lockKey);

    public static TavallMemoryStableIdentity from(
            TavallMemoryIdentity identity,
            TavallMemoryScope scope,
            TavallMemoryKind kind,
            String titleKey
    ) {
        return new TavallMemoryStableIdentity(
                identity.userId(),
                identity.workspaceId(),
                scope == TavallMemoryScope.GLOBAL ? "" : identity.projectId(),
                scope == TavallMemoryScope.SESSION ? identity.chatId() : "",
                scope == TavallMemoryScope.SESSION ? identity.threadKey() : "",
                scope,
                kind,
                titleKey
        );
    }

    public static TavallMemoryStableIdentity from(TavallMemoryRecord record) {
        return new TavallMemoryStableIdentity(
                record.userId(), record.workspaceId(), record.projectId(), record.chatId(), record.threadKey(),
                record.scope(), record.kind(), record.titleKey()
        );
    }

    public String lockKey() {
        return String.join("\u001f", userId, workspaceId, projectId, chatId, threadKey, scope.name(), kind.name(), titleKey);
    }

    @Override
    public int compareTo(TavallMemoryStableIdentity other) {
        return ORDER.compare(this, other);
    }
}
