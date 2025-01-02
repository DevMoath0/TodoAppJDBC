package com.moath.todoappjdbc.scheduler.controller;

import com.moath.todoappjdbc.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/scheduler")
@CrossOrigin(origins = "http://localhost:8081") // Allow frontend origin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    private static final Logger logger = Logger.getLogger(ScheduleController.class.getName());

    @PostMapping("/enable-create-user-schedule")
    public ResponseEntity<Boolean> enableCreateUserSchedule() {
        scheduleService.setCreateUserFlag(true);
        logger.info("Create User Flag On");
        return ResponseEntity.ok(scheduleService.getCreateUserFlag());
    }

    @PostMapping("/disable-create-user-schedule")
    public ResponseEntity<Boolean> disableCreateUserSchedule() {
        scheduleService.setCreateUserFlag(false);
        logger.info("Create User Flag Off");
        return ResponseEntity.ok(scheduleService.getCreateUserFlag());
    }
}

