# places-manager

- Necessário ter instalado Java 7+, MongoDB, Maven para build e IDE;
- Projeto está com plugin do jetty, então ele pode ser executado com "mvn jetty:run"
- A conexão com o mongodb pode ser editada em application.properties. Os comandos que devem ser executados para criação são: <br/>

use places<br/>
db.createCollection("place")<br/>
db.place.createIndex( { "address.location" : "2dsphere" } )<br/>

# Serviços

## places POST

Descrição: Insere um local.

```javascript
{
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
201: CREATED<br/>
404: Address is Invalid. Coordinates not found.<br/>
400: Name and Address fields are required.<br/>
409: Duplicate place: Found same address with similar place name<br/>

## places/{id} PUT

Descrição: Edita um local.

```javascript
{
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

## places/{id} GET

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

## places GET

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

## places/{id} DELETE

Descrição: Desativa um local por id. Locais desativados não são retornados nos serviços search e list.

{id}: _id do place

Status response:<br/>
200: OK<br/>
404: Place not found<br/>

## places/{id} PATCH

Descrição: Ativa um local por id. Locais desativados não são retornados nos serviços search e list.

{id}: _id do place

Status response:<br/>
200: OK<br/>
404: Place not found<br/>

