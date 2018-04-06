# places-manager

Observações
- Quanto a verificação de duplicidade, na parte de endereço, a api se baseia no place_id retornado da api do Google.
Pois o google já considera as variações de nome para retornar um local.
- Logo, verifica-se se o local tem o mesmo endereço (place_id) de algum já cadastrado, e então, se tiver, compara-se os nomes. Se hambos nomes tiverem um índice de similaridade igual ou superior a 60%, considera-se que é um item duplicado e recusa o mesmo.
- A distância de busca está sendo considerada em km pois acredito que uma busca com raio menor que 1km engloba poucos locais. E de qualquer forma é possível também usando um valor decimal menor que 1.

- A api foi desenvolvida utilizando Maven para build das dependências, eclipse e tomcat.

- O BD está em mongo e a conexão com o mongodb pode ser editada em application.properties. Os comandos utilizados no mongodb foram: <br/>

use places<br/>
db.createCollection("place")<br/>
db.place.createIndex( { "address.location" : "2dsphere" } )<br/>

- O path places está sendo considerado com base em que será deployado no tomcat, de forma que o war está sendo gerado com o nome "places".

# Serviços

## places/save POST

Descrição: Insere ou edita um local.

```javascript
{
   "_id": "opcional - quando enviado irá editar o item com este id. Quando não enviado, irá salvar novo item"
   "name": "Nome do local - obrigatório",
   "address": {
    "street": "Rua do local - obrigatório",
    "streetNumber": "Número - obrigatório",
    "district": "Bairro - obrigatório",
    "city": "Cidade - obrigatório",
    "state": "Sigla Estado, ex SP - obrigatório",
    "country": "Sigla País, ex. BR - obrigatório",
    "zipcode": "CEP - obrigatório"
   }
}
```

Response Status:<br/>
200: OK<br/>
404: Address is Invalid. Coordinates not found.<br/>
400: Name and Address fields are required.<br/>
409: Duplicate place: Found same address with similar place name<br/>

## places/get/{id} GET

Descrição: Busca local por id.

{id}: _id do place<br/>

Ex. body Response:
```javascript
{
	_id: "58b1d547950d511c3cfc71e4",
	name: "Padaria Antonia",
	active: true,
	address: {
		street: "Rua Josefa Alves de Siqueira",
		streetNumber: "173",
		district: "Jd Anhanguera",
		city: "Praia Grande",
		state: "SP",
		country: "BR",
		zipcode: "11718000",
		placeId: "ChIJ31_UqQIfzpQRpaNgsNXDRng",
		location: {
			x: -46.48457336425781,
			y: -24.02081871032715,
			type: "Point",
			coordinates: [
				-46.48457336425781,
				-24.02081871032715
			]
		}
	}
}
```
Status response:<br/>
200: OK<br/>
404: Place not found<br/>

## places/list GET

Descrição: Lista locais paginadamente.

Parâmetros Query String:<br/>
page: número da página a ser retornada. Não obrigatório, default 1.<br/>
rows: números de linhas a serem exibidas por pagina. Não obrigatório, default 10.<br/>

Ex. Response body:<br/>
```javascript
{
	results: [
	{
		_id: "58b48602950d511364272097",
		name: "Kinkoe Japa Restaurante",
		active: true,
		address: {
		street: "Avenida Presidente Kennedy",
		streetNumber: "7854",
		district: "Ocian",
		city: "Praia Grande",
		state: "SP",
		country: "BR",
		zipcode: "11718000",
		placeId: "EkJBdi4gUHJlcy4gS2VubmVkeSwgNzg1NCAtIFZpbGEgQ2Fpw6dhcmEsIFByYWlhIEdyYW5kZSAtIFNQLCBCcmFzaWw",
		location: {
			x: -46.4838277,
			y: -24.0274839,
			type: "Point",
			coordinates: [
			-46.4838277,
			-24.0274839
			]
			}
		}
	 }
	],
	page: 1,
	pages: 1,
	found: 1
}
```
<br/>
Status Response:<br/>
200: OK<br/>

## places/search GET

Descrição: Lista locais paginadamente.<br/>
<br/>
### Parâmetros Query String:
page: número da página a ser retornada. Não obrigatório, default 1.<br/>
rows: números de linhas a serem exibidas por pagina. Não obrigatório, default 10.<br/>
q: Texto a ser buscado nos nomes de locais. Obrigatório<br/>
maxDistance: Valor em km, do raio de distância sob os quais serão buscados locais. Não obrigatório, default 2KM.<br/>
<br/>
Campos de endereço, todos opcionais. Com base no endereço formado por esses campos, e consecutivamente pelas coordenadas destes, será aplicado o maxDistance.<br/>
street<br/>
streetNumber<br/>
district	<br/>
city<br/>
state	<br/>
country	<br/>
zipcode<br/>

Ex.: request:<br/>
places/search?page=0&rows=10&q=m&maxDistance=3&city=Praia%20Grande&state=SP&district=Jardim%20Anhanguera<br/>


Ex. Response body:
```javascript
{
	results: [
	{
		distance: {
			value: 1.0812779009870068,
			metric: "KILOMETERS",
			unit: "km",
			normalizedValue: 0.00016952879829752903
		},
		place: {
			_id: "58b48602950d511364272097",
			name: "Kinkoe Japa Restaurante",
			active: true,
			address: {
				street: "Avenida Presidente Kennedy",
				streetNumber: "7854",
				district: "Ocian",
				city: "Praia Grande",
				state: "SP",
				country: "BR",
				zipcode: "",
				placeId: "EkJBdi4gUHJlcy4gS2VubmVkeSwgNzg1NCAtIFZpbGEgQ2Fpw6dhcmEsIFByYWlhIEdyYW5kZSAtIFNQLCBCcmFzaWw",
				location: {
					x: -46.4838277,
					y: -24.0274839,
					type: "Point",
					coordinates: [
						-46.4838277,
						-24.0274839
					]
				  }
				}
			}
		}
	],
	page: 1,
	pages: 1,
	found: 1
}
```
<br/>
Status Response:<br/>
200: OK<br/>

## places/disable/{id} GET

Descrição: Desativa um local por id. Locais desativados não são retornados nos serviços search e list.

{id}: _id do place

Status response:<br/>
200: OK<br/>
404: Place not found<br/>

## places/activate/{id} GET

Descrição: Ativa um local por id. Locais desativados não são retornados nos serviços search e list.

{id}: _id do place

Status response:<br/>
200: OK<br/>
404: Place not found<br/>

