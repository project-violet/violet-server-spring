package com.example.violetserver.record.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaViewReportRepository extends JpaRepository<ViewReport, ViewReportRepository>, ViewReportRepository  {
}
