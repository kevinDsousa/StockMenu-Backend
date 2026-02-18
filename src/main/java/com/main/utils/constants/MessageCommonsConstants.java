package com.main.utils.constants;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum MessageCommonsConstants implements GenericEnum {

    SAVE_SUCCESS("SAVE_SUCCESS", "O registro foi salvo com sucesso!"),
    UPDATE_SUCCESS("UPDATE_SUCCESS", "O registro foi atualizado com sucesso!"),
    DELETE_SUCCESS("DELETE_SUCCESS", "O registro foi excluído com sucesso!"),
    FIND_ID_SUCCESS("FIND_ID_SUCCESS", "Registro encontrado."),
    FIND_ALL_SUCCESS("FIND_ALL_SUCCESS", "Registros encontrados."),
    FIND_FILTER_EMPTY("FIND_FILTER_EMPTY", "Nenhum resultado encontrado! Tente alterar os filtros ou o termo de busca."),
    LOGIN_SUCCESS("LOGIN_SUCCESS", "Login realizado com sucesso."),
    UPLOAD_SUCCESS("UPLOAD_SUCCESS", "Os arquivos foram salvos com sucesso!"),
    PASSWORD_CHANGED("PASSWORD_CHANGED", "Senha alterada com sucesso!"),
    CODE_SENT("CODE_SENT", "Um novo código foi enviado para o seu contato principal!"),
    SUBSCRIPTION_CANCELLED("SUBSCRIPTION_CANCELLED", "Assinatura cancelada com sucesso!");

    private final String key;
    private final String value;

    MessageCommonsConstants(String key, String value) {
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


    public static String formatSaveSuccess(String name, boolean isRegistration) {
        String action = isRegistration ? "registered" : "created";
        return String.format("%s %s successfully!", name, action);
    }

    public static String formatUpdateSuccess(String name) {
        return String.format("%s updated successfully.", name);
    }
}
