package com.TaskManagement.TaskManagement3.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagement.TaskManagement3.Entity.FileAttachment;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {
	List<FileAttachment>findByIssueId(Long issueId);
}
