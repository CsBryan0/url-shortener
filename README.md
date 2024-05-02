# URL Shortner
Este é um serviço de encurtamento de URLs desenvolvido com Java e Spring, permitindo que os usuários transforme URLs longas em URLs mais curtas e fáceis de compartilhar!


## Funcionalidades
- Encurta URLs longas para URLs com no mínimo 5 e máximo 10 caracteres alfanuméricos
- Armazena as URLs encurtadas no banco de dados com um prazo de validade configurável.
- Redireciona as URLs curtas para seus links originais.

## Tecnologias Utilizadas

- Java 17;
- Maven;
- H2 Database
- Lombok
- Guava

## Endpoints

- POST/generate: Encurta a URL longa. Envie um payload JSON com a URL original e a data de expiração. 
Exemplo:
	```json 
	{ 
	"url": "[https://example.com](https://example.com/)", 
	"expirationDate": "2024-05-10T12:00:00" 
	} 
	```

- GET/{shorlink}: Ao receber a URL curta, basta coloca ao lado do http://localhost:8080 que será direcionado para a URL original.


## Como usar

1. Clone este repositório:
	``bash
	git clone https://github.com/seu-usuario/url-shortener.git
	``
	
2. Navegue até o diretório do projeto
	``bash
	cd url-shortener
	``
3. Execute o projeto com Maven
	``bash
	mvn spring-boot:run
	``

## Contribuindo
Contribuições são bem-vindas! Se você tem sugestões ou encontrou erros, por favor abra um issue ou envie um pull request para o repositório. 

Ao contribuir para este projeto, siga o estilo de código existente, confirme as convenções e envie suas alterações em um branch separado.
