# Projeto ESII - 2a Atividade

Prototipo de aplicacao com persistencia em banco relacional, implementado com:

- C# (.NET 8)
- Entity Framework Core (ORM/MOR)
- MySQL
- API REST + interface web simples para testes

Este projeto atende ao diagrama da atividade, com heranca de produtos e relacionamento entre pedido, item e produto.

## 1) Modelo de dominio

Entidades:

- `Pedido`: `Id`, `Data`, `ValorTotal`
- `Item`: `CodigoItem`, `Quantidade`, `ValorItem`, `PedidoId`, `ProdutoId`
- `Produto`: `Id`, `Nome`, `Preco`, `Estoque`
- `ProdutoEletronico`: herda de `Produto`, adiciona `Voltagem`
- `ProdutoPerecivel`: herda de `Produto`, adiciona `DataValidade`

Relacionamentos:

- `Pedido (1) -> (N) Item`
- `Item (N) -> (1) Produto`
- Heranca de `Produto` com mapeamento TPH (tabela unica com discriminador)

## 2) Estrutura principal

- `Program.cs`: configuracao da API, Swagger, arquivos estaticos e DI
- `Data/AppDbContext.cs`: mapeamento EF Core
- `Controllers/`: endpoints REST
- `Migrations/`: migracao inicial
- `script.sql`: script SQL gerado pelo ORM
- `wwwroot/index.html`: interface de prototipo para avaliacao

## 3) Pre-requisitos

- .NET SDK 8 instalado
- MySQL rodando (local)
- Usuario com permissao no banco

### Exemplo de preparacao no WSL Ubuntu

```bash
sudo service mysql start
sudo mysql -e "CREATE DATABASE IF NOT EXISTS esii_pedidos;"
sudo mysql -e "CREATE USER IF NOT EXISTS 'esii'@'localhost' IDENTIFIED BY '123456';"
sudo mysql -e "GRANT ALL PRIVILEGES ON esii_pedidos.* TO 'esii'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"
```

## 4) Configuracao

Arquivo `appsettings.json`:

```json
{
  "ConnectionStrings": {
    "MySql": "server=localhost;port=3306;database=esii_pedidos;user=esii;password=123456"
  }
}
```

Se necessario, ajuste `user` e `password` para o seu ambiente.

## 5) Como compilar e executar

No diretorio do projeto:

```bash
dotnet restore
dotnet tool restore
dotnet tool run dotnet-ef database update
dotnet run
```

Ao iniciar, a aplicacao fica disponivel em:

- Interface web do prototipo: `http://localhost:5249/`
- Swagger/OpenAPI: `http://localhost:5249/swagger`

## 6) Como testar (passo a passo para avaliacao)

1. Abrir `http://localhost:5249/`.
2. Cadastrar um produto (`Comum`, `Eletronico` ou `Perecivel`).
3. Cadastrar um pedido.
4. Adicionar item ao pedido.
5. Verificar nas tabelas da pagina:
- Produtos cadastrados
- Pedidos cadastrados
- Itens cadastrados
- `ValorTotal` do pedido atualizado

Tambem e possivel testar endpoint por endpoint no Swagger.

## 7) Endpoints da API

Base URL local: `http://localhost:5249`

### 7.1 Produtos

`GET /api/produtos`

- Lista todos os produtos.

`GET /api/produtos/{id}`

- Busca produto por ID.

`POST /api/produtos`

- Cadastra produto comum.
- Body:

```json
{
  "nome": "Caneta",
  "preco": 2.5,
  "estoque": 100
}
```

`POST /api/produtos/eletronicos`

- Cadastra produto eletronico.
- Body:

```json
{
  "nome": "Notebook",
  "preco": 3500.0,
  "estoque": 15,
  "voltagem": 220
}
```

`POST /api/produtos/pereciveis`

- Cadastra produto perecivel.
- Body:

```json
{
  "nome": "Iogurte",
  "preco": 8.5,
  "estoque": 40,
  "dataValidade": "2026-06-30T00:00:00Z"
}
```

`PUT /api/produtos/{id}`

- Atualiza produto.
- Body (campos comuns obrigatorios, campos especificos opcionais):

```json
{
  "nome": "Notebook Gamer",
  "preco": 4200.0,
  "estoque": 10,
  "voltagem": 220,
  "dataValidade": null
}
```

`DELETE /api/produtos/{id}`

- Exclui produto (se nao estiver associado a item de pedido).

### 7.2 Pedidos

`GET /api/pedidos`

- Lista pedidos com itens.

`GET /api/pedidos/{id}`

- Busca pedido por ID, incluindo itens.

`POST /api/pedidos`

- Cadastra pedido.
- Body:

```json
{
  "data": "2026-03-31T10:30:00Z"
}
```

Observacao: `data` e opcional. Se nao enviar, o backend usa data/hora atual.

`PUT /api/pedidos/{id}`

- Atualiza data do pedido.
- Body:

```json
{
  "data": "2026-04-01T12:00:00Z"
}
```

`DELETE /api/pedidos/{id}`

- Exclui pedido.

### 7.3 Itens

`GET /api/itens`

- Lista itens.

`GET /api/itens/{codigoItem}`

- Busca item por codigo.

`POST /api/itens`

- Adiciona item ao pedido e recalcula `ValorTotal`.
- Tambem baixa estoque do produto.
- Body:

```json
{
  "pedidoId": 1,
  "produtoId": 1,
  "quantidade": 2
}
```

`PUT /api/itens/{codigoItem}`

- Atualiza quantidade de item, ajusta estoque e recalcula total.
- Body:

```json
{
  "quantidade": 3
}
```

`DELETE /api/itens/{codigoItem}`

- Remove item, devolve estoque e recalcula total do pedido.

## 8) Regras de negocio implementadas

- Nao permite criar item com quantidade <= 0
- Nao permite item com estoque insuficiente
- Nao permite excluir produto que ja esta em item de pedido
- Valor total do pedido e atualizado automaticamente ao inserir/editar/remover item

## 9) Script SQL e migracoes

Arquivos:

- `Migrations/20260331184805_InitialCreate.cs`
- `script.sql`

Comandos uteis:

```bash
dotnet tool run dotnet-ef migrations add NomeDaMigracao
dotnet tool run dotnet-ef database update
dotnet tool run dotnet-ef migrations script --output script.sql
```

## 10) Evidencias de funcionamento

Para validar persistencia no MySQL:

```bash
mysql -uesii -p123456 -e "USE esii_pedidos; SELECT * FROM Produtos; SELECT * FROM Pedidos; SELECT * FROM Itens;"
```

Se as tabelas tiverem registros apos os testes pela interface/Swagger, o prototipo esta funcionando corretamente.

## 11) Prints das telas:
<img width="1903" height="975" alt="image" src="https://github.com/user-attachments/assets/843492bc-4e84-40a4-982a-9bab43580227" />

<img width="1705" height="939" alt="image" src="https://github.com/user-attachments/assets/90ee4beb-dad0-4448-8526-366640b99c68" />

<img width="1592" height="873" alt="image" src="https://github.com/user-attachments/assets/044f4ec3-6a4c-4426-9013-cd7e3e1d0ad2" />

<img width="1517" height="861" alt="image" src="https://github.com/user-attachments/assets/aa89affa-4360-40d6-a82a-2d71c908c4cd" />

<img width="1705" height="893" alt="image" src="https://github.com/user-attachments/assets/333ff590-237b-454a-8af3-d3eb3f32c7dd" />

<img width="1650" height="731" alt="image" src="https://github.com/user-attachments/assets/4bbd3d1f-f10f-4da4-8078-89f5f86eb570" />



