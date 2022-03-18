package com.indeas.cursokotlinspring.repository

import com.indeas.cursokotlinspring.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String> {

    fun findByCnpj(cnpj: String): Empresa?

}