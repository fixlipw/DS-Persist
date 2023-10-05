# DS-Persist-Trabalho_01

## Descrição do Trabalho

1. Defina uma entidade com pelo menos 5 atributos e relacionada ao domínio escolhido pela dupla para o trabalho prático da disciplina. Crie uma classe Java para representar a entidade escolhida.

---

2. Crie uma classe Principal com um Menu contendo as funcionalidades (Fx) definidas a seguir:

* F1. Inserir entidade no arquivo CSV - Cadastrar dados relacionados à entidade definida na questão 1. Receber dados via teclado e os adicionar através de append a um arquivo CSV.

* F2. Mostrar a quantidade de entidades existentes no arquivo CSV. Mesmo que você saia da aplicação e a reexecute futuramente, esta funcionalidade deve mostrar a real quantidade de entidades existentes no arquivo CSV, mesmo que dados sejam inseridos ou removidos no arquivo CSV através de um editor de texto externo à sua aplicação.

* F3. Converter os dados do arquivo CSV para gerar um arquivo JSON e um arquivo XML usando a biblioteca Jackson.

* F4. Compactar o arquivo CSV e gerar um novo arquivo de mesmo nome, mas com a extensão ZIP. Deve-se usar a API Java para realizar a compressão.

* F5. Mostrar na tela o hash SHA256 do arquivo CSV. Use a API Java para calcular o hash.

---

3. Crie um arquivo (divisao_tarefas.txt) detalhando a divisão de tarefas e mostrando o que cada membro da dupla efetivamente fez no trabalho. Divida as tarefas definidas entre os membros da dupla.

---

### Observações:

1. Cada funcionalidade acima deve estar definida em uma ou mais classe(s) específica(s). Ou seja: modularize seu código.

2. A classe Principal deve ser capaz de realizar todas as funcionalidades da aplicação, sem a necessidade de sair da aplicação para executar outra classe contendo o método main. Assim, sua aplicação só precisa ter um único método main, capaz de, a partir dele, executar todas as funcionalidades do trabalho.

2. Busque separar a interface com o usuário (UI - User Interface / Menu / Visualizações em Tela / Entrada via Teclado) das demais funcionalidades da aplicação.

3. Use um gerenciador de Build (Maven ou Gradle) para gerenciar as dependências do seu projeto.

4. Envie o código-fonte e o arquivo CSV com dados. Não envie arquivos de classes compiladas, nem os arquivos JAR das bibliotecas usadas.

5. Envie dados que sejam o mais próximo possível de dados reais. Evite de todo modo preencher um atributo com o valor "sadfadsfasdfasd", por exemplo.
