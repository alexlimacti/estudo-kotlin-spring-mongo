package com.indeas.cursokotlinspring.service.impl

import com.indeas.cursokotlinspring.documents.Empresa
import com.indeas.cursokotlinspring.repository.EmpresaRepository
import com.indeas.cursokotlinspring.service.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun buscarPorCnpj(cnpj: String): Empresa ? = empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa): Empresa = empresaRepository.save(empresa)

}