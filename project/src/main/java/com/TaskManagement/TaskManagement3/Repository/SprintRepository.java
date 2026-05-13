package com.TaskManagement.TaskManagement3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagement.TaskManagement3.Entity.Sprint;
import com.TaskManagement.TaskManagement3.Enum.SprintState;
@Repository

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findByProjectId(Long projectId);
    List<Sprint> findBySprintState(SprintState sprintState);
}
