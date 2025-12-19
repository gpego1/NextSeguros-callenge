🛡️ NExT Seguros – API REST de Análise de Risco

Este projeto faz parte do desafio técnico do programa NExT 2023.1 e tem como objetivo demonstrar boas práticas de desenvolvimento back-end utilizando Java e Spring Boot, com foco em qualidade de código, organização, versionamento e clareza arquitetural.

A aplicação consiste em uma API REST responsável por realizar o cadastro de clientes, casas e veículos, além de calcular o perfil de risco de seguros com base nas informações fornecidas pelo usuário.

📌 Objetivo do Projeto

Criar uma API que:

Receba dados pessoais e patrimoniais do usuário

Calcule o perfil de risco para diferentes linhas de seguro

Retorne uma classificação final para cada ramo:

economic

regular

responsible

O projeto simula um cenário real de seguradora, onde o cliente não precisa entender regras complexas de seguro — o sistema atua como um consultor automático.

🧱 Arquitetura e Tecnologias

Java 21

Spring Boot

Spring Data JPA (Hibernate)

API REST

Banco de Dados Relacional (H2 / PostgreSQL)

Maven

Lombok

DTOs para Request/Response

GitFlow + Versionamento Semântico

Arquitetura em camadas:

Controller

Service

Repository

Model (Entities)

DTO

📂 Funcionalidades Implementadas
👤 Cliente

GET /clients

POST /clients

GET /clients/{id}

PUT /clients/{id}

DELETE /clients/{id}

🏠 Casa

GET /houses

POST /houses

PUT /houses/{id}

DELETE /houses/{id}

Relação ManyToOne:
Um cliente pode possuir várias casas, e cada casa pertence a um único cliente.

🚗 Veículo

POST /vehicles

PUT /vehicles/{id}/clients

DELETE /vehicles/{id}

🛡️ Seguro (Análise de Risco)

POST /insurances/life

POST /insurances/disability

POST /insurances/home

POST /insurances/auto

⚙️ Algoritmo de Análise de Risco

O sistema calcula uma pontuação base a partir das respostas de risco (0 a 3) e aplica regras de negócio específicas para cada ramo de seguro:
Regras principais

Usuário sem renda → inelegível para invalidez

Usuário sem casa → inelegível para residencial

Usuário sem veículo → inelegível para automóvel

Idade > 60 → inelegível para vida e invalidez

Idade < 30 → −2 pontos em todos os ramos

Idade entre 30 e 40 → −1 ponto

Renda > 200k → −1 ponto em todos os ramos

Casa hipotecada → +1 ponto em residencial

Cada casa hipotecada → +1 ponto em invalidez

Dependentes → +1 ponto em vida e invalidez

Casado → +1 ponto em vida e −1 em invalidez

Veículo com até 5 anos → +1 ponto em automóvel

Classificação final
Pontuação	Resultado
≤ 0	economic
1 – 2	regular
≥ 3	responsible
