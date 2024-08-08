package com.ssafy.alttab.member.entity;

import com.ssafy.alttab.common.jointable.entity.MemberStudy;
import com.ssafy.alttab.member.enums.MemberRoleStatus;
import com.ssafy.alttab.study.entity.Study;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleStatus role;

    @OneToMany(mappedBy = "member")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    //==생성 메서드==//
    public static Member createMember(String name, String avatarUrl, MemberRoleStatus role) {
        return Member.builder()
                .name(name)
                .avatarUrl(avatarUrl)
                .role(role)
                .build();
    }

    //==비즈니스 로직==//
    /**
     * 맴버가 팔로우 한 스터디 목록 반환
     *
     * @return
     */
    public List<Study> getFollowedStudies() {
        return memberStudies.stream()
                .filter(ms -> ms.getRole() == MemberRoleStatus.FOLLOWER)
                .map(MemberStudy::getStudy)
                .collect(Collectors.toList());
    }

    public void changeAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    /**
     * 멤버 스터디 관계 삭제
     *
     * @param memberStudy
     */
    public void removeMemberStudy(MemberStudy memberStudy) {
        this.memberStudies.remove(memberStudy);
    }
}
