# Teste Tegra - Matheus Lima


## Execução

Na linha de comando, execute:

```bash
gradle build
```

Após o processo terminar, acesse a pasta que foi gerada no endereço: /build/libs e execute o comando 

```bash
java -jar matheusairlines-0.0.1-SNAPSHOT.jar
```

O servidor será iniciado e a aplicação estará disponível na url: http://localhost:8080


Os endpoints criados estão sob os paths:

POST - api/flights - Receberá um critério e buscará os vôos de acordo;

GET - api/flights - Retornará a lista completa de vôos, de ambos os arquivos fornecidos

GET - api/airports - Retornará uma lista de aeroportos disponíveis.


## Author
Matheus Lima
