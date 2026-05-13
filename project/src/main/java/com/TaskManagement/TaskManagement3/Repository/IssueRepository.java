package com.TaskManagement.TaskManagement3.Repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TaskManagement.TaskManagement3.Entity.Issue;
import com.TaskManagement.TaskManagement3.Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional <Issue> findByIssueKey(String issueKey);
    List<Issue> findByAssigneeEmail(String assigneeEmail);
    List<Issue> findBySprintId(Long sprintId);
    List<Issue> findByIssueStatus(IssueStatus issueStatus);
}
