package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.VenueTableMergeRequestDTO;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.request.VenueTableSplitRequestDTO;
import com.main.model.dto.request.VenueTableStatusRequestDTO;
import com.main.model.dto.response.VenueTableResponseDTO;

import java.util.List;
import java.util.UUID;

public interface VenueTableService extends GenericService<VenueTableRequestDTO, VenueTableResponseDTO> {

    List<VenueTableResponseDTO> findByCompanyId(UUID companyId);

    VenueTableResponseDTO split(UUID tableId, VenueTableSplitRequestDTO request);

    VenueTableResponseDTO merge(VenueTableMergeRequestDTO request);

    VenueTableResponseDTO setStatus(UUID id, VenueTableStatusRequestDTO request);
}
