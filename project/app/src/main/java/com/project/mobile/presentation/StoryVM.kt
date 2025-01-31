package com.project.mobile.presentation

import java.time.LocalTime

data class StoryVM constructor(
    val title: String = "",
    val description: String = "",
    val hour: LocalTime = LocalTime.now()
    // liste de jours que l'on a créait nous meme ?
)

val stories = mutableListOf(
    StoryVM(
        title = "Inscription",
        description = "En tant que utilisateur, \nje veux créer un compte."
    ),
    StoryVM(
        "Consulter le solde",
        description = "En tant que client, " +
                "\nje veux voir mon solde."
    ),
    StoryVM(
        "Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        "Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        "Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        "Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        "Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    )
)