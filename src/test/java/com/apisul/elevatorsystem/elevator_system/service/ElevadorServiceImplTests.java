package com.apisul.elevatorsystem.elevator_system.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.apisul.elevatorsystem.elevator_system.dto.RegistroUsoDto;
import com.apisul.elevatorsystem.elevator_system.service.impl.ElevadorServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ElevadorServiceImplTests {

    private ElevadorServiceImpl service;

    @BeforeEach
    void setUp() {
        List<RegistroUsoDto> registros = Arrays.asList(
                new RegistroUsoDto(1, 'A', 'M'),
                new RegistroUsoDto(2, 'A', 'T'),
                new RegistroUsoDto(1, 'B', 'M'),
                new RegistroUsoDto(3, 'C', 'N'),
                new RegistroUsoDto(1, 'A', 'M'),
                new RegistroUsoDto(2, 'B', 'T')
        );
        service = new ElevadorServiceImpl(registros);
    }

    @Test
    void testAndarMenosUtilizado() {
        List<Integer> menosUtilizados = service.andarMenosUtilizado();
        assertEquals(Collections.singletonList(3), menosUtilizados);
    }

    @Test
    void testElevadorMaisFrequentado() {
        List<Character> maisFrequentados = service.elevadorMaisFrequentado();
        assertEquals(Collections.singletonList('A'), maisFrequentados);
    }

    @Test
    void testPeriodoMaiorFluxoElevadorMaisFrequentado() {
        List<Character> periodos = service.periodoMaiorFluxoElevadorMaisFrequentado();
        assertEquals(Collections.singletonList('M'), periodos);
    }

    @Test
    void testElevadorMenosFrequentado() {
        List<Character> menosFrequentados = service.elevadorMenosFrequentado();
        assertEquals(Arrays.asList('C'), menosFrequentados);
    }

    @Test
    void testPeriodoMenorFluxoElevadorMenosFrequentado() {
        List<Character> periodos = service.periodoMenorFluxoElevadorMenosFrequentado();
        assertEquals(Collections.singletonList('N'), periodos);
    }

    @Test
    void testPeriodoMaiorUtilizacaoConjuntoElevadores() {
        List<Character> periodos = service.periodoMaiorUtilizacaoConjuntoElevadores();
        assertEquals(Collections.singletonList('M'), periodos);
    }

    @Test
    void testPercentualDeUsoElevadorA() {
        float percentual = service.percentualDeUsoElevadorA();
        assertEquals(50.0f, percentual);
    }

    @Test
    void testPercentualDeUsoElevadorB() {
        float percentual = service.percentualDeUsoElevadorB();
        assertEquals(33.333336f, percentual, 0.0001f);
    }

    @Test
    void testPercentualDeUsoElevadorC() {
        float percentual = service.percentualDeUsoElevadorC();
        assertEquals(16.666668f, percentual, 0.0001f);
    }

    @Test
    void testPercentualDeUsoElevadorD() {
        float percentual = service.percentualDeUsoElevadorD();
        assertEquals(0.0f, percentual);
    }

    @Test
    void testPercentualDeUsoElevadorE() {
        float percentual = service.percentualDeUsoElevadorE();
        assertEquals(0.0f, percentual);
    }

    @Test
    void testSemRegistros() {
        ElevadorServiceImpl vazio = new ElevadorServiceImpl(Collections.emptyList());
        assertTrue(vazio.andarMenosUtilizado().isEmpty());
        assertTrue(vazio.elevadorMaisFrequentado().isEmpty());
        assertEquals(0.0f, vazio.percentualDeUsoElevadorA());
    }
}
