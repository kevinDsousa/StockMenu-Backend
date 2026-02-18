package com.main.infrastructure.exeptions.constants;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum GlobalExceptionConstants implements GenericEnum {

    PSQL_ERROR_MESSAGE("PSQL_ERROR_MESSAGE","Ocorreu um erro durante a operação. Por favor, tente novamente mais tarde."),
    ATTACHMENT("ATTACHMENT", "anexo"),
    FILE_MAX_SIZE("FILE_MAX_SIZE", "O arquivo deve ter no máximo 10MB"),
    CHECK_FIELDS_MESSAGE("CHECK_FIELDS_MESSAGE", "Por favor, verifique todos os campos com validação"),
    INVALID_VALUE_MESSAGE("INVALID_VALUE_MESSAGE", "Valor inválido para o tipo %s: %s. Os valores aceitos são: %s"),
    MANDATORY_PARAMETER_NOT_PROVIDED("MANDATORY_PARAMETER_NOT_PROVIDED", "Parâmetro obrigatório não informado: "),
    INVALID_DATE_FORMAT_MESSAGE("INVALID_DATE_FORMAT_MESSAGE", "Formato de data inválido: "),
    EXPECTED_DATE_FORMAT_MESSAGE("EXPECTED_DATE_FORMAT_MESSAGE", "O formato esperado é AAAA-MM-DD"),
    USE_APPROPRIATE_FORMATS_MESSAGE("USE_APPROPRIATE_FORMATS_MESSAGE", "Utilize formatos apropriados para data e hora."),
    AUTHENTICATION_ERROR_MESSAGE("AUTHENTICATION_ERROR_MESSAGE", "Não autorizado, é necessário autenticar-se."),
    RESOURCE_NOT_FOUND_MESSAGE("RESOURCE_NOT_FOUND_MESSAGE", "Recurso não encontrado"),
    INVALID_PARAMETER_TYPE_MESSAGE("INVALID_PARAMETER_TYPE_MESSAGE", "Tipo de parâmetro inválido: ");

    private final String key;
    private final String value;

    GlobalExceptionConstants(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
