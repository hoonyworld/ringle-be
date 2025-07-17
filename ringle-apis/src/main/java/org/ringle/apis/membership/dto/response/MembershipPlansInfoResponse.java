package org.ringle.apis.membership.dto.response;

import java.math.BigDecimal;
import java.util.List;

import org.ringle.domain.membership.CustomerType;
import org.ringle.domain.membership.vo.MembershipPlansInfo;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "멤버십 플랜 목록 응답")
public record MembershipPlansInfoResponse(

	@ArraySchema(schema = @Schema(description = "멤버십 플랜 리스트"))
	List<MembershipPlanResponse> plans

) {

	public static MembershipPlansInfoResponse from(MembershipPlansInfo vo) {
		List<MembershipPlanResponse> responses = vo.plans().stream()
			.map(MembershipPlanResponse::from)
			.toList();

		return new MembershipPlansInfoResponse(responses);
	}

	@Schema(description = "단일 멤버십 플랜 정보")
	public record MembershipPlanResponse(

		@Schema(description = "멤버십 플랜 ID", example = "1")
		Long id,

		@Schema(description = "멤버십 이름", example = "AI 보카")
		String name,

		@Schema(description = "고객 유형", example = "B2C")
		CustomerType customerType,

		@Schema(description = "제공 기능 정보")
		MembershipFeatures features,

		@Schema(description = "사용 가능 기간 (일 단위)", example = "30")
		int durationDays,

		@Schema(description = "가격 (KRW)", example = "9900")
		BigDecimal price

	) {
		public static MembershipPlanResponse from(MembershipPlansInfo.MembershipPlanInfo plan) {
			return new MembershipPlanResponse(
				plan.id(),
				plan.name(),
				plan.customerType(),
				new MembershipFeatures(
					plan.conversations(),
					plan.rolePlaying(),
					plan.discussion(),
					plan.levelAnalysis()
				),
				plan.durationDays(),
				plan.price()
			);
		}
	}

	@Schema(description = "제공 기능 수치 정보")
	public record MembershipFeatures(

		@Schema(description = "AI 대화 횟수", example = "100")
		int conversations,

		@Schema(description = "롤플레잉 횟수", example = "10")
		int rolePlaying,

		@Schema(description = "토론 횟수", example = "10")
		int discussion,

		@Schema(description = "레벨 분석 횟수", example = "5")
		int levelAnalysis

	) {
	}
}
