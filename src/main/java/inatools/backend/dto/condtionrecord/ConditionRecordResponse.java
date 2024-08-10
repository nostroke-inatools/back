package inatools.backend.dto.condtionrecord;

import inatools.backend.domain.ConditionRecord;
import inatools.backend.domain.ConditionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record ConditionRecordResponse(

        @Schema(description = "컨디션 기록 식별자", example = "1")
        Long conditionRecordId,

        @Schema(description = "기록 날짜", example = "2024-08-15")
        LocalDate recordDate,

        @Schema(description = "컨디션 상태", example = "좋음")
        ConditionType conditionType,

        @Schema(description = "회원 식별자", example = "1")
        Long memberId
) {

    public static ConditionRecordResponse fromConditionRecord(ConditionRecord conditionRecord) {
        return new ConditionRecordResponse(
                conditionRecord.getId(),
                conditionRecord.getRecordDate(),
                conditionRecord.getConditionType(),
                conditionRecord.getMember().getId()
        );
    }
}
