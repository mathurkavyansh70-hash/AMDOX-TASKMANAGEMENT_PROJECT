package com.TaskManagement.TaskManagement3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagement.TaskManagement3.Entity.WorkFlowTransaction;
import com.TaskManagement.TaskManagement3.Enum.IssueStatus;

@Repository
public interface WorkFlowTransactionRepository extends JpaRepository<WorkFlowTransaction,Long> {

	List<WorkFlowTransaction>findByWorkFlowId(Long workFlowId);
	List<WorkFlowTransaction>findByWorkFlowIdAndFrom(Long workFlowId,IssueStatus from);
}

