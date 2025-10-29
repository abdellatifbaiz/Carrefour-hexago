Feature: Génération des échéances d'une commande

  Scenario: Générer les échéances pour un paiement en 3 fois sans frais
    Given une commande avec un montant de 1900 et une option de paiement "THREE_TIMES_NO_FEES"
    When je génère les échéances de la commande
    Then la commande doit contenir 3 échéances
    And chaque échéance doit avoir le même montant
    And chaque échéance doit être espacée d'un mois

  Scenario: Générer les échéances pour un paiement par virement bancaire
    Given une commande avec un montant de 1500 et une option de paiement "BANK_TRANSFER"
    When je génère les échéances de la commande
    Then la commande doit contenir 1 échéances
    And le montant doit inclure les frais bancaires

  Scenario: Refuser une commande avec un montant trop bas
    Given une commande avec un montant de 50 et une option de paiement "BANK_TRANSFER"
    When je génère les échéances de la commande
    Then une erreur "OrderExceedAmount" doit être levée
