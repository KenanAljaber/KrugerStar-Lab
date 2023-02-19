package com.krugerstarlab.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.Group;
import com.krugerstarlab.entity.Member;
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
	public Group updateGroup(Group group) {
		return groupRepository.save(group);
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
		
	}

	  
    @Override
    public void removeMemberFromGroup(Long groupId, Long memberId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found"));
        if (!group.getMembers().contains(member)) {
            throw new EntityNotFoundException("Member not in group");
        }
        group.getMembers().remove(member);
        member.setGroup(null);
    }

}
