package org.tavall.ai.memory;

import java.util.Objects;

/** Authority identity supplied by an already-authenticated runtime host. */
public record TavallMemoryIdentity(
        String userId,
        String workspaceId,
        String projectId,
        String chatId,
        String sessionId,
        String threadKey
) {
    public TavallMemoryIdentity {
        userId = requireText(userId, "userId");
        workspaceId = requireText(workspaceId, "workspaceId");
        projectId = clean(projectId);
        chatId = clean(chatId);
        sessionId = clean(sessionId);
        threadKey = clean(threadKey);
    }

    public String authorityKey() {
        return userId + "|" + workspaceId;
    }

    public String exactStateKey(long globalRevision) {
        return String.join("|", userId, workspaceId, projectId, chatId, threadKey)
                + "|global-revision=" + globalRevision;
    }

    private static String requireText(String value, String field) {
        String normalized = clean(value);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return normalized;
    }

    static String clean(String value) {
        return Objects.toString(value, "").strip();
    }
}
