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
		UserMembership userMembership,
		MembershipPlan membershipPlan
	) {
		return new UserMembershipInfo(
			userMembership.getId(),
			membershipPlan.getName(),
			membershipPlan.getConversationCount(),
			userMembership.getRemainingConversations(),
			membershipPlan.getRolePlayingCount(),
			userMembership.getRemainingRolePlaying(),
			membershipPlan.getDiscussionCount(),
			userMembership.getRemainingDiscussion(),
			membershipPlan.getAnalysisCount(),
			userMembership.getRemainingAnalysis(),
			userMembership.getExpiryDate(),
			userMembership.getStatus()
		);
	}
}
