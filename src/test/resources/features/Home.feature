Feature: Validar flujo de compra de la página Makro PlazaVea

  Scenario: Usuario abre la página de Makro PlazaVea y verifica que cargó correctamente
    Given el usuario abre el navegador
    When el usuario navega a la página de Makro PlazaVea
    Then la página debe mostrar el logo de Makro

  Scenario: Usuario realiza una compra food en Makro PlazaVea
    Given el usuario abre el navegador
    When el usuario navega a la página de Makro PlazaVea
    And el usuario ingresa sus preferencias de entrega
    And el usuario busca un producto
    And agrega el producto al carrito


   Scenario: xd

     And el usuario procede al checkout
     And el usuario selecciona el método de pago con tarjeta de crédito
     And el usuario confirma la compra
     Then el sistema debe mostrar un mensaje de "Compra realizada con éxito"



    