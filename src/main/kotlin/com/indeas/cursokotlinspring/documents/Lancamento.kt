package com.indeas.cursokotlinspring.documents

import com.indeas.cursokotlinspring.enums.Tipo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Lancamento(
    @Id val id: String? = null,
    val data: Date,
    val tipo: Tipo,
    val funcionarioId: String,
    val descricao: String? = "",
    val localizacao: String? = ""
)
