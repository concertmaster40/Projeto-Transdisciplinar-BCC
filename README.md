Viewed DBConnection.java:1-21
Viewed App.java:1-14
Listed directory banco_de_dados
Viewed catalogo_db.sql:1-59

Esta é a explicação completa da arquitetura do projeto **Catálogo de Mídias**, detalhando o padrão arquitetural, cada arquivo **`.java`**, **`.jsp`** e **`.css`**, e aprofundando nos conceitos de **POO**, **Servlets**, **JSP**, **JDBC** e **Prevenção de SQL Injection**.

---

## 🏛️ 1. Visão Geral da Arquitetura (MVC + DAO)

O projeto adota o padrão de arquitetura **MVC (Model-View-Controller)** combinado com o padrão de projeto **DAO (Data Access Object)**:

```
                  ┌──────────────────────────────────────────────────┐
                  │                 Navegador (Client)               │
                  └─────────┬──────────────────────────────▲─────────┘
                            │ Requisição HTTP              │ Resposta HTML
                            ▼                              │
                  ┌────────────────────────────────────────┴─────────┐
                  │          CONTROLLER (Java Servlets)              │
                  │  (Cadastrar, Listar, Alterar, Deletar Servlets)  │
                  └─────────┬──────────────────────────────▲─────────┘
                            │                              │
             Manipula Dados │               Passa Modelos  │ Renderiza
                            ▼               via Request    │
┌──────────────────────────────────────┐       ┌───────────┴──────────┐
│      MODEL & PERSISTENCE LAYER       │       │    VIEW (JSP + CSS)  │
│  - Modelo: ItemMidia (JavaBean)      │       │  - index.jsp         │
│  - DAO: ItemMidiaDAO (CRUD / JDBC)   │       │  - listarItens.jsp   │
│  - Conexão: DBConnection (Driver)    │       │  - editarItem.jsp    │
│  - Banco de Dados: MySQL             │       │  - estilo.css        │
└──────────────────────────────────────┘       └──────────────────────┘
```

---

## ☕ 2. Camada de Modelo e Persistência (`.java`)

### 📦 A. Modelo (POO e Encapsulamento)
#### 📄 [ItemMidia.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/modelo/ItemMidia.java)
- **Papel**: Representa o objeto de negócio do domínio (Entidade / JavaBean).
- **Conceitos de POO aplicados**:
  - **Encapsulamento**: Todos os atributos (`id`, `titulo`, `autorDiretor`, `anoLancamento`, `genero`, `sinopse`, `tipoMidia`) são declarados como `private`. O acesso e a modificação só ocorrem de forma controlada através de métodos públicos `getters` e `setters`.
  - **Construtores**:
    - Construtor padrão sem argumentos (`public ItemMidia()`), exigido pelas convenções de JavaBeans.
    - Construtor sobrecarregado parametrizado para instanciar mídias diretamente antes de persistir no banco.
  - **Sobrescrita de Métodos (`@Override`)**: Sobrescreve `toString()` da classe `Object` utilizando `StringBuilder` para representação textual e debugging eficiente sem alocação desnecessária de strings na memória Heap.

---

### 🗄️ B. Conexão e DAO (JDBC e Prevenção de SQL Injection)
#### 📄 [DBConnection.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/dao/DBConnection.java)
- **Papel**: Centralizar a criação e o fornecimento de conexões com o banco MySQL.
- **Conceitos de JDBC**:
  - `Class.forName("com.mysql.cj.jdbc.Driver")`: Carrega explicitamente a classe do driver JDBC do MySQL no ClassLoader.
  - `DriverManager.getConnection(URL, USUARIO, SENHA)`: Cria e retorna uma instância ativa da interface `java.sql.Connection`.
  - Tratamento centralizado de `SQLException` convertendo-a em `RuntimeException` para evitar tratamento redundante de checked exceptions nas camadas superiores.

#### 📄 [ItemMidiaDAO.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/dao/ItemMidiaDAO.java)
- **Papel**: Implementa o padrão **DAO (Data Access Object)**, isolando todas as instruções SQL e regras de persistência da camada de controle.
- **Operações CRUD implementadas**:
  1. `insert(ItemMidia item)` -> **Create** (`INSERT INTO item_midia ...`)
  2. `read(Integer id)` -> **Read Unitário** (`SELECT * FROM item_midia WHERE id = ?`)
  3. `readAll()` -> **Read Completo** (`SELECT * FROM item_midia`)
  4. `update(ItemMidia item)` -> **Update** (`UPDATE item_midia SET ... WHERE id = ?`)
  5. `delete(Integer id)` -> **Delete** (`DELETE FROM item_midia WHERE id = ?`)

#### 🛡️ Segurança: Como o DAO previne SQL Injection
> [!IMPORTANT]
> **Por que `PreparedStatement` é seguro contra SQL Injection?**
> Se o código concatenasse strings (ex: `"WHERE id = " + idString`), um invasor poderia enviar `' OR '1'='1` ou comandos destrutivos como `; DROP TABLE item_midia;`.
>
> Com `PreparedStatement`, a query SQL é enviada ao motor do banco com placeholders **`?`** (parâmetros posicionais). O banco de dados **pré-compila** o plano de execução SQL primeiro. Depois, métodos como `stmt.setString(1, ...)` e `stmt.setInt(7, ...)` tratam os dados estritamente como literais/valores, e nunca como código executável.

- **Gerenciamento de Recursos com `try-with-resources`**:
  - `Connection`, `PreparedStatement` e `ResultSet` implementam a interface `AutoCloseable`.
  - Ao declarar `try (Connection conn = ...; PreparedStatement stmt = ...)`, o Java garante que os sockets de conexão e cursores de memória do banco serão fechados automaticamente ao término do bloco, mesmo que ocorra uma exceção, evitando vazamento de conexões (*Connection Leaks*).
- **Mapeamento Objeto-Relacional Manual**: O método utilitário `mapearItemMidia(ResultSet rs)` extrai as colunas da tabela relacional e preenche a instância da classe [ItemMidia](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/modelo/ItemMidia.java).

---

## 🚦 3. Camada Controladora (`Servlets`)

Todas as Servlets herdam de `javax.servlet.http.HttpServlet` e utilizam anotações `@WebServlet` para roteamento de URLs sem necessidade de mapeamentos manuais extensos no `web.xml`.

```
Requisição HTTP (GET/POST)
       │
       ▼
HttpServlet (service()) ───► Direciona para doGet() ou doPost()
                                    │
                         ┌──────────┴──────────┐
                         ▼                     ▼
               Processa Parâmetros       Interage com DAO
                         │                     │
                         └──────────┬──────────┘
                                    ▼
                 RequestDispatcher.forward() OU response.sendRedirect()
```

### 📄 [CadastrarItemServlet.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/CadastrarItemServlet.java) (`/cadastrar`)
- **Método `doPost`**: 
  - Recebe os dados do formulário via `request.getParameter()`.
  - Faz a conversão de tipo (*parsing*) de `String` para `Integer` no campo de ano.
  - Instancia um novo `ItemMidia`, aciona `dao.insert(item)` e retorna confirmação com links de navegação.

### 📄 [ListarItensServlet.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/ListarItensServlet.java) (`/listarItens`)
- **Método `doGet` e `doPost`**:
  - Consulta o banco chamando `dao.readAll()`, obtendo um `List<ItemMidia>`.
  - Armazena a lista no escopo da requisição com `request.setAttribute("itens", listaItens)`.
  - Faz o **Forward** (`request.getRequestDispatcher("listarItens.jsp").forward(request, response)`), transferindo os dados para a página JSP no lado do servidor sem alterar a URL do cliente.

### 📄 [ListarItemServlet.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/ListarItemServlet.java) (`/listarItem`)
- **Método `doGet`**:
  - Obtém o parâmetro `id` da URL (ex: `listarItem?id=2`).
  - Executa `dao.read(id)`.
  - Se o item existir, despacha para [listarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/listarItem.jsp) com `request.setAttribute("item", item)`. Caso contrário, redireciona para `listarItens`.

### 📄 [AlterarItemServlet.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/AlterarItemServlet.java) (`/alterar`)
- **Método `doGet` (Preparação)**:
  - Recebe o `id` do item a ser editado.
  - Busca os dados atuais no banco com `dao.read(id)`.
  - Anexa o objeto `item` ao `request` e encaminha via `forward` para [editarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/editarItem.jsp) para preencher o formulário.
- **Método `doPost` (Execução)**:
  - Configura `request.setCharacterEncoding("UTF-8")` para suporte correto a acentuação.
  - Lê todos os campos atualizados enviados pelo formulário, incluindo o `id` oculto (*hidden*).
  - Executa `dao.update(item)` e renderiza a tela de sucesso com opções de retorno.

### 📄 [DeletarItemServlet.java](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/DeletarItemServlet.java) (`/excluir`)
- **Método `doGet`**:
  - Captura o `id` enviado por parâmetro.
  - Executa a remoção física com `dao.delete(id)`.
  - Apresenta feedback de sucesso com link de retorno à lista.

---

## 🖥️ 4. Camada de Apresentação (`.jsp`)

As páginas JSP atuam como a **View** no padrão MVC. Elas combinam HTML5 com Scriptlets Java (`<% ... %>`) e expressões de saída (`<%= ... %>`) para renderização dinâmica:

### 📄 [index.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/index.jsp)
- **Menu Central da Aplicação**:
  - **Opção 1**: Link direto para a servlet `listarItens`.
  - **Opção 2**: Link direto para a tela de cadastro [cadastrarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/cadastrarItem.jsp).
  - **Opção 3**: Formulário de consulta rápida por ID (submete `GET` para `listarItem`).
  - **Opção 4**: Seção de edição por ID (link para [editarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/editarItem.jsp) e formulário `GET` para a servlet `alterar`).
  - **Opção 5**: Formulário de exclusão rápida por ID (submete `GET` para `excluir` com confirmação via JavaScript `onsubmit`).

### 📄 [listarItens.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/listarItens.jsp)
- Recupera a lista com `(List<ItemMidia>) request.getAttribute("itens")`.
- Itera sobre a coleção através de um laço `for (ItemMidia item : itens)` gerando dinamicamente linhas `<tr>` na tabela HTML.
- Fornece botões de ação contextuais por linha para **Ver Detalhes** (`listarItem?id=...`), **Editar** (`alterar?id=...`) e **Excluir** (`excluir?id=...`).

### 📄 [editarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/editarItem.jsp)
- **Renderização Condicional**:
  - **Se `item != null`**: Exibe o formulário de edição com os dados atuais já carregados nos inputs (`value="<%= item.getTitulo() %>"`), pré-seleciona a opção correta no `<select>` de tipo de mídia e inclui o `<input type="hidden" name="id">`.
  - **Se `item == null`**: Exibe um formulário de busca para que o usuário informe o ID do item que deseja carregar.

### 📄 [listarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/listarItem.jsp)
- Exibe a ficha completa da mídia em formato de cartão (`.card`), incluindo detalhes como sinopse e autor.
- Disponibiliza atalhos para editar o item exibido ou retornar à listagem.

### 📄 [cadastrarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/cadastrarItem.jsp)
- Formulário HTML limpo que submete via `POST` para o endpoint `/cadastrar`.

### 📄 [deletarItem.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/deletarItem.jsp)
- Tela de suporte para confirmação de exclusão ou exclusão manual informando ID.

---

## 🎨 5. Estilização Centralizada (`.css`)

### 📄 [estilo.css](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/css/estilo.css)
- **Reset Global e Tipografia**: Define `box-sizing: border-box`, margens zeradas e a família tipográfica moderna `'Segoe UI', sans-serif`.
- **Layout Flexbox**: O `body` é configurado com `display: flex; flex-direction: column; align-items: center;` garantindo que todos os formulários, tabelas e cabeçalhos fiquem centralizados com largura máxima de `800px`.
- **Componentes Reutilizáveis**:
  - `.card`: Caixas brancas com bordas arredondadas e sombras suaves (`box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05)`).
  - Formulários (`form`, `input`, `select`, `textarea`): Campos em largura total com transição suave e foco estilizado (`:focus`).
  - Botões (`button`, `.btn`): Estilização em azul primário (`#2563eb`) com estados de `:hover`.
  - Tabelas (`table`, `th`, `td`): Cabeçalho escuro (`#1e293b`), linhas zebradas com `nth-child(even)` e efeito hover (`#f1f5f9`).

---

## 🔄 6. Resumo das Diferenças Críticas no Java Web

| Conceito | `RequestDispatcher.forward()` | `HttpServletResponse.sendRedirect()` |
| :--- | :--- | :--- |
| **Local de Execução** | No servidor (Server-side) | No cliente / navegador (Client-side) |
| **Requisição HTTP** | Mesma requisição mantida (`request.getAttribute` preservado) | Nova requisição HTTP (código 302, atributos do `request` são reiniciados) |
| **URL no Navegador** | Não muda | Muda para o novo endereço |
| **Uso no Projeto** | Enviar do Servlet para a JSP ([ListarItensServlet](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/java/com/projeto/controlador/ListarItensServlet.java) -> [listarItens.jsp](file:///c:/Users/nicolas.jackel/Desktop/Projeto-Transdisciplinar-BCC/catalogo/src/main/webapp/listarItens.jsp)) | Redirecionamento após erro ou ID inválido |