package com.krugerstarlab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.service.group.GroupService;

@RestController
@RequestMapping("/api/v1/users/groups")
public class GroupController {
    
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group savedGroup = groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        Group updatedGroup = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<Void> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long memberId) {
        groupService.addMemberToGroup(groupId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<Void> removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long memberId) {
        groupService.removeMemberFromGroup(groupId, memberId);
        return ResponseEntity.noContent().build();
    }
}
