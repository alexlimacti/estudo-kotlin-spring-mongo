package com.indeas.cursokotlinspring.documents

import com.indeas.cursokotlinspring.enums.Perfil
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Funcionario(
    @Id val id: String? = null,
    val name: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val perfil: Perfil,
    val empresaId: String,
    val valorHora: Double? = 0.0,
    val qtdHorasTrabalhoDia: Float? = 0.0f,
    val qtdHorasAlmoco: Float? = 0.0f,
)
