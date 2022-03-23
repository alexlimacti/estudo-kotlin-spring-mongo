package com.indeas.cursokotlinspring.controller

import com.indeas.cursokotlinspring.documents.Funcionario
import com.indeas.cursokotlinspring.documents.Lancamento
import com.indeas.cursokotlinspring.dto.LancamentoDto
import com.indeas.cursokotlinspring.enums.Tipo
import com.indeas.cursokotlinspring.response.Response
import com.indeas.cursokotlinspring.service.FuncionarioService
import com.indeas.cursokotlinspring.service.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(
    val lancamentoService: LancamentoService,
    val funcionarioService: FuncionarioService
) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${paginacao.qtd_por_pagina}")
    val qtdPorPagina: Int = 15

    @PostMapping
    fun adicionar(
        @Valid @RequestBody lancamentoDto: LancamentoDto,
        result: BindingResult
    ): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) erro.defaultMessage?.let { response.erros.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)
        response.data = converterDtoParaLancamentoDto(lancamentoService.persistir(lancamento))
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: String): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if (lancamento == null) {
            response.erros.add(("Lançamento não encontrado para o ID $id"))
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterDtoParaLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: String, @Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult): ResponseEntity<Response<LancamentoDto>> {

        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)
        lancamentoDto.id = id
        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) erro.defaultMessage?.let { response.erros.add(it) }
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.persistir(lancamento)
        response.data = converterDtoParaLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun remover(@PathVariable("id") id: String): ResponseEntity<Response<String>> {

        val response: Response<String> = Response<String>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if (lancamento == null) {
            response.erros.add("Erro ao remover lançamento. Registro não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.remover(id)
        return ResponseEntity.ok(Response<String>())
    }

    private fun converterDtoParaLancamentoDto(lancamento: Lancamento): LancamentoDto =
        LancamentoDto(
            lancamento.id, dateFormat.format(lancamento.data), lancamento.tipo.toString(),
            lancamento.descricao, lancamento.localizacao,
            lancamento.funcionarioId
        )

    private fun converterDtoParaLancamento(lancamentoDto: LancamentoDto, result: BindingResult): Lancamento {
        if (lancamentoDto.id != null) {
            val lanc: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id!!)
            if (lanc == null) result.addError(
                ObjectError(
                    "lancamento",
                    "Lançamento não encontrado."
                )
            )
        }

        return Lancamento(
            lancamentoDto.id, dateFormat.parse(lancamentoDto.data), Tipo.valueOf(lancamentoDto.tipo!!),
            lancamentoDto.funcionarioId!!, lancamentoDto.descricao,
            lancamentoDto.localizacao
        )
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        if (lancamentoDto.funcionarioId == null) {
            result.addError(
                ObjectError(
                    "funcionario",
                    "Funcionário não informado."
                )
            )
            return
        }

        val funcionario: Funcionario? = funcionarioService.buscarPorId(lancamentoDto.funcionarioId)
        if (funcionario == null) {
            result.addError(
                ObjectError(
                    "funcionario",
                    "Funcionário não encontrado. ID inexistente."
                )
            );
        }
    }
}