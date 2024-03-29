package com.krugerstarlab.service.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.repository.GroupRepository;
import com.krugerstarlab.repository.MemberRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Group createGroup(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public Group getGroupById(Long id) {
		return groupRepository.findById(id).orElse(null);
	}

	@Override
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public Group updateGroup(Long id,Group group) {
		Group existingGroup=this.getGroupById(id);
		if(existingGroup==null) {
			return null;
		}
		existingGroup.setName(group.getName());
		List<Member> newMembers=new ArrayList<>();
		group.getMembers().forEach(member->newMembers.add(member));
		existingGroup.setMembers(newMembers);
		
		return groupRepository.save(existingGroup);
	}

	@Override
	public void deleteGroupById(Long id) {
		groupRepository.deleteById(id);
	}

	@Override
	public void addMemberToGroup(Long groupId, Long memberId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new EntityNotFoundException("Group not found"));
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("Member not found"));
		if (!group.addMember(member)) {
			throw new EntityExistsException("Member already in group");
		}
		group.getMembers().add(member);
		 groupRepository.save(group);
	        memberRepository.save(member);
	}

	  
    @Override
    public void removeMemberFromGroup(Long groupId, Long memberId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found"));
        if (!group.getMembers().contains(member)) {
            throw new EntityNotFoundException("Member not in group");
        }
        group.getMembers().remove(member);
        member.setMyGroup(null);
        groupRepository.save(group);
        memberRepository.save(member);
    }

}
