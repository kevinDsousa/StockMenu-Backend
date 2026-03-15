package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.CompanyRequestDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.model.entity.Company;
import com.main.model.mapper.CompanyMapper;
import com.main.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCompanyServiceTest {

    @Mock
    private CompanyRepository repository;

    @Mock
    private CompanyMapper mapper;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private DefaultCompanyService service;

    private static final UUID COMPANY_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("deve exigir super admin e delegar para super.create")
        void deveExigirSuperAdminEDelegarParaSuper() {
            CompanyRequestDTO request = new CompanyRequestDTO(
                    "Trade",
                    "Corporate",
                    "12345678901234",
                    "(85)99928-7198",
                    10
            );
            Company entity = new Company();
            entity.setId(COMPANY_ID);
            CompanyResponseDTO response = new CompanyResponseDTO();

            when(mapper.toEntity(request)).thenReturn(entity);
            when(repository.save(entity)).thenReturn(entity);
            when(mapper.toResponse(entity)).thenReturn(response);

            CompanyResponseDTO result = service.create(request);

            verify(authorizationService).requireSuperAdmin();
            verify(repository).save(entity);
            assertThat(result).isSameAs(response);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("deve lançar quando registro não encontrado")
        void deveLancarQuandoNaoEncontrado() {
            when(repository.findById(COMPANY_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(COMPANY_ID))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Registro não encontrado");

            verify(authorizationService, never()).requireCompanyAccess(any());
        }

        @Test
        @DisplayName("deve exigir acesso à empresa e retornar DTO quando encontrado")
        void deveExigirAcessoERetornarDTO() {
            Company entity = new Company();
            entity.setId(COMPANY_ID);
            CompanyResponseDTO response = new CompanyResponseDTO();

            when(repository.findById(COMPANY_ID)).thenReturn(Optional.of(entity));
            when(mapper.toResponse(entity)).thenReturn(response);

            CompanyResponseDTO result = service.findById(COMPANY_ID);

            verify(authorizationService).requireCompanyAccess(COMPANY_ID);
            assertThat(result).isSameAs(response);
        }
    }

    @Nested
    @DisplayName("update")
    class Update {

        @Test
        @DisplayName("deve exigir acesso à empresa e delegar para super.update")
        void deveExigirAcessoEDelegarParaSuper() {
            CompanyRequestDTO request = new CompanyRequestDTO(
                    "Trade",
                    "Corporate",
                    "12345678901234",
                    "(85)99928-7198",
                    10
            );
            Company entity = new Company();
            entity.setId(COMPANY_ID);
            CompanyResponseDTO response = new CompanyResponseDTO();

            when(repository.findById(COMPANY_ID)).thenReturn(Optional.of(entity));
            when(repository.save(entity)).thenReturn(entity);
            when(mapper.toResponse(entity)).thenReturn(response);

            CompanyResponseDTO result = service.update(COMPANY_ID, request);

            verify(authorizationService).requireCompanyAccess(COMPANY_ID);
            verify(mapper).updateEntity(request, entity);
            assertThat(result).isSameAs(response);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("deve exigir acesso à empresa e chamar repository.deleteById")
        void deveExigirAcessoEDeletar() {
            service.delete(COMPANY_ID);

            verify(authorizationService).requireCompanyAccess(COMPANY_ID);
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
            verify(repository).deleteById(captor.capture());
            assertThat(captor.getValue()).isEqualTo(COMPANY_ID);
        }
    }
}
