# Documentação da API Pokémon Teams

Esta API permite aos usuários criar e gerenciar times de Pokémon. Utiliza a pokeapi.co para obter informações sobre os Pokémon.

## Rotas

### Listar todos os times registrados

```
GET /api/teams
```

**Descrição:** Retorna todos os times de Pokémon registrados no sistema.

**Resposta:**
- **200 OK:** Retorna uma lista de times de Pokémon.

### Buscar um time registrado por usuário

```
GET /api/teams/{user}
```

**Descrição:** Retorna o time de Pokémon registrado para o usuário especificado.

**Parâmetros de URL:**
- `{user}`: Nome de usuário do time a ser buscado.

**Resposta:**
- **200 OK:** Retorna o time de Pokémon registrado para o usuário especificado.
- **404 Not Found:** Se o usuário não tiver um time registrado.

### Criar um novo time

```
POST /api/teams
```

**Descrição:** Cria um novo time de Pokémon com base nos dados fornecidos.

**Corpo da requisição (JSON):**
```json
{
  "userName": "string",
  "pokemons": ["string"]
}
```

**Propriedades:**
- `"userName"`: Nome do usuário proprietário do time.
- `"pokemons"`: Lista de nomes de Pokémon para compor o time.

**Resposta:**
- **201 Created:** Retorna uma mensagem de confirmação e a ID única do time criado.
- **404 Not Found:** Se um ou mais Pokémon na lista não forem encontrados na pokeAPI.
- **500 Internal Server Error:** Se ocorrer um erro interno ao criar o time.

## Exemplo de Resposta

```json
{
  "owner": "Ash Ketchum",
  "pokemons": [
    {
      "id": 25,
      "name": "Pikachu",
      "weight": 60,
      "height": 4
    },
    {
      "id": 6,
      "name": "Charizard",
      "weight": 905,
      "height": 17
    }
  ]
}
```

Este é um exemplo de resposta ao buscar um time de Pokémon. Retorna o nome do proprietário do time e uma lista de Pokémon, cada um com seu ID na pokédex, peso e altura.

## Erros

A API pode retornar os seguintes erros:

- **400 Bad Request:** Se os parâmetros da requisição estiverem incorretos ou faltando.
- **404 Not Found:** Se o recurso solicitado não for encontrado.
- **500 Internal Server Error:** Se ocorrer um erro interno no servidor.

Esta é a documentação básica da API Pokémon Teams. Certifique-se de ler os detalhes dos endpoints para entender como usar a API corretamente.
