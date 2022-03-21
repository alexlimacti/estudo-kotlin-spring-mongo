package com.indeas.cursokotlinspring.service.impl

import com.indeas.cursokotlinspring.documents.Funcionario
import com.indeas.cursokotlinspring.repository.FuncionarioRepository
import com.indeas.cursokotlinspring.service.FuncionarioService
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository) : FuncionarioService {

    override fun persistir(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String): Funcionario? = funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String): Funcionario? = funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: String): Funcionario? = funcionarioRepository.findById(id).orElse(null)

}
