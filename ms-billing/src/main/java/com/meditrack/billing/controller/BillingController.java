package com.meditrack.billing.controller;

import com.meditrack.billing.dto.InvoiceDTO;
import com.meditrack.billing.service.BillingService;
import jakarta.validation.Valid; // Assuming Jakarta Validation API for @Valid
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        return ResponseEntity.ok(billingService.getAllInvoices());
    }

    @GetMapping("/{id}")
    // Simplified to Role check for now as discussed
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT', 'PATIENT')")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getInvoiceById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        return ResponseEntity.ok(billingService.createInvoice(invoiceDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDTO invoiceDTO) {
        return ResponseEntity.ok(billingService.updateInvoice(id, invoiceDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        billingService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(billingService.getInvoicesForPatient(patientId));
    }
}
