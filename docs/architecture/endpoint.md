# Tabela de Endpoints

| Verbo  | Caminho                    | Nome                       | Descrição                                      | Status             |
| ------ | -------------------------- | -------------------------- | ---------------------------------------------- | ------------------ |
| GET    | /users                     | Listagem dos usuário       | Listagem de todos os usuários cadastrados      | :white_check_mark: |
| POST   | /users                     | Cadastrar usuário          | Permite realizar o cadastro de um novo usuário | :white_check_mark: |
| PUT    | /users                     | Atualizar usuário          | Atualiza os dados do usuário                   | :white_check_mark: |
| GET    | /users/:id                 | Visualizar usuário         | Exibe todos os dados do usuário selecionado    | :white_check_mark: |
| DELETE | /users/:id                 | Excluir usuário            | Realiza a remoção do usuário                   | :white_check_mark: |
| POST   | /users/:id/deactivate      | Inativar usuário           | Desativa o usuário associado ao condomínio     | :construction:     |
| GET    | /users/:id/mailings        | Correspondências Recebidas | Lista todas as correspondências                | :construction:     |
| POST   | /users/:id/mailings        | Cadastrar correspondências | Cadastramento de correspondência recebida      | :construction:     |
| GET    | /users/:id/mailings/:id    | Dados da correspondência   | Detalhes da correspondência                    | :construction:     |
|        |                            |                            |                                                |                    |
| GET    | /townhouses                | Listagem de condomínios    | Listagem dos condomínios cadastrados           | :white_check_mark: |
| POST   | /townhouses                | Cadastrar condomínio       | Cadastra um novo condomínio                    | :white_check_mark: |
| PUT    | /townhouses                | Atualizar condomínio       | Atualiza os dados do condomínio                | :white_check_mark: |
| GET    | /townhouses/:id            | Visualizar condomínio      | Visualiza todos os dados do condomínio         | :white_check_mark: |
| DELETE | /townhouses/:id            | Excluir condomínio         | Realiza a remoção do condomínio                | :white_check_mark: |
| POST   | /townhouses/:id/deactivate | Inativar condomínio        | Desativa a atualização das informações         | :construction:     |