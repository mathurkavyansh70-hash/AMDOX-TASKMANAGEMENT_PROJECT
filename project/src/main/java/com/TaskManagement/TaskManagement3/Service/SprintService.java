package com.TaskManagement.TaskManagement3.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.TaskManagement3.Entity.Issue;
import com.TaskManagement.TaskManagement3.Entity.Sprint;
import com.TaskManagement.TaskManagement3.Enum.IssueStatus;
import com.TaskManagement.TaskManagement3.Enum.SprintState;
import com.TaskManagement.TaskManagement3.Repository.IssueRepository;
import com.TaskManagement.TaskManagement3.Repository.SprintRepository;

import jakarta.transaction.Transactional;

@Service
public class SprintService {
    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private SprintRepository sprintRepo;

    public Sprint createSprint(Sprint sprint) {
        sprint.setSprintState(SprintState.PLANNED);
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Issue assignIssueToSprint(Long sprintId, Long issueId) {
        Sprint sprint = sprintRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Sprint not found"));
        Issue issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
        if(sprint.getSprintState() == SprintState.COMPLETED) {
            throw new RuntimeException("Cannot add task to a completed sprint");
        }
        issue.setSprintId(sprintId);
        return issueRepo.save(issue);
    }

    @Transactional
    public Sprint startSprint(Long sprintId){
        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint not found"));
        if(sprint.getSprintState() != SprintState.PLANNED) {
            throw new RuntimeException("Sprint cannot be started");
        }
        sprint.setSprintState(SprintState.ACTIVE);
        if(sprint.getStartDate() == null) {
            sprint.setStartDate(LocalDateTime.now());
        }
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Sprint endSprint(Long sprintId){
        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint not found"));
        sprint.setSprintState(SprintState.COMPLETED);
        if(sprint.getEndDate() == null) {
            sprint.setEndDate(LocalDateTime.now());
        }
        List<Issue> issues = issueRepo.findBySprintId(sprintId);
        for(Issue i: issues){
            if(!i.getIssueStatus().name().equals(IssueStatus.DONE)) {
                i.setSprintId(null);
                issueRepo.save(i);
            }
        }
        return sprintRepo.save(sprint);
    }
    public Map<String , Object>getBurnDownData(Long sprintId) {
        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("Sprint not found"));
        LocalDateTime start = sprint.getStartDate();
        LocalDateTime end = sprint.getEndDate() != null ? sprint.getEndDate() : LocalDateTime.now();
        List<Issue> issues=issueRepo.findBySprintId(sprintId);

        int totalTask=issues.size();
        Map<String,Integer> charts=new LinkedHashMap<>();
        LocalDateTime cursor = start;
        while(!cursor.isAfter(end)) {
            int completed=(int)issues.stream().filter(i->i.getIssueStatus().name().equals(IssueStatus.DONE)).count();
            int remaining=totalTask-completed;
            charts.put(cursor.toString(), remaining);
            cursor=cursor.plusDays(1);
        }
        Map<String,Object>response= new HashMap<>();
        response.put("sprintId", sprintId);
        response.put("startDate", start);
        response.put("endDate", end);
        response.put("burnDownData", charts);

        return response;

    }
}
