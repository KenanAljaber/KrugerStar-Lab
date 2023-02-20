package com.krugerstarlab.service.group;


import java.util.List;

import com.krugerstarlab.entity.Group;

public interface GroupService {
	
	// Create a new group
	public Group createGroup(Group group);
	
	// Retrieve a group by ID
	public Group getGroupById(Long id);
	
	// Retrieve all groups
	public List<Group> getAllGroups();
	
	// Update an existing group
	public Group updateGroup(Long id,Group group);
	
	// Delete a group by ID
	public void deleteGroupById(Long id);
	
	// Add a member to a group
	public void addMemberToGroup(Long groupId, Long memberId);
	
	// Remove a member from a group
	public void removeMemberFromGroup(Long groupId, Long memberId);

}
