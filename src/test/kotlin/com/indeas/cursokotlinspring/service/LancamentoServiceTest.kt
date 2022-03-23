package com.indeas.cursokotlinspring.service

import com.indeas.cursokotlinspring.documents.Lancamento
import com.indeas.cursokotlinspring.enums.Tipo
import com.indeas.cursokotlinspring.repository.LancamentoRepository
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoServiceTest {

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    @Autowired
    private val lancamentoService: LancamentoService? = null

    private val id: String = "1"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito
            .given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(id, PageRequest.of(0, 10)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))
        BDDMockito.given(lancamentoRepository?.findById("1")?.orElse(null)).willReturn(lancamento())
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java)))
            .willReturn(lancamento())
    }

//    @Test
//    fun testBuscarLancamentoPorFuncionarioId() {
//        val lancamento: Page<Lancamento>? = lancamentoService?.buscarPorFuncionarioId(id, PageRequest(0, 10))
//        Assert.assertNotNull(lancamento)
//    }

    @Test
    fun testBuscarLancamentoPorId() {
        val lancamento: Lancamento? = lancamentoService?.buscarPorId(id)
        Assert.assertNotNull(lancamento)
    }

    @Test
    fun testPersistirLancamento() {
        val lancamento: Lancamento? = lancamentoService?.persistir(lancamento())
        Assert.assertNotNull(lancamento)
    }

    private fun lancamento(): Lancamento = Lancamento(id, Date(), Tipo.INICIO_TRABALHO, "")

}