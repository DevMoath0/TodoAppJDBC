package com.moath.todoappjdbc.scheduler.controller;

import com.moath.todoappjdbc.common.ApiResponse;
import com.moath.todoappjdbc.scheduler.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/scheduler")
@CrossOrigin(origins = "http://localhost:8081") // Allow frontend origin
@Tag(name = "Scheduler", description = "Endpoints for managing scheduled tasks.")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private static final Logger logger = Logger.getLogger(ScheduleController.class.getName());

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Enable Create Item Schedule", description = "Enable the schedule for creating items.")
    @PostMapping("/enable-create-item-schedule")
    public ResponseEntity<ApiResponse> enableCreateItemSchedule() {
        try {
            scheduleService.setCreateItemFlag(true);
            logger.info("Create Item Flag enabled.");
            return ResponseEntity.ok(new ApiResponse(true, "Create Item schedule enabled successfully."));
        } catch (Exception e) {
            logger.severe("Error enabling Create Item schedule: "+ e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to enable Create Item schedule."));
        }
    }

    @Operation(summary = "Disable Create Item Schedule", description = "Disable the schedule for creating items.")
    @PostMapping("/disable-create-item-schedule")
    public ResponseEntity<ApiResponse> disableCreateItemSchedule() {
        try{
            scheduleService.setCreateItemFlag(false);
            logger.info("Create Item Flag disabled.");
            return ResponseEntity.ok(new ApiResponse(true, "Create Item schedule disabled successfully."));
        } catch (Exception e) {
            logger.severe("Error enabling Create Item schedule: "+ e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to enable Create Item schedule."));
        }
    }

    @Operation(summary = "Enable Delete Item Schedule", description = "Enable the schedule for deleting items.")
    @PostMapping("/enable-delete-item-schedule")
    public ResponseEntity<ApiResponse> enableDeleteItemSchedule() {
        try{
            scheduleService.setDeleteItemFlag(true);
            logger.info("Delete Item Flag enabled.");
            return ResponseEntity.ok(new ApiResponse(true, "Delete Item schedule enabled successfully."));
        } catch (Exception e) {
            logger.severe("Error enabling Delete Item schedule: "+ e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to enable Delete Item schedule."));
        }
    }

    @Operation(summary = "Disable Delete Item Schedule", description = "Disable the schedule for deleting items.")
    @PostMapping("/disable-delete-item-schedule")
    public ResponseEntity<ApiResponse> disableDeleteItemSchedule() {
        try{
            scheduleService.setDeleteItemFlag(false);
            logger.info("Delete Item Flag disabled.");
            return ResponseEntity.ok(new ApiResponse(true, "Delete Item schedule disabled successfully."));
        } catch (Exception e) {
            logger.severe("Error disabling Delete Item schedule: "+ e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to disable Delete Item schedule."));
        }
    }

}

