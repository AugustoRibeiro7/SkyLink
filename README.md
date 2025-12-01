# Sistema Aeroporto SkyLink ✈

## Funcionalidades implementadas
- Arquitetura em camadas (DAO → Service → Controller → View)
- Tratamento de exceções
- Totalmente funcional via linha de comando

## Como executar o projeto

### 1. Banco de dados
Deve-se adicionar as credenciais de conexão com o banco no arquivo `src/database.properties`

### 2. Driver MySQL (OBRIGATÓRIO)

**Faça o seguinte:**

1. Acesse: https://dev.mysql.com/downloads/connector/j/
2. Escolha **Platform Independent** → baixe o arquivo **ZIP**
3. Extraia e pegue o arquivo `mysql-connector-j-8.x.x.jar` (ou `mysql-connector-j-9.x.x.jar`)
4. Crie uma pasta chamada **`lib`** na raiz do projeto (ao lado da pasta `src`)
5. Cole o .jar dentro da pasta `lib`

### 3. Adicionar o driver na IDE

**IntelliJ IDEA**  
File → Project Structure → Modules → Dependencies → “+” → JARs or Directories → selecione o .jar

### 4. Executar
- Execute a classe `Main.java`

Pronto! O sistema conecta no banco real e persiste tudo.

## Autores

| Nome               | GitHub                                                                                                                                     | LinkedIn                                                                                                                                                          |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Augusto Paiva      | [![GitHub](https://img.shields.io/badge/-GitHub-181717?logo=github&logoColor=white&style=flat)](https://github.com/AugustoRibeiro7)          | [![LinkedIn](https://img.shields.io/badge/-LinkedIn-0077B5?logo=linkedin&logoColor=white&style=flat)](https://www.linkedin.com/in/augusto-ribeiro7) |
| Ranieri Massini    | [![GitHub](https://img.shields.io/badge/-GitHub-181717?logo=github&logoColor=white&style=flat)](https://github.com/Ranieri-D10)            | [![LinkedIn](https://img.shields.io/badge/-LinkedIn-0077B5?logo=linkedin&logoColor=white&style=flat)](https://www.linkedin.com/in/rsm10) |