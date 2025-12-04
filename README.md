#  PHOENIXTECH

É um sistema que propõe um ecossistema colaborativo para cadastro e consultas de recursos educacionais críticos sobre **IA Responsável, CiberSegurança, Privacidade e Ética Digital**.  
Permite que usuários autenticados construam uma base de conhecimento confiável, centralizada em artigos e podcasts, para facilitar o aprendizado contínuo e a disseminação de boas práticas tecnológicas.

---

## 🎓 Contexto Acadêmico
Projeto desenvolvido para a disciplina **Programação de Soluções Computacionais** na **Universidade São Judas Tadeu**.

---

## 💻 Tecnologias Utilizadas
- **Java 17**
- **Java Swing** — Framework para interface gráfica desktop
- **MySQL 8.x** — Banco de dados relacional
- **JDBC (Java Database Connectivity)** — Driver para conexão com MySQL
- **mysql-connector-j-8.3.0.jar** — Driver MySQL específico
- **SQL** — Linguagem de consulta ao banco de dados
- **Git** — Controle de versão

---

## ⚙️ Instalação e Uso
### Passo 1: Clonar o repositório

```bash
git clone https://github.com/julianavsleal/PhoenixTech.git
cd PhoenixTech
```

### Passo 2: Configurar o banco de dados

1. Abra o **MySQL** (via linha de comando ou cliente MySQL):

```bash
mysql -u root -p
```

2. Cole o conteúdo do arquivo `.sql/phoenixtech_schema.sql`:

**Ou execute de uma vez pela linha de comando:**


```bash
mysql -u root -p < .sql/phoenixtech_schema.sql
```

### Passo 3: Compilar o projeto

No terminal, dentro da pasta do projeto:

```bash
javac -d target/classes -cp "lib/mysql-connector-j-8.3.0.jar" src/main/java/com/phoenixtech/**/*.java
```

### Passo 4: Executar a aplicação

```bash
java -cp "target/classes;lib/mysql-connector-j-8.3.0.jar" com.phoenixtech.Main
```

Ou em Linux/macOS (usar `:` em vez de `;`):
```bash
java -cp "target/classes:lib/mysql-connector-j-8.3.0.jar" com.phoenixtech.Main
```

---

## 🔧 Funcionalidades

-  **Sistema de login**
- **Cadastro de novos usuários**
-  **Autenticação de usuários**
-  **Criar, editar e deletar postagens (cada usuário gerencia suas próprias)**
-  **Visualização de postagens e usuários cadastrados**
-  **Ordenação alfabética por título das postagens**
-  **Dois perfis de usuários:**
  -  Administrador
  -  Usuário comum
-  **Administrador pode:**
  -  Inativar e excluir usuários
    

---

