package com.TaskManagement.TaskManagement3.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManagement.TaskManagement3.Entity.Issue;
import com.TaskManagement.TaskManagement3.Entity.Sprint;
import com.TaskManagement.TaskManagement3.Service.SprintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {
    @Autowired
    private SprintService sprintService;

    @PostMapping("/createSprint")
    public ResponseEntity<Sprint>createSprint(@RequestBody Sprint sprint){
        return ResponseEntity.ok(sprintService.createSprint(sprint));
    }

    @PutMapping("/assisgn/{sprintId}/{issueId}")
    public ResponseEntity<Issue>assignIssueToSprint(@PathVariable Long sprintId,@PathVariable Long issueId){
        return ResponseEntity.ok(sprintService.assignIssueToSprint(sprintId, issueId));
    }

    @PutMapping("/{sprintId}/start")
    public ResponseEntity<Sprint> startSprint(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.startSprint(sprintId));
    }

    @PutMapping("/{sprintId}/end")
    public ResponseEntity<Sprint> endSprint(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.endSprint(sprintId));
    }

    @GetMapping("/{sprintId}/burnDownData")
    public ResponseEntity<Map<String, Object>> getBurnDownData(@PathVariable Long sprintId){
        return ResponseEntity.ok(sprintService.getBurnDownData(sprintId));
    }
}
