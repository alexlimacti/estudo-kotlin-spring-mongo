package com.indeas.cursokotlinspring.service

import com.indeas.cursokotlinspring.documents.Empresa

interface EmpresaService {

    fun buscarPorCnpj(cnpj: String): Empresa?

    fun persistir(empresa: Empresa): Empresa

}