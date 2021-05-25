package be.webfactor.lcanalyzer.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor()
@EntityListeners(AuditingEntityListener.class)
public class Blunder {

	@Id @NonNull
	private String fen;

	@NonNull
	private String initialSan;

	@NonNull
	private String play;

	@NonNull
	private long count;

	@NonNull
	private int whitePct;

	@NonNull
	private int drawPct;

	@NonNull
	private int blackPct;

	@CreationTimestamp
	private LocalDateTime foundAt;
}
