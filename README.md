O sistema de cuidao para gatos é um aplicativo Android nativo desenvolvido em Kotlin e Jetpack Compose, focado no gerenciamento de cuidados para gatos. Ele permite que tutores organizem a vida de seus pets, registrando informações de saúde, agendamentos veterinários, vacinas e muito mais, tudo em um só lugar.
O projeto foi desenvolvido com uma estética fofa, utilizando uma fonte pixelada (Silkscreen) e uma paleta de cores personalizada (verde bebê e marrom) sobre um papel de parede temático.

obs: os commit do joão não estão aqui no github pois nós estavamos compoctando o código e mandado um para o outro para facilitar.

👥 Equipe e Divisão de Tarefas

Este projeto foi desenvolvido pela dupla: Dayane Dias e João Pedro Xavier Morgado

Para fins acadêmicos, a divisão de tarefas foi organizada da seguinte forma:

Dayane Dias: (

Responsável pela identidade visual e pela experiência do usuário além de fluxo de navegação, integração, repository, dependencias e API.

Design de UI/UX: Definição da estética "fofa", paleta de cores (verde bebê e marrom), e seleção do papel de parede.
Implementação da Fonte: Pesquisa, download e implementação da fonte customizada (Silkscreen) no projeto.
Desenvolvimento das Telas (Screens): Codificação de todas as 12 telas do aplicativo usando Jetpack Compose, incluindo HomeScreen, TutorListScreen, CatFormScreen, ScheduleListScreen, etc.
Fluxo de Navegação: Implementação do NavHost e das rotas de navegação (Screen.kt) para conectar todas as telas.
Integração com ViewModel: Conexão das telas com seus respectivos ViewModels para exibir dados (Listas) e salvar informações (Formulários).
Repository: Implementação do CuiGatoRepository para abstrair a origem dos dados (seja do banco ou da API).
Injeção de Dependência (Koin): Configuração do Koin para injetar o Repository, ViewModels e o Banco de Dados.
API (Retrofit): Configuração do Retrofit e do JsonPlaceholderService para a tela de demonstração de API.

João: (Arquitetura e Backend)

Responsável pela fundação, lógica de negócios e persistência de dados:

Arquitetura: Definição da arquitetura do projeto (MVVM - Model-View-ViewModel).
Banco de Dados (Room): Modelagem e implementação do banco de dados local.
Entidades e DAOs: Criação de todas as Entities (TutorEntity, GatoEntity, etc.) e seus DAOs (Data Access Objects) para operações de banco.


🚀 Instruções para Execução

Pré-requisitos:

Android Studio (versão Iguana ou mais recente)
Emulador Android (API 30+) ou dispositivo físico

Passos para Rodar:

Clone este repositório.
Abra o projeto no Android Studio e aguarde a sincronização do Gradle.
Adicionar Recursos Manuais: O projeto depende de dois recursos que não são versionados:
Fonte (Silkscreen):
Baixe a fonte silkscreen_regular.ttf.
No Android Studio, navegue até app/src/main/res/.
Crie um novo diretório chamado font (clique direito em res > New > Android Resource Directory > Resource type: font).
Copie o arquivo silkscreen_regular.ttf para dentro da pasta res/font.
Papel de Parede (Wallpaper):
Pegue a imagem de fundo do projeto (ex: wallpaper_background.jpg).
Copie a imagem para a pasta app/src/main/res/drawable/.
Execute o aplicativo (Run 'app').

📊 Diagrama da Estrutura do Banco de Dados

O banco de dados (Room) é estruturado com 4 entidades principais. Os relacionamentos são definidos por Chaves Estrangeiras (Foreign Keys).

[ TUTOR ] 1--* [ GATO ]
                  |
                  |
           +------+------+
           |             |
           * *
    [ TRATAMENTO ] [ REGISTRO_SAUDE ]


TutorEntity: Armazena os dados do responsável (ID, Nome, Email, etc.).
GatoEntity: Armazena os dados do gato (ID, Nome, Raça, etc.) e possui uma tutorId para o relacionamento.
TratamentoEntity: Armazena agendamentos (Consulta, Vacina, etc.) e possui uma gatoId.
RegistroSaudeEntity: Armazena o histórico (Peso, Alergia, etc.) e possui uma gatoId.

Relacionamentos:

1 Tutor pode ter N Gatos.

1 Gato pode ter N Tratamentos.

1 Gato pode ter N Registros de Saúde.

🗺️ Diagrama de Navegação

O aplicativo utiliza uma MainActivity única que hospeda um NavHost (Jetpack Compose Navigation).

[ MainActivity (com Wallpaper de Fundo) ]
    |
    |-- (Rota Inicial) --> [ HomeScreen ]
                                |
        +-----------------------+-----------------------+-----------------------+
        |                       |                       |                       |
        v                       v                       v                       v
[ TutorListScreen ]     [ CatListScreen ]     [ ScheduleListScreen ]  [ ApiDemoScreen ]
        |                       |                       (Abas: Agend./Prontuário)
        v                       v
[ TutorFormScreen ]     [ CatDetailScreen ]
(Novo/Editar)                   |
                        +-------+-------+
                        |               |
                        v               v
                [ TreatmentListScreen ] [ HealthHistoryScreen ]
                        |               |
                        v               v
                [ TreatmentFormScreen ] [ HealthRecordFormScreen ]
                (Novo/Editar)           (Novo/Editar)


📡 Endpoints da API (Demonstração)

A tela "Demonstração API" consome a API pública jsonplaceholder.typicode.com para testar operações de rede com Retrofit.

GET /users
Descrição: Busca uma lista de usuários de exemplo.
Usado em: Botão "GET Users".

POST /posts
Descrição: Cria um novo post falso no servidor.
Usado em: Botão "POST Post".

PUT /posts/1
Descrição: Atualiza o post com ID 1.
Usado em: Botão "PUT Post".

DELETE /posts/1
Descrição: Deleta o post com ID 1.
Usado em: Botão "DELETE Post".
