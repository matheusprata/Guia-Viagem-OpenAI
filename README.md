# API Guia de Viagens
Sistema para sugestões de lugares para viajar usando OpenAI

### ✔️ Técnicas e tecnologias utilizadas
- ``java 17``
- ``Spring Boot 3.2.5``
- ``Maven``
- ``OpenAI``
- ``Intellij IDEA``
- ``Git e GitHub``
- ``Postman - para teste da API local``
- ``Swagger - para teste da API local``

### Instalação e Configuração

1. Clone o repositório para sua máquina local:
   git clone https://github.com/matheusprata/Guia-Viagem-OpenAI.git

2. Importe o projeto em sua IDE.

3. Execute o projeto utilizando o Maven.

4. O sistema estará disponível no swagger em: `http://localhost:8080/swagger-ui/index.html`

### Funcionalidades

- POST /visited para informar locais onde o usuário já viajou.
- GET /visited/recommendations busca na OpenAI recomendações com base nos locais onde a pessoa já viajou.
- GET /travel busca lugares bons para viajar, o usuário pode especificar o país, estado e cidade se desejar.
- GET /stream/informations tem a intenção de te ajudar a decidir o porquê devo viajar para o local informado.
- GET /review um breve resumo sobre o local informado e os melhores pontos turísticos nesse local e região próxima.
