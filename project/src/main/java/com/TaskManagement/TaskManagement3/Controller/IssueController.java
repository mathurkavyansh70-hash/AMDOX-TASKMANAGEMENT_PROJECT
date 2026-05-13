package com.TaskManagement.TaskManagement3.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.TaskManagement3.Entity.Issue;
import com.TaskManagement.TaskManagement3.Entity.IssueComment;
import com.TaskManagement.TaskManagement3.Entity.Sprint;
import com.TaskManagement.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement.TaskManagement3.Service.IssueService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController // Indicates that this class is a REST controller, which means it will handle
                // HTTP requests and return responses in a RESTful manner
@RequestMapping("/api/issues") // Base URL for all issue-related endpoints
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments (final
                         // fields)

public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping("/create") // Endpoint for creating a new issue
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue,
            @RequestParam(required = false) Set<String> labels) {
        Issue created = issueService.createIssue(issue, labels);
        return ResponseEntity.ok(created);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Issue issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/assigneeEmail")
    public ResponseEntity<List<Issue>> getIssuesByEmail(@RequestParam String assigneeEmail) {
        return ResponseEntity.ok(issueService.getIssueByAssigneeEmail(assigneeEmail));
    }

    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<Issue>> getIssuesBySprintId(@PathVariable Long sprintId) {
        return ResponseEntity.ok(issueService.getIssueBySprintId(sprintId));
    }

    @GetMapping("/issueStatus")
    public ResponseEntity<List<Issue>> getIssuesByIssueStatus(@RequestParam IssueStatus status) {
        return ResponseEntity.ok(issueService.getIssueByStatus(status));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Issue> getIssueStatus(@PathVariable Long id, @RequestParam IssueStatus issueStatus) {
        return ResponseEntity.ok(issueService.updateIssueStatus(id, issueStatus));
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<@Nullable Object> addComment(
            @PathVariable Long id,
            @RequestParam String authorEmail,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(issueService.addComments(id, authorEmail, body));
    }

    @PostMapping("/sprint")
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        Sprint created = issueService.creatSprint(sprint);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Issue>> search(@RequestBody(required = false) Map<String, String> filters) {
        return ResponseEntity.ok(issueService.search(filters));
    }
}
