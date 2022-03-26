package com.indeas.cursokotlinspring.controller

import com.indeas.cursokotlinspring.documents.Empresa
import com.indeas.cursokotlinspring.documents.Funcionario
import com.indeas.cursokotlinspring.dto.CadastroPFDto
import com.indeas.cursokotlinspring.enums.Perfil
import com.indeas.cursokotlinspring.response.Response
import com.indeas.cursokotlinspring.service.EmpresaService
import com.indeas.cursokotlinspring.service.FuncionarioService
import com.indeas.cursokotlinspring.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastrar-pf")
class CadastroPFController(val empresaService: EmpresaService,
                           val funcionarioService: FuncionarioService
) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastroPFDto: CadastroPFDto,
                  result: BindingResult): ResponseEntity<Response<CadastroPFDto>> {
        val response: Response<CadastroPFDto> = Response<CadastroPFDto>()

        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastroPFDto.cnpj)
        validarDadosExistentes(cadastroPFDto, empresa, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) erro.defaultMessage?.let { response.erros.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        val funcionario: Funcionario = converterDtoParaFuncionario(cadastroPFDto, empresa!!)

        response.data = converterCadastroPFDto(funcionarioService.persistir(funcionario), empresa!!)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastroPFDto: CadastroPFDto, empresa: Empresa?,
                                       result: BindingResult) {
        if (empresa == null) {
            result.addError(ObjectError("empresa", "Empresa não cadastrada."))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastroPFDto.cpf)
        if (funcionarioCpf != null) {
            result.addError(ObjectError("funcionario", "CPF já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastroPFDto.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDtoParaFuncionario(cadastroPFDto: CadastroPFDto, empresa: Empresa) =
        Funcionario(cadastroPFDto.id, cadastroPFDto.nome, cadastroPFDto.email,
            SenhaUtils().gerarBcrypt(cadastroPFDto.senha), cadastroPFDto.cpf,
            Perfil.ROLE_USUARIO, empresa.id.toString(),
            cadastroPFDto.valorHora?.toDouble(), cadastroPFDto.qtdHorasTrabalhoDia?.toFloat(),
            cadastroPFDto.qtdHorasAlmoco?.toFloat())


    private fun converterCadastroPFDto(funcionario: Funcionario, empresa: Empresa): CadastroPFDto  =
        CadastroPFDto(funcionario.name, funcionario.email, "", funcionario.cpf,
            empresa.cnpj, empresa.id.toString(),funcionario.valorHora.toString(),
            funcionario.qtdHorasTrabalhoDia.toString(),
            funcionario.qtdHorasTrabalhoDia.toString(),
            funcionario.id)
}