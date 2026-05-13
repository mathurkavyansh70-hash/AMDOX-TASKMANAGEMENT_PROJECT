package com.TaskManagement.TaskManagement3.Entity;

import java.time.LocalDateTime;
import java.util.*;

import com.TaskManagement.TaskManagement3.Enum.*;
import com.TaskManagement.TaskManagement3.Enum.IssueType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="issues",indexes = {@Index(name = "idx_issue_key", columnList = "issueKey"),
        @Index(name = "idx_assignee_email", columnList = "assigneeEmail"),
        @Index(name = "idx_issue_status", columnList = "issueStatus"),
        @Index(name = "idx_issue_priority", columnList = "priority")
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String issueKey;

    @Column(nullable = false)
    private String issueTitle;
    
    @Column(length = 5000)
    private String issueDescription;
    
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    private String assigneeEmail;
    private String reportEmail;

    private Long sprintId;
    private Long epicId;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updatedAt=LocalDateTime.now();
    private LocalDateTime dueDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "issue_labels",
        joinColumns = @JoinColumn(name = "issue_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id"))

    private Set<Label> labels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public String getReportEmail() {
        return reportEmail;
    }

    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId2) {
        this.sprintId = sprintId2;
    }

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Label> getLabels() {
        return labels;
    }
    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
}
