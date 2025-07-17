package org.ringle.domain.membership.vo;

import java.time.LocalDate;

import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipStatus;
import org.ringle.domain.membership.UserMembership;

public record UserMembershipInfo(
	Long id,
	String name,
	int conversationCount,
	int remainingConversations,
	int rolePlayingCount,
	int remainingRolePlaying,
	int discussionCount,
	int remainingDiscussion,
	int analysisCount,
	int remainingAnalysis,
	LocalDate expiryDate,
	MembershipStatus status
) {
	public static UserMembershipInfo newInstance(
		UserMembership membership,
		MembershipPlan membershipPlan
	) {
		return new UserMembershipInfo(
			membership.getId(),
			membershipPlan.getName(),
			membershipPlan.getConversationCount(),
			membership.getRemainingConversations(),
			membershipPlan.getRolePlayingCount(),
			membership.getRemainingRolePlaying(),
			membershipPlan.getDiscussionCount(),
			membership.getRemainingDiscussion(),
			membershipPlan.getAnalysisCount(),
			membership.getRemainingAnalysis(),
			membership.getExpiryDate(),
			membership.getStatus()
		);
	}
}
