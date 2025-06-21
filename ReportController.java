package com.MedicalReportService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MedicalReportService.Entity.Report;
import com.MedicalReportService.Service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports/")
@CrossOrigin(origins = "*")
@EnableDiscoveryClient
public class ReportController {
@Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadReport(@RequestBody Report report) {
    	System.out.println("Getting input :"+report.toString());
        reportService.saveReport(report);
        return ResponseEntity.ok("Report uploaded successfully!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByUserId(@PathVariable String userId) {
        List<Report> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }
}
