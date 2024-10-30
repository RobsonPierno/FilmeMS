# Projeto Java Spring Boot

Este projeto foi desenvolvido com o intuito de colocar em prática conhecimentos de desenvolvimento Java e Spring Boot, abrangendo uma série de funcionalidades e integrações. Abaixo estão os detalhes das principais tecnologias e conceitos aplicados.

## Funcionalidades

- **Conexão com Banco de Dados PostgreSQL**: A aplicação conecta-se a um banco de dados PostgreSQL, permitindo o armazenamento e recuperação de dados de forma eficiente e segura.
  
- **Exposição de APIs REST**: Implementação de uma API RESTful para criação, leitura, atualização e exclusão de dados, facilitando a comunicação com outras aplicações e microsserviços.

- **Mensageria com Kafka**: O projeto inclui uma integração com Kafka, onde é feita a publicação de mensagens para um tópico, permitindo a comunicação assíncrona com outros sistemas.

- **Comunicação entre Microsserviços**: 
  - **Feign Client**: A aplicação utiliza Feign Client para facilitar a comunicação com outros microsserviços, reduzindo o código e aumentando a legibilidade.
  - **RestTemplate**: Também é utilizado o RestTemplate para realizar chamadas REST de maneira manual, permitindo maior controle sobre as requisições.

- **Consulta Personalizada com Query no Repository**: A aplicação possui um exemplo de consulta customizada no Repository usando JPQL ou SQL nativo, permitindo filtragens complexas de dados.

- **Conversão entre DTO e Entity com ModelMapper**: Para simplificar a conversão entre objetos de transferência de dados (DTO) e entidades de banco de dados (Entity), o projeto utiliza ModelMapper, promovendo uma camada de abstração para facilitar o mapeamento de dados.

- **Autenticação OAuth2 com Client Credentials Flow**: O projeto implementa autenticação e autorização com OAuth2, utilizando o fluxo de credenciais do cliente (Client Credentials Flow) para proteger os endpoints.

- **Keycloak** (para autenticação e autorização)

## Dockerização

O projeto está completamente dockerizado, facilitando a execução e a replicação do ambiente. O arquivo principal `docker-compose.yml`, que orquestra todos os serviços necessários para a solução, está incluído neste repositório.