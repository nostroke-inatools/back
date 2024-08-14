package inatools.backend.service;

import inatools.backend.domain.MedicationInfo;
import inatools.backend.domain.Member;
import inatools.backend.dto.medication.MedicationInfoListResponse;
import inatools.backend.dto.medication.MedicationInfoRequest;
import inatools.backend.dto.medication.MedicationInfoResponse;
import inatools.backend.repository.MedicationInfoRepository;
import inatools.backend.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicationInfoService {

    private final MemberRepository memberRepository;
    private final MedicationInfoRepository medicationInfoRepository;

    private final MemberService memberService;
    private final UserConnectionService userConnectionService;

    /**
     * 복용약 정보 생성
     */
    @Transactional
    public MedicationInfoListResponse createMedicationInfoList(String loginId,
            MedicationInfoRequest medicationInfoRequest) {
        Member member = memberRepository.findById(medicationInfoRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        memberService.checkMember(loginId, member, userConnectionService);

        List<MedicationInfo> medicationInfoList = medicationInfoRequest.medications().stream()
                .map(detailRequest -> MedicationInfo.createMedicationInfo(detailRequest, member))
                .collect(Collectors.toList());

        List<MedicationInfo> savedMedications = medicationInfoRepository.saveAll(medicationInfoList);
        List<MedicationInfoResponse> medicationInfoResponseList = savedMedications.stream()
                .map(MedicationInfoResponse::fromMedicationInfo)
                .collect(Collectors.toList());

        return new MedicationInfoListResponse(medicationInfoResponseList);
    }

//    /**
//     * 추가하려는 복용약 정보가 이미 존재하는지 복용약 이름으로 확인하고,
//     * 존재한다면 업데이트하고, 존재하지 않는다면 새로 생성
//     */
//    private MedicationInfo createOrUpdateMedication(MedicationDetailRequest request, List<MedicationInfo> existingMedications, Member member) {
//        MedicationInfo existingMedication = existingMedications.stream()
//                .filter(med -> med.getMedicationName().equals(request.medicationName()))
//                .findFirst()
//                .orElse(null);
//
//        if (existingMedication == null) {
//            return MedicationInfo.createMedicationInfo(request, member);
//        } else {
//            existingMedication.updateMedicationInfo(request);
//            return existingMedication;
//        }
//    }

    /**
     * 회원 식별자로 복용약 정보 리스트 조회
     */
    public MedicationInfoListResponse getMedicationInfoListByMemberId(String loginId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        memberService.checkMember(loginId, member, userConnectionService);

        List<MedicationInfo> medicationInfoList = medicationInfoRepository.findAllByMemberIdAndActive(memberId, true);
        List<MedicationInfoResponse> medicationInfoResponseList = medicationInfoList.stream()
                .map(MedicationInfoResponse::fromMedicationInfo)
                .collect(Collectors.toList());

        return new MedicationInfoListResponse(medicationInfoResponseList);
    }


    @Transactional
    public void deleteMedicationInfo(Long id, String loginId) {
        Member member = memberRepository.findByUserId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        MedicationInfo medicationInfo = medicationInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 복용약 정보가 존재하지 않습니다."));

        memberService.checkMember(loginId, member, userConnectionService);

        medicationInfo.delete();
    }

}

