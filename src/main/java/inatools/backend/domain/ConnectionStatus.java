package inatools.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConnectionStatus {
    REQUESTING("요청중"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨");

    private final String description;
}
