# 💸 Projeto PicPay Simplificado

Este projeto é uma aplicação Java que simula funcionalidades essenciais de um sistema de pagamentos estilo PicPay. Ele permite o cadastro de usuários (comuns e lojistas) e a realização de transações entre eles, respeitando regras de autorização e notificações externas. Ideal para estudos sobre arquitetura REST, integração com APIs externas e boas práticas de código.

## ⚙️ Funcionalidades

- Cadastro de usuários do tipo **Common** (usuário comum) e **Merchant** (lojista)
- Validação de CPF e e-mail durante o cadastro
- Realização de transações entre usuários
- Validação de saldo do remetente antes da transação
- Regras que impedem lojistas de realizarem transferências
- Autorização de transações via API externa
- Notificação ao destinatário após transações bem-sucedidas
- Tratamento de exceções com mensagens apropriadas

## 🚀 Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 
- Maven
- Docker (opcional)
- RestTemplate – Integração com serviços externos
- Lombok – Redução de boilerplate

## 📁 Estrutura do Projeto
```
picpay-simplificado/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/picpay_simplificado/
│ │ │ ├── controllers/
│ │ │ ├── domain/
│ │ │ ├── dto/
│ │ │ ├── repositories/
│ │ │ ├── services/
│ │ │ └── infra/
│ │ └── resources/
│ └── test/
├── .env
├── Dockerfile
├── pom.xml
└── README.md

```

## ⚙️ Configuração

O projeto utiliza variáveis de ambiente para conectar-se a serviços externos de **autorização** e **notificação de transações**.

#### Exemplo de `.env`

```env
AUTHORIZATION_API=https://util.devi.tools/api/v2/authorize
NOTIFICATION_API=https://util.devi.tools/api/v1/notify

```

#### No `application.properties`
```
external.api.authorization.url=${AUTHORIZATION_API}
external.api.notification.url=${NOTIFICATION_API}
```

## Executando o Projeto

### Usando Maven

```bash
mvn clean package
java -jar target/picpay-simplificado.jar
```

### Usando Docker

```bash
docker build -t picpay-simplificado:1.0 .
docker run --env-file .env picpay-simplificado
```

## 🧪 Exemplos de Endpoints
  ### Criar Usuário
  ```
    POST /users
  Content-Type: application/json
  
  {
    "firstName": "João ",
    "lastName":"da Silva",
    "email": "joao@email.com",
    "password": "123456",
    "document": "12345678901",
    "balance": 1000.00,
    "userType": "COMMON"
  }
```
  ### Realizar Transação

  ```
  POST /transactions
  Content-Type: application/json
  
  {
    "receiverId": 1,
    "senderId": 2,
    "value": 150.00
  }

  ```



## 📨Exemplo de Funcionamento
- Cadastro de um usuário Common e outro Merchant via endpoint /users.

- Realização de uma transação via /transactions informando ID do pagador, ID do recebedor e valor.

- O sistema verifica:

    - Se o usuário tem saldo suficiente.

    - Se o tipo do usuário permite enviar dinheiro.

    - Se a transação é autorizada via API externa.

    - Se tudo estiver ok, o valor é debitado do remetente e creditado no recebedor.

    -  Uma notificação é enviada ao recebedor.

## 📌 Observações

  - Usuários do tipo MERCHANT não podem realizar transferências.

  - Transações passam por uma validação em API externa e notificação após sucesso.

