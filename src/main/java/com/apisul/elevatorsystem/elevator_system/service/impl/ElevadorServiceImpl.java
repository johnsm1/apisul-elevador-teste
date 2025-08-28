package com.apisul.elevatorsystem.elevator_system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.apisul.elevatorsystem.elevator_system.dto.RegistroUsoDto;
import com.apisul.elevatorsystem.elevator_system.service.ElevadorService;

public class ElevadorServiceImpl implements ElevadorService {

    private final List<RegistroUsoDto> registros;
    private final Map<Integer, Integer> contagemAndares = new HashMap<>();
    private final Map<Character, Integer> contagemElevadores = new HashMap<>();
    private final Map<Character, Integer> contagemPeriodos = new HashMap<>();
    private final Map<Character, Map<Character, Integer>> contagemElevadorPeriodo = new HashMap<>();

    public ElevadorServiceImpl(List<RegistroUsoDto> registros) {
        this.registros = (registros == null) ? Collections.emptyList() : registros;
        processarDados();
    }

    /**
     * Processa a lista de registros e preenche os maps de contagem 
    */
    private void processarDados() {
        if (registros.isEmpty()) {
            return;
        }

        for (RegistroUsoDto registro : registros) {
            contagemAndares.merge(registro.andar(), 1, Integer::sum);

            contagemElevadores.merge(registro.elevador(), 1, Integer::sum);
            
            contagemPeriodos.merge(registro.turno(), 1, Integer::sum);
            
            contagemElevadorPeriodo
                .computeIfAbsent(registro.elevador(), k -> new HashMap<>())
                .merge(registro.turno(), 1, Integer::sum);
        }
    }

    @Override
    public List<Integer> andarMenosUtilizado() {
        return encontrarChavesComMenorValor(contagemAndares);
    }

    @Override
    public List<Character> elevadorMaisFrequentado() {
        return encontrarChavesComMaiorValor(contagemElevadores);
    }
    
    @Override
    public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
        List<Character> elevadoresMaisFrequentados = elevadorMaisFrequentado();
        if (elevadoresMaisFrequentados.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Character> periodosDeMaiorFluxo = new HashSet<>();
        for (Character elevador : elevadoresMaisFrequentados) {
            Map<Character, Integer> periodosDoElevador = contagemElevadorPeriodo.get(elevador);
            periodosDeMaiorFluxo.addAll(encontrarChavesComMaiorValor(periodosDoElevador));
        }
        return new ArrayList<>(periodosDeMaiorFluxo);
    }
    
    @Override
    public List<Character> elevadorMenosFrequentado() {
        return encontrarChavesComMenorValor(contagemElevadores);
    }

    @Override
    public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
        List<Character> elevadoresMenosFrequentados = elevadorMenosFrequentado();
        if (elevadoresMenosFrequentados.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Character> periodosDeMenorFluxo = new HashSet<>();
        for (Character elevador : elevadoresMenosFrequentados) {
            Map<Character, Integer> periodosDoElevador = contagemElevadorPeriodo.get(elevador);
            periodosDeMenorFluxo.addAll(encontrarChavesComMenorValor(periodosDoElevador));
        }
        return new ArrayList<>(periodosDeMenorFluxo);
    }
    
    @Override
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
        return encontrarChavesComMaiorValor(contagemPeriodos);
    }

    private <K> List<K> encontrarChavesComMaiorValor(Map<K, Integer> mapa) {
        if (mapa == null || mapa.isEmpty()) {
            return Collections.emptyList();
        }
        long maxValor = Collections.max(mapa.values());
        return mapa.entrySet().stream()
                .filter(entry -> entry.getValue() == maxValor)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private <K> List<K> encontrarChavesComMenorValor(Map<K, Integer> mapa) {
        if (mapa == null || mapa.isEmpty()) {
            return Collections.emptyList();
        }
        long minValor = Collections.min(mapa.values());
        return mapa.entrySet().stream()
                .filter(entry -> entry.getValue() == minValor)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private float calcularPercentual(char elevador) {
        if (registros.isEmpty()) {
            return 0.0f;
        }
        long totalUsos = registros.size();
        long usosElevador = contagemElevadores.getOrDefault(elevador, 0);
        return (float) usosElevador * 100.0f / totalUsos;
    }

    @Override
    public float percentualDeUsoElevadorA() {
        return calcularPercentual('A');
    }

    @Override
    public float percentualDeUsoElevadorB() {
        return calcularPercentual('B');
    }

    @Override
    public float percentualDeUsoElevadorC() {
        return calcularPercentual('C');
    }

    @Override
    public float percentualDeUsoElevadorD() {
        return calcularPercentual('D');
    }

    @Override
    public float percentualDeUsoElevadorE() {
        return calcularPercentual('E');
    }
}
