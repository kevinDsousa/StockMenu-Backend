package com.main.controller.impl;

import com.main.controller.PaymentMethodController;
import com.main.infrastructure.generic.controller.ControllerResponseHelper;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import com.main.service.PaymentMethodService;
import com.main.utils.constants.MessageCommonsConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "paymentMethod")
@RequiredArgsConstructor
public class DefaultPaymentMethodController implements PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> create(@RequestBody @Valid PaymentMethodRequestDTO request) {
        return ControllerResponseHelper.created(service.create(request), MessageCommonsConstants.SAVE_SUCCESS.getValue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid PaymentMethodRequestDTO request) {
        return ControllerResponseHelper.ok(service.update(id, request), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> findById(@PathVariable UUID id) {
        return ControllerResponseHelper.ok(service.findById(id), MessageCommonsConstants.FIND_ID_SUCCESS.getValue());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PaymentMethodResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId) {
        List<PaymentMethodResponseDTO> list = companyId != null ? service.findByCompanyId(companyId) : service.findAll();
        return ControllerResponseHelper.ok(list, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ControllerResponseHelper.noContent();
    }
}
