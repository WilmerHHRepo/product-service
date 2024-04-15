# product-service
Modulo para la gestión de los productos

# Proyecto POSTMAN
BOOTCAMP51.postman_collection.json

# Crear La coleccion de parametros
Esta coleccion permite configurar los movimientos y los productos de los clientes


Sentencia:
db.createCollection("parameter")

Estructura:

{
	"codParameter" : "1001",
	"desParameter" : "Reglas de negocio",
	"listParameter" : {
		"listRuleMovement" : [
			{
				"indTypeClient" : "1",
				"indProduct" : "1",
				"Commission" : "0",
				"indRule" : "3",
				"desRule" : "Por Mes",
				"movement" : {
					"retreat" : "10",
					"deposit" : "10"
				}
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "2",
				"Commission" : "5.0",
				"indRule" : "3",
				"desRule" : "Por Mes",
				"movement" : {
					"retreat" : "*",
					"deposit" : "*"
				}
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "3",
				"Commission" : "0",
				"indRule" : "3",
				"desRule" : "Por Mes",
				"movement" : {
					"retreat" : "1",
					"deposit" : "1"
				}
			}
		],
		"listRuleSales" : [
			{
				"indTypeClient" : "1",
				"indProduct" : "1",
				"number" : "1"
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "2",
				"number" : "1"
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "3",
				"number" : "1"
			},
			{
				"indTypeClient" : "2",
				"indProduct" : "1",
				"number" : "0"
			},
			{
				"indTypeClient" : "2",
				"indProduct" : "3",
				"number" : "0"
			},
			{
				"indTypeClient" : "2",
				"indProduct" : "2",
				"number" : "*"
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "4",
				"number" : "1"
			},
			{
				"indTypeClient" : "2",
				"indProduct" : "5",
				"number" : "*"
			},
			{
				"indTypeClient" : "1",
				"indProduct" : "6",
				"number" : "1"
			},
			{
				"indTypeClient" : "2",
				"indProduct" : "6",
				"number" : "1"
			}
		]
	}
}
