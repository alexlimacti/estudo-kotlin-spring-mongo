package com.indeas.cursokotlinspring

import com.indeas.cursokotlinspring.documents.Empresa
import com.indeas.cursokotlinspring.documents.Funcionario
import com.indeas.cursokotlinspring.enums.Perfil
import com.indeas.cursokotlinspring.repository.EmpresaRepository
import com.indeas.cursokotlinspring.repository.FuncionarioRepository
import com.indeas.cursokotlinspring.repository.LancamentoRepository
import com.indeas.cursokotlinspring.utils.SenhaUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CursoKotlinSpringApplication {

//    private val log = LoggerFactory.getLogger(CursoKotlinSpringApplication::class.java)
//
//    @Bean
//    fun init(
//        empresaRepository: EmpresaRepository,
//        funcionarioRepository: FuncionarioRepository,
//        lancamentoRepository: LancamentoRepository,
//    ) = CommandLineRunner {
//
//        log.info("Limpando base de dados")
//        empresaRepository.deleteAll()
//        funcionarioRepository.deleteAll()
//        lancamentoRepository.deleteAll()
//
//        log.info("Criando empresa")
//        val empresa: Empresa = Empresa(null, "Empresa", "10443887000146")
//        empresaRepository.save(empresa)
//        val temp: Empresa? = empresaRepository.findByCnpj("10443887000146")
//
//        log.info("Criando Funcionario admin")
//        val admin: Funcionario = Funcionario(
//            null, "Admin", "admin@empresa.com",
//            SenhaUtils().gerarBcrypt("123456"), "25708317000",
//            Perfil.ROLE_ADMIN, temp?.id!!
//        )
//        funcionarioRepository.save(admin)
//
//        log.info("Criando Funcionario")
//        val funcionario: Funcionario = Funcionario(
//            null, "Funcionario","funcionario@empresa.com",
//            SenhaUtils().gerarBcrypt("123456"), "44325441557",
//            Perfil.ROLE_USUARIO, temp.id!!
//        )
//        funcionarioRepository.save(funcionario)
//
//        log.info("listando os trem")
//        System.out.println("Empresa ID: " + temp.id)
//        System.out.println("Admin ID: " + admin.id)
//        System.out.println("Funcionario ID: " + funcionario.id)
//    }
}

fun main(args: Array<String>) {
    runApplication<CursoKotlinSpringApplication>(*args)
}