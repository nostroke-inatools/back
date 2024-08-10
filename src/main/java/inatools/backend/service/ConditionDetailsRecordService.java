package inatools.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import inatools.backend.domain.ConditionDetailsRecord;
import inatools.backend.domain.ConditionRecord;
import inatools.backend.domain.Member;
import inatools.backend.dto.condtiondetails.ConditionDetailsListResponse;
import inatools.backend.dto.condtiondetails.ConditionDetailsRecordRequest;
import inatools.backend.dto.condtiondetails.ConditionDetailsRecordResponse;
import inatools.backend.repository.ConditionDetailsRecordRepository;
import inatools.backend.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConditionDetailsRecordService {

    private final MemberRepository memberRepository;
    private final ConditionDetailsRecordRepository conditionDetailsRecordRepository;

    /**
     * 몸 상태 기록 저장 로직
     */
    @Transactional
    public ConditionDetailsRecord createConditionDetailsRecord(String loginId, ConditionDetailsRecordRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Member.checkMember(loginId, member);

        ConditionDetailsRecord conditionDetailsRecord
                = ConditionDetailsRecord.createConditionDetailsRecord(request, member);
        return conditionDetailsRecordRepository.save(conditionDetailsRecord);
    }

    /**
     * 몸 상태 기록 조회 로직
     */
    public ConditionDetailsRecord getConditionDetailsRecord(String loginId, Long memberId, LocalDate recordDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Member.checkMember(loginId, member);

        return conditionDetailsRecordRepository.findByMemberIdAndRecordDate(memberId, recordDate)
                .orElseThrow(() -> new IllegalArgumentException("해당 몸 상태 기록이 존재하지 않습니다."));
    }

    /**
     * 몸 상태 기록 리스트 조회 로직
     */
    public ConditionDetailsListResponse getConditionDetailsRecordList(String loginId, Long memberId, LocalDate recordDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Member.checkMember(loginId, member);

        List<ConditionDetailsRecord> conditionDetailsRecordList =
                conditionDetailsRecordRepository.findAllByMemberIdAndRecordDate(memberId, recordDate);
        List<ConditionDetailsRecordResponse> conditionDetailsRecordResponseList = conditionDetailsRecordList.stream()
                .map(ConditionDetailsRecordResponse::fromConditionDetailsRecord)
                .toList();

        return new ConditionDetailsListResponse(conditionDetailsRecordResponseList);
    }

    /**
     * 몸 상태 기록 수정 로직
     */
    @Transactional
    public ConditionDetailsRecord updateConditionDetailsRecord(Long conditionDetailsRecordId, String loginId,
            ConditionDetailsRecordRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Member.checkMember(loginId, member);

        return conditionDetailsRecordRepository.findById(conditionDetailsRecordId)
                .map(conditionDetailsRecord -> conditionDetailsRecord.updateConditionDetailsRecord(request))
                .orElseThrow(() -> new IllegalArgumentException("해당 몸 상태 기록이 존재하지 않습니다."));
    }

    /**
     * 몸 상태 기록 삭제 로직
     */


}
