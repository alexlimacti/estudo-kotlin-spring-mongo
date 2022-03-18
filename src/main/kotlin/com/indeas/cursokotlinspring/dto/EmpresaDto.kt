package com.indeas.cursokotlinspring.dto

data class EmpresaDto (
        val razaoSocial: String,
        val cnpj: String,
        val id: String? = null
)
