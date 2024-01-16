package com.example.demoauth.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApproveApplication {

   private String message;

   private LocalDateTime dateOfApprove;

    public ApproveApplication() {
    }

    public ApproveApplication(String message, LocalDateTime dateOfApprove) {
        this.message = message;
        this.dateOfApprove = dateOfApprove;
    }

    public LocalDateTime getDateOfApprove() {
        return dateOfApprove;
    }

    public void setDateOfApprove(LocalDateTime dateOfApprove) {
        this.dateOfApprove = dateOfApprove;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
