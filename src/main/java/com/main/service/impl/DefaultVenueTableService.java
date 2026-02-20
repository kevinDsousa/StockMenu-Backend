package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import com.main.model.entity.VenueTable;
import com.main.model.mapper.VenueTableMapper;
import com.main.repository.VenueTableRepository;
import com.main.service.VenueTableService;
import org.springframework.stereotype.Service;

@Service
public class DefaultVenueTableService extends DefaultGenericService<VenueTable, VenueTableRequestDTO, VenueTableResponseDTO> implements VenueTableService {

    public DefaultVenueTableService(VenueTableRepository repository, VenueTableMapper mapper) {
        super(repository, mapper);
    }
}
