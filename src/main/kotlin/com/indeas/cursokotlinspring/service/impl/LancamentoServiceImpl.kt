package com.indeas.cursokotlinspring.service.impl

import com.indeas.cursokotlinspring.documents.Lancamento
import com.indeas.cursokotlinspring.repository.LancamentoRepository
import com.indeas.cursokotlinspring.service.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository) : LancamentoService {

    override fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest): Page<Lancamento> = lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest)

    override fun buscarPorId(id: String): Lancamento? = lancamentoRepository.findById(id).orElse(null)

    override fun persistir(lancamento: Lancamento): Lancamento = lancamentoRepository.save(lancamento)

    override fun remover(id: String) = lancamentoRepository.deleteById(id)

}