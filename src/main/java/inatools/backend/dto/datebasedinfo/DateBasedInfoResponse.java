package inatools.backend.dto.datebasedinfo;

import inatools.backend.dto.bloodpressure.BloodPressureListResponse;
import inatools.backend.dto.condtiondetails.ConditionDetailsListResponse;
import inatools.backend.dto.condtionrecord.ConditionRecordListResponse;
import inatools.backend.dto.medication.MedicationInfoListResponse;
import inatools.backend.dto.medication.MedicationRecordListResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record DateBasedInfoResponse(

        @Schema(description = "혈압 측정 기록 목록")
        BloodPressureListResponse bloodPressureRecords,

        @Schema(description = "복용 약 정보 목록")
        MedicationInfoListResponse medicationInfos,

        @Schema(description = "복용 약 기록 목록")
        MedicationRecordListResponse medicationRecords,

        @Schema(description = "컨디션 상태 기록")
        ConditionRecordListResponse conditionRecords,

        @Schema(description = "몸 상세 기록 목록")
        ConditionDetailsListResponse conditionDetailsRecords
) {

}
