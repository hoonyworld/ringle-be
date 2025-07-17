package org.ringle.domain.membership.vo;

import java.math.BigDecimal;
import java.util.List;

import org.ringle.domain.membership.CustomerType;
import org.ringle.domain.membership.MembershipPlan;

public record MembershipPlansInfo(List<MembershipPlanInfo> plans) {

	public static MembershipPlansInfo newInstance(List<MembershipPlan> plans) {
		List<MembershipPlanInfo> planInfos = plans.stream()
			.map(MembershipPlanInfo::from)
			.toList();

		return new MembershipPlansInfo(planInfos);
	}

	public record MembershipPlanInfo(
		Long id,
		String name,
		CustomerType customerType,
		int conversations,
		int rolePlaying,
		int discussion,
		int levelAnalysis,
		int durationDays,
		BigDecimal price
	) {
		public static MembershipPlanInfo from(MembershipPlan plan) {
			return new MembershipPlanInfo(
				plan.getId(),
				plan.getName(),
				plan.getCustomerType(),
				plan.getConversationCount(),
				plan.getRolePlayingCount(),
				plan.getDiscussionCount(),
				plan.getAnalysisCount(),
				plan.getDurationDays(),
				plan.getPrice()
			);
		}
	}
}
