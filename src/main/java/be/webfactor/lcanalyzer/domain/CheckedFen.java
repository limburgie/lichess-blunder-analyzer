package be.webfactor.lcanalyzer.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor()
public class CheckedFen {

	@Id @NonNull
	private String code;

	@CreationTimestamp
	private LocalDateTime checkedAt;

	@NonNull
	private String firstCheckedDuringPlay;
}
