package org.ringle.apis.membership.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.ringle.domain.membership.MembershipStatus;
import org.ringle.domain.membership.vo.UserMembershipInfo;

import java.time.LocalDate;

@Schema(description = "유저 멤버십 정보 응답")
public record UserMembershipInfoResponse(

	@Schema(description = "멤버십 상세 정보")
	Membership membership,

	@Schema(description = "남은 콘텐츠 사용량")
	MembershipFeatures remainingUsage,

	@Schema(description = "멤버십 만료일", example = "2025-12-31")
	LocalDate expiryDate,

	@Schema(description = "멤버십 상태", example = "ACTIVE")
	MembershipStatus status
) {
	public static UserMembershipInfoResponse from(UserMembershipInfo info) {
		return new UserMembershipInfoResponse(
			new Membership(
				info.id(),
				info.name(),
				new MembershipFeatures(
					info.conversationCount(),
					info.rolePlayingCount(),
					info.discussionCount(),
					info.analysisCount()
				)
			),
			new MembershipFeatures(
				info.remainingConversations(),
				info.remainingRolePlaying(),
				info.remainingDiscussion(),
				info.remainingAnalysis()
			),
			info.expiryDate(),
			info.status()
		);
	}

	@Schema(description = "멤버십 정보")
	public record Membership(
		@Schema(description = "멤버십 ID", example = "1")
		Long id,

		@Schema(description = "멤버십 이름", example = "프리미엄 플러스")
		String name,

		@Schema(description = "멤버십에서 제공하는 기능")
		MembershipFeatures features
	) {}

	@Schema(description = "콘텐츠 기능 수치")
	public record MembershipFeatures(
		@Schema(description = "일반 대화 가능 횟수", example = "100")
		int conversations,

		@Schema(description = "롤플레잉 가능 횟수", example = "50")
		int rolePlaying,

		@Schema(description = "디스커션 가능 횟수", example = "30")
		int discussion,

		@Schema(description = "레벨 분석 가능 횟수", example = "5")
		int levelAnalysis
	) {}
}
