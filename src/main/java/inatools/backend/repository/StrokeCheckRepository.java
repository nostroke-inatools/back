package inatools.backend.repository;

import inatools.backend.domain.Member;
import inatools.backend.domain.StrokeCheck;
import inatools.backend.domain.StrokeCheckTestType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrokeCheckRepository extends JpaRepository<StrokeCheck, Long> {

    StrokeCheck findByMemberAndTestTypeAndRecordDate(Member member, StrokeCheckTestType testType, LocalDate recordDate);

    List<StrokeCheck> findAllByRecordDate(LocalDate date);
}
