package com.apisul.elevatorsystem.elevator_system;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.apisul.elevatorsystem.elevator_system.dto.RegistroUsoDto;
import com.apisul.elevatorsystem.elevator_system.service.ElevadorService;
import com.apisul.elevatorsystem.elevator_system.service.impl.ElevadorServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ElevatorSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElevatorSystemApplication.class, args);
		InputStream inputStream = ElevatorSystemApplication.class.getClassLoader().getResourceAsStream("input.json");

		if (inputStream == null) {
            throw new RuntimeException("Arquivo input.json não encontrado!");
        }

		InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		Gson gson = new Gson();
		Type type = TypeToken.getParameterized(List.class, RegistroUsoDto.class).getType();
		List<RegistroUsoDto> registros = gson.fromJson(reader, type);

		ElevadorService service = new ElevadorServiceImpl(registros);

		System.out.println("--- Análise do Sistema de Elevadores ---");

		System.out.println("\na. Andar(es) menos utilizado(s): " + service.andarMenosUtilizado());

		System.out.println("\nb. Elevador(es) mais frequentado(s): " + service.elevadorMaisFrequentado());
		System.out.println("   Período(s) de maior fluxo do elevador mais frequentado: " + service.periodoMaiorFluxoElevadorMaisFrequentado());

		System.out.println("\nc. Elevador(es) menos frequentado(s): " + service.elevadorMenosFrequentado());
		System.out.println("   Período(s) de menor fluxo do elevador menos frequentado: " + service.periodoMenorFluxoElevadorMenosFrequentado());

		System.out.println("\nd. Período(s) de maior utilização do conjunto de elevadores: " + service.periodoMaiorUtilizacaoConjuntoElevadores());
		
		System.out.println("\ne. Percentual de uso de cada elevador:");
		System.out.printf("   - Elevador A: %.2f%%\n", service.percentualDeUsoElevadorA());
		System.out.printf("   - Elevador B: %.2f%%\n", service.percentualDeUsoElevadorB());
		System.out.printf("   - Elevador C: %.2f%%\n", service.percentualDeUsoElevadorC());
		System.out.printf("   - Elevador D: %.2f%%\n", service.percentualDeUsoElevadorD());
		System.out.printf("   - Elevador E: %.2f%%\n", service.percentualDeUsoElevadorE());
		
	}

}
