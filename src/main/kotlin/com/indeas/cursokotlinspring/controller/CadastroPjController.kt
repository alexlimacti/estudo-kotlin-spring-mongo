package com.indeas.cursokotlinspring.controller

import com.indeas.cursokotlinspring.documents.Empresa
import com.indeas.cursokotlinspring.documents.Funcionario
import com.indeas.cursokotlinspring.dto.CadastroPJDto
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
@RequestMapping("/api/cadastrar-pj")
class CadastroPjController(val empresaService: EmpresaService,
                           val funcionarioService: FuncionarioService
) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastroPJDto: CadastroPJDto,
                  result: BindingResult): ResponseEntity<Response<CadastroPJDto>> {
        val response: Response<CadastroPJDto> = Response<CadastroPJDto>()

        validarDadosExistentes(cadastroPJDto, result)
        if (result.hasErrors()) {
            for (erro in result.allErrors) erro.defaultMessage?.let { response.erros.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        val empresa: Empresa = converterDtoParaEmpresa(cadastroPJDto)
        empresaService.persistir(empresa)

        val funcionario: Funcionario = converterDtoParaFuncionario(cadastroPJDto, empresa)
        funcionarioService.persistir(funcionario)
        response.data = converterCadastroPJDto(funcionario, empresa)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastroPJDto: CadastroPJDto, result: BindingResult) {
        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastroPJDto.cnpj)
        if (empresa != null) {
            result.addError(ObjectError("empresa", "Empresa já existente."))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastroPJDto.cpf)
        if (funcionarioCpf != null) {
            result.addError(ObjectError("funcionario", "CPF já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastroPJDto.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDtoParaEmpresa(cadastroPJDto: CadastroPJDto): Empresa =
        Empresa(null, cadastroPJDto.razaoSocial, cadastroPJDto.cnpj)


    private fun converterDtoParaFuncionario(cadastroPJDto: CadastroPJDto, empresa: Empresa) =
        Funcionario(null, cadastroPJDto.nome, cadastroPJDto.email,
            SenhaUtils().gerarBcrypt(cadastroPJDto.senha), cadastroPJDto.cpf,
            Perfil.ROLE_ADMIN, empresa.id.toString())

    private fun converterCadastroPJDto(funcionario: Funcionario, empresa: Empresa): CadastroPJDto =
        CadastroPJDto(funcionario.name, funcionario.email, "", funcionario.cpf,
            empresa.cnpj, empresa.razaoSocial, funcionario.id)
}