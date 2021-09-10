# iCorrespondência API

<div align="center">
<h4>&#x1F6A7; Projeto em construção &#x1F6A7;</h4>

<p>
<img alt="iCorrespondencia version" src="https://img.shields.io/static/v1?label=version&message=0.9-alpha&color=blue&style=flat-square" />

<a href="https://github.com/ozluuh/icorrespondencia-api/blob/main/LICENSE">
    <img alt="GitHub license" src="https://img.shields.io/github/license/ozluuh/icorrespondencia-api?style=flat-square" />
</a>
</p>
</div>

## Tabela de conteúdos

-   [Sobre](#sobre)
-   [Documentos do projeto](#documentos-do-projeto)
-   [Funcionalidades](#funcionalidades)
-   [Como executar](#como-executar)
    -   [Pré-requisitos](#pré-requisitos)
        -   [Preparando o ambiente](#preparando-o-ambiente)
        -   [Executando a aplicação](#executando-a-aplicação)
-   [Como contribuir](#como-contribuir)
-   [Autores](#autores)
-   [Licença](#licença)

## Sobre

**iCorrespondência** trata-se de um projeto voltado para o controle de correspondências de pequeno porte.

Projeto desenvolvido para a **Challenge 2021** proposta pela instituição **FIAP - Faculdade de Informática e Administração Paulista** em parceria com a **Plusoft**.

## Documentos do projeto

Os documentos do projeto: **Escopo**, **Modelagem de dados** dentre outros poderão ser encontrados [aqui](docs/README.md).

:warning: Ao todo possuem quatro sprints para a realização desse projeto em **2021**. A documentação final será disponibilizada em formato de **PDF** no mesmo diretório.

## Funcionalidades

:warning: Ainda em desenvolvimento

-   [ ] **Usuários:** endpoint principal de cadastro de usuários da aplicação, são divididos em:
    -   [ ] **Admin**: visão de administrador do condomínio;
    -   [x] **User**: visão de morador do condomínio;
-   [ ] **Correspondências**: os moradores poderão ver informações de suas correspondências e acompanhar por um dashboard na aplicação mobile
-   [ ] **Condomínios:** endpoint principal de cadastro de condomínios associados
    -   [ ] possui um dashboard mobile com dados resumidos das correspondências (totalizador)
    -   [ ] visualização de detalhes de blocos e apartamentos cadastrados;

## Como executar

Este projeto concentra todo o backend, podendo ser encontrado no seguinte diretório: `src/main/java`

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:

-   **Git:** para clonar o repositório em sua máquina;
-   **Java Development Kit (JDK):** para conseguir compilar e executar o projeto;
-   **PostgreSQL:** banco de dados relacional, responsável por armazenar todas as informações do sistema;
-   **Visual Studio Code:** Editor de código utilizada para desenvolver este projeto **ou qualquer outro de sua preferência**
-   **Insomnia:** Para realizar os testes da API enviando as requisições **ou qualquer outro utilitário de sua preferência**.

#### Preparando o ambiente

Após instalar e configurar as ferramentas de sua preferência, ao executar a aplicação pela primeira vez, será criada a estrutura de tabelas necessárias.

:warning: É necessário que a base de dados tenha sido criada previamente.

:warning: Para que a aplicação consiga acessar a base de dados, edite o arquivo `.env.template` ou exporte as variáveis de ambiente no seu sistema operacional.

#### Executando a aplicação

-   Clone este repositório
    ```bash
    # HTTPS
    git clone https://github.com/ozluuh/icorrespondencia-api.git
    # or with SSH
    git clone git@github.com:ozluuh/icorrespondencia-api.git
    ```
-   Abra no editor de código de sua escolha;
-   Abra o arquivo **ApiApplication.java** e execute pelo editor de código ou pelo **terminal** com:

    ```bash
    cd icorrespondencia-api

    mvn spring-boot:run
    ```

-   Abra o Insomnia e crie uma requisição do tipo `GET` apontando para o seguinte endereço `http://localhost:8080/api/test`;
-   Por fim, realize os testes &#x1F603;

## Como contribuir

1. Faça o **fork** do projeto;
2. Realize as alterações e envie um **pull request**.

## Autores

| Nome| RM | Função | Social |
| - | - | - | - |
| Daiane Estenio  | 84198 | Project manager     |
| Denis Mantovani | 86225 |                     | [![LinkedIn badge](https://img.shields.io/badge/-denismantovani-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://linkedin.com/in/denis-mantovani)](https://linkedin.com/in/denis-mantovani)                                                                                                                                                                                                    |
| Lucas Oliveira  | 85142 | Software architect  | [![Github badge](https://img.shields.io/badge/-Lukinhas08-black?style=flat-square&logo=Github&logoColor=white&link=https://github.com/Lukinhas08)](https://github.com/Lukinhas08) [![LinkedIn badge](https://img.shields.io/badge/-lucasoliveira-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://linkedin.com/in/lucas-oliveira-bba345198)](https://linkedin.com/in/lucas-oliveira-bba345198) |
| Luís Paulino    | 85398 | Fullstack Developer | [![Github badge](https://img.shields.io/badge/-ozluuh-black?style=flat-square&logo=Github&logoColor=white&link=https://github.com/ozluuh)](https://github.com/ozluuh) [![LinkedIn badge](https://img.shields.io/badge/-ozluuh-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://linkedin.com/in/ozluuh)](https://linkedin.com/in/ozluuh)                                                        |

## Licença

[MIT](./LICENSE) &copy; 21 Things
