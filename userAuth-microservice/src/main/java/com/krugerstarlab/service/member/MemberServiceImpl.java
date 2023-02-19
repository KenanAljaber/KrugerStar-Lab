package com.krugerstarlab.service.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.Member;
import com.krugerstarlab.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElse(null);
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberId, Member member) {
        Member existingMember = getMemberById(memberId);
        if(existingMember==null) {
        	return null;
        }
        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        existingMember.setRole(member.getRole());
        existingMember.setGroup(member.getGroup());

        return memberRepository.save(existingMember);
    }

    @Override
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

	@Override
	public Member getMemberByEmail(String email) {
		 return memberRepository.findByEmail(email)
	                .orElse(null);
	}

	


}
