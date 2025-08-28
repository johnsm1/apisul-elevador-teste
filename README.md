# Apisul Elevador Teste

## Descrição
Projeto desenvolvido como teste prático para a Apisul, visando analisar o uso de elevadores do prédio 99a da Tecnopuc.  
Permite identificar andares menos usados, elevadores mais/menos frequentados e períodos de maior fluxo.

## Tecnologias
- Java 17
- Spring Boot
- Maven
- JSON para entrada de dados

## Funcionalidades
- Qual andar é menos utilizado
- Qual elevador é mais/menos frequentado e seus períodos de maior/menor fluxo
- Percentual de uso de cada elevador
- Período de maior utilização do conjunto de elevadores

## Estrutura do Projeto
- `src/main/java` → código-fonte Java
- `src/main/resources` → arquivo `input.json` com os dados de entrada
- `IElevadorService.java` → interface principal do serviço de análise
- `ElevadorServiceImpl.java` → implementação da interface

## Como Executar
1. Clone o repositório:
```bash
git clone https://github.com/johnsm1/apisul-elevador-teste.git
