package com.TaskManagement.TaskManagement3.Service;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.TaskManagement3.Repository.IssueRepository;
import com.TaskManagement.TaskManagement3.Repository.LabelRepository;
import com.TaskManagement.TaskManagement3.Repository.SprintRepository;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;

import com.TaskManagement.TaskManagement3.Entity.Issue;
import com.TaskManagement.TaskManagement3.Entity.IssueComment;
import com.TaskManagement.TaskManagement3.Enum.IssuePriority;
import com.TaskManagement.TaskManagement3.Enum.*;
import com.TaskManagement.TaskManagement3.Repository.IssueCommentRepository;
import com.TaskManagement.TaskManagement3.Entity.Label;
import com.TaskManagement.TaskManagement3.Entity.Sprint;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private LabelRepository labelRepo;

    @Autowired
    private SprintRepository sprintRepo;

    private String generateKey(Long id) {
        return "PROJECT-" + id;
    }

    @Transactional // for the rollaback in case of any failure during the issue creation process
    public Issue createIssue(Issue issue, Set<String> labelNames) {
        issue.setIssueType(issue.getIssueType() != null ? issue.getIssueType() : IssueType.TASK); // default issue type
                                                                                                  // is task
        issue.setPriority(issue.getPriority() != null ? issue.getPriority() : IssuePriority.MEDIUM); // default priority
                                                                                                     // is medium
        issue.setIssueStatus(IssueStatus.OPEN);
        if (labelNames != null) {
            for (String name : labelNames) {
                Label label = labelRepo.findByName(name).orElseThrow(() -> new RuntimeException("Label not found "));
                issue.getLabels().add(label);
            }
        }
        Issue saved = issueRepo.save(issue);
        saved.setIssueKey(generateKey(saved.getId()));
        return issueRepo.save(issue);
    }

    public Issue getIssueById(Long id) {
        return issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    public List<Issue> getIssueByAssigneeEmail(String assigneeEmail) {
        return issueRepo.findByAssigneeEmail(assigneeEmail);
    }

    public List<Issue> getIssueBySprintId(Long sprintId) {
        return issueRepo.findBySprintId(sprintId);
    }

    public List<Issue> getIssueByStatus(IssueStatus issueStatus) {
        return issueRepo.findByIssueStatus(issueStatus);
    }

    @Transactional
    public IssueComment addComment(Long issueId, String authorEmail,String body) {
        Issue issue = getIssueById(issueId);
        IssueComment comments = new IssueComment();
        comments.setIssueId(issue.getId());
        comments.setAuthorEmail(authorEmail);
        comments.setBody(body);
        return issueCommentRepo.save(comments);
    }

    @Transactional
    public Issue updateIssueStatus(Long id, IssueStatus issueStatus) {
        Issue issue = getIssueById(id);

        if (issueStatus == null) {
            throw new RuntimeException("Status can not be null");
        }
        issue.setIssueStatus(issueStatus);
        issue.getUpdatedAt();
        return issueRepo.save(issue);
    }

    @Transactional
    public Sprint creatSprint(Sprint sprint) {
        if (sprint.getSprintState() == null) {
            sprint.setSprintState(SprintState.PLANNED);
        }
        return sprintRepo.save(sprint);
    }

    public List<Issue> search(Map<String, String> filters) {
        List<Issue> issue = issueRepo.findAll();
        if (filters.containsKey("assigneeEmail")) {
            String email = filters.get("assigneeEmail");
            issue = issue.stream().filter(i -> email.equalsIgnoreCase(i.getAssigneeEmail()))
                    .collect(Collectors.toList());
        }
        if (filters.containsKey("sprint")) {
            Long sprintId = Long.valueOf(filters.get("sprint"));
            issue = issue.stream().filter(i -> i.getSprintId().equals(sprintId)).collect(Collectors.toList());
        }
        String statusStr=filters.get("status");
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                IssueStatus issueStatus = IssueStatus.valueOf(statusStr.trim().toUpperCase());
                issue = issue.stream().filter(i -> i.getIssueStatus() == issueStatus).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("Invalid issue filter");
            }
        }
        return issue;
    }

    public @Nullable Object addComments(Long id, String authorEmail, Map<String,String> body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addComments'");
    }
}
