# Book Inventory Manager - BIM

## Descrição do Projeto

O Book Inventory Manager (BIM) é uma aplicação desenvolvida em Java utilizando o framework Spring Boot. O objetivo do
projeto é gerenciar um inventário de livros, permitindo operações como adicionar, atualizar, buscar e deletar livros e
categorias.

## Tecnologias Utilizadas

* Java 21
* Maven 3.6.3
* Spring Boot
* Spring Data JPA
* Lombok
* H2 Database
* MySQL

## Requisitos

* Java 21
* Maven 3.6.3 ou superior
* Docker (opcional, para executar o banco de dados MySQL em contêiner)

## Configuração do Ambiente

### Clonando o Repositório

```bash
  git clone git@github.com:kenneth-de-oliveira/BIM-WS.git
```

### Instalando as Dependências

```bash
  mvn clean install
```

## Configuração do Banco de Dados

### Ambiente de Desenvolvimento

O projeto está configurado para usar o banco de dados H2 em memória no ambiente de desenvolvimento. As configurações
estão no arquivo src/main/resources/application-dev.yml.

### Ambiente de Produção

Para o ambiente de produção, o projeto está configurado para usar o banco de dados MySQL. As configurações estão no
arquivo src/main/resources/application-prod.yml. Você pode usar variáveis de ambiente para configurar o banco de dados:

* DB_HOST: Host do banco de dados (padrão: localhost)
* DB_PORT: Porta do banco de dados (padrão: 3306)
* DB_NAME: Nome do banco de dados (padrão: banco)
* DB_USERNAME: Nome de usuário do banco de dados (padrão: root)
* DB_PASSWORD: Senha do banco de dados (padrão: root)

## Executando a Aplicação

Para executar a aplicação, use o seguinte comando:

```bash
  mvn spring-boot:run
```

O WSDL da aplicação estará disponível em http://localhost:8080/BIM-WS/InventoryManagementWS.wsdl

## Configuração do SOAP UI

Para configurar o SOAP UI para testar os endpoints SOAP do Book Inventory Manager (BIM), siga os passos abaixo:

1. Baixe e instale o SOAP UI:
    * Acesse o site oficial do SOAP UI e baixe a versão mais recente.
    * Siga as instruções de instalação fornecidas no site.

2. Importe o projeto SOAP UI:
    * Abra o SOAP UI.
    * Clique em File > Import Project.
    * Selecione o arquivo InventoryManagementWS-soapui-project.xml localizado no diretório raiz do projeto.

## Utilização dos Controladores SOAP

### BookEndpoint

* deleteByIsbn: Deleta um livro pelo ISBN.

    * Request: DeleteBookRequest
    * Endpoint: /BIM-WS/InventoryManagementWS
    * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
    * Exemplo de requisição:
  ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
   <soapenv:Header/>
   <soapenv:Body>
      <bim:DeleteBookRequest>
         <bim:isbn>9780134685991</bim:isbn>
      </bim:DeleteBookRequest>
   </soapenv:Body>
   </soapenv:Envelope>
    ``` 

* findByIsbn: Busca um livro pelo ISBN.

    * Request: SearchBookRequest
    * Response: BookResponse
    * Endpoint: /BIM-WS/InventoryManagementWS
    * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
    * Exemplo de requisição:
    ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
   <soapenv:Header/>
   <soapenv:Body>
      <bim:SearchBookRequest>
         <bim:Book>
            <bim:isbn>9780316769488</bim:isbn>
         </bim:Book>
      </bim:SearchBookRequest>
   </soapenv:Body>
   </soapenv:Envelope>
    ```
    * Exemplo de resposta:
  ```xml
   <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:BookResponse xmlns:ns2="http://com.example/BIM-WS">
         <ns2:Book>
            <ns2:id>1</ns2:id>
            <ns2:title>The Rye in the Car</ns2:title>
            <ns2:authorName>J.D. Salinger</ns2:authorName>
            <ns2:text>A story about two rebellions.</ns2:text>
            <ns2:isbn>9780316769488</ns2:isbn>
            <ns2:category>
               <ns2:id>1</ns2:id>
               <ns2:name>Biography</ns2:name>
               <ns2:description>Biographical books</ns2:description>
            </ns2:category>
         </ns2:Book>
      </ns2:BookResponse>
   </SOAP-ENV:Body>
   </SOAP-ENV:Envelope>
    ```

* saveOrUpdate: Salva e atualiza um livro.

    * Request: BookRequest
    * Response: BookResponse
    * Endpoint: /BIM-WS/InventoryManagementWS
    * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
    * Exemplo de requisição:
  ```xml
  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
   <soapenv:Header/>
   <soapenv:Body>
      <bim:BookRequest>
         <bim:Book>
            <bim:title>The Rye in the Car</bim:title>
            <bim:authorName>J.D. Salinger</bim:authorName>
            <bim:text>A story about two rebellions.</bim:text>
            <bim:isbn>9780316769488</bim:isbn>
            <bim:categoryId>1</bim:categoryId>
         </bim:Book>
      </bim:BookRequest>
   </soapenv:Body>
  </soapenv:Envelope>
  ```

* retrieveAllBooks: Recupera todos os livros.

    * Response: BookResponse
    * Endpoint: /BIM-WS/InventoryManagementWS
    * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
    * Exemplo de requisição:
    ```xml
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
     <soapenv:Header/>
       <soapenv:Body>
        <bim:retrieveAllBooks/>
       </soapenv:Body>
     </soapenv:Envelope>
    ```
    * Exemplo de resposta:
  ```xml
   <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
     <ns2:Book>
            <ns2:id>1</ns2:id>
            <ns2:title>Clean Architecture</ns2:title>
            <ns2:authorName>Uncle Bob</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9783127323207</ns2:isbn>
            <ns2:category>
               <ns2:id>1</ns2:id>
               <ns2:name>Informatica</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>2</ns2:id>
            <ns2:title>Continuous Delivery</ns2:title>
            <ns2:authorName>Jez Humble</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780321601919</ns2:isbn>
            <ns2:category>
               <ns2:id>2</ns2:id>
               <ns2:name>DevOps</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>3</ns2:id>
            <ns2:title>Domain-Driven Design</ns2:title>
            <ns2:authorName>Eric Evans</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780321125217</ns2:isbn>
            <ns2:category>
               <ns2:id>3</ns2:id>
               <ns2:name>Design</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>4</ns2:id>
            <ns2:title>Patterns of Enterprise Application Architecture</ns2:title>
            <ns2:authorName>Martin Fowler</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780321127426</ns2:isbn>
            <ns2:category>
               <ns2:id>4</ns2:id>
               <ns2:name>Architecture</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>5</ns2:id>
            <ns2:title>Head First Design Patterns</ns2:title>
            <ns2:authorName>Eric Freeman</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780596007126</ns2:isbn>
            <ns2:category>
               <ns2:id>5</ns2:id>
               <ns2:name>Design Patterns</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>6</ns2:id>
            <ns2:title>Java Concurrency in Practice</ns2:title>
            <ns2:authorName>Brian Goetz</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780321349606</ns2:isbn>
            <ns2:category>
               <ns2:id>6</ns2:id>
               <ns2:name>Concurrency</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>7</ns2:id>
            <ns2:title>You Don't Know JS</ns2:title>
            <ns2:authorName>Kyle Simpson</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9781491904244</ns2:isbn>
            <ns2:category>
               <ns2:id>7</ns2:id>
               <ns2:name>JavaScript</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
         <ns2:Book>
            <ns2:id>8</ns2:id>
            <ns2:title>The Mythical Man-Month</ns2:title>
            <ns2:authorName>Frederick P. Brooks Jr.</ns2:authorName>
            <ns2:text>bla bla bla</ns2:text>
            <ns2:isbn>9780201835953</ns2:isbn>
            <ns2:category>
               <ns2:id>8</ns2:id>
               <ns2:name>Software Engineering</ns2:name>
               <ns2:description>bla, bla, bla</ns2:description>
            </ns2:category>
         </ns2:Book>
   </SOAP-ENV:Body>
   </SOAP-ENV:Envelope>
  ``` 

### CategoryEndpoint

* deleteById: Deleta uma categoria pelo ID.

    * Request: DeleteCategoryRequest
    * Endpoint: /BIM-WS/InventoryManagementWS
    * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
    * Exemplo de requisição:
  ```xml
  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
   <soapenv:Header/>
   <soapenv:Body>
      <bim:DeleteCategoryRequest>
         <bim:id>1</bim:id>
      </bim:DeleteCategoryRequest>
   </soapenv:Body>
  </soapenv:Envelope>
  ```

  * findById: Busca uma categoria pelo ID.

      * Request: SearchCategoryRequest
      * Response: CategoryResponse
      * Endpoint: /BIM-WS/InventoryManagementWS
      * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
      * Exemplo de requisição:
      ```xml
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
     <soapenv:Header/>
     <soapenv:Body>
        <bim:SearchCategoryRequest>
           <bim:category>
              <bim:id>1</bim:id>
           </bim:category>
        </bim:SearchCategoryRequest>
     </soapenv:Body>
    </soapenv:Envelope>
      ```
      * Exemplo de resposta:
      ```xml
     <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
     <SOAP-ENV:Header/>
     <SOAP-ENV:Body>
        <ns2:CategoryResponse xmlns:ns2="http://com.example/BIM-WS">
           <ns2:category>
              <ns2:id>1</ns2:id>
              <ns2:name>Biography</ns2:name>
              <ns2:description>Biographical books</ns2:description>
           </ns2:category>
        </ns2:CategoryResponse>
     </SOAP-ENV:Body>
    </SOAP-ENV:Envelope>
      ```

    * save: Salva uma categoria.

        * Request: CategoryRequest
        * Response: CategoryResponse
        * Endpoint: /BIM-WS/InventoryManagementWS
        * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
        * Exemplo de requisição:
        ```xml
       <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
       <soapenv:Header/>
       <soapenv:Body>
          <bim:CategoryRequest>
             <bim:category>
                <bim:name>Biography</bim:name>
                <bim:description>Biographical books</bim:description>
             </bim:category>
          </bim:CategoryRequest>
       </soapenv:Body>
      </soapenv:Envelope>
        ```
        * Exemplo de resposta:
        ```xml
       <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
       <SOAP-ENV:Header/>
       <SOAP-ENV:Body>
          <ns2:CategoryResponse xmlns:ns2="http://com.example/BIM-WS">
             <ns2:category>
                <ns2:id>1</ns2:id>
                <ns2:name>Biography</ns2:name>
                <ns2:description>Biographical books</ns2:description>
             </ns2:category>
          </ns2:CategoryResponse>
       </SOAP-ENV:Body>
      </SOAP-ENV:Envelope>
        ```  

        * retrieveAllCategories: Recupera todas as categorias.

            * Response: CategoryResponse
            * Endpoint: /BIM-WS/InventoryManagementWS
            * SOAP Action: http://localhost:8080/BIM-WS/InventoryManagementWS
            * Exemplo de requisição:
            ```xml
              <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:bim="http://com.example/BIM-WS">
              <soapenv:Header/>
               <soapenv:Body>
                    <bim:retrieveAllCategories/>
               </soapenv:Body>   
              </soapenv:Envelope>  
            ``` 
            * Exemplo de resposta:
            ```xml
           <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
           <SOAP-ENV:Header/>
           <SOAP-ENV:Body>
             <ns2:CategoryResponse xmlns:ns2="http://com.example/BIM-WS">
             <ns2:category>
                <ns2:id>1</ns2:id>
                <ns2:name>Informatica</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>2</ns2:id>
                <ns2:name>DevOps</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>3</ns2:id>
                <ns2:name>Design</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>4</ns2:id>
                <ns2:name>Architecture</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>5</ns2:id>
                <ns2:name>Design Patterns</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>6</ns2:id>
                <ns2:name>Concurrency</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>7</ns2:id>
                <ns2:name>JavaScript</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
             <ns2:category>
                <ns2:id>8</ns2:id>
                <ns2:name>Software Engineering</ns2:name>
                <ns2:description>bla, bla, bla</ns2:description>
             </ns2:category>
            </ns2:CategoryResponse>
           </SOAP-ENV:Body>
          </SOAP-ENV:Envelope>
            ```

## Logs Arquivados

O projeto está configurado para arquivar logs diariamente. Os logs arquivados são armazenados no diretório
`./logs/archived/` com o padrão de nome `spring-boot-logger-YYYY-MM-DD.log`.

### Configuração do Logback

A configuração do Logback para arquivamento de logs está definida no arquivo `src/main/resources/logback-spring.xml`.
Abaixo está um exemplo de configuração:

```xml

<configuration>
    <appender name="RollingFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>./logs/spring-boot-logger.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>./logs/archived/spring-boot-logger-%d{yyyy-MM-dd}.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="RollingFile"/>
        <appender-ref ref="Console"/>
    </root>
</configuration>
```