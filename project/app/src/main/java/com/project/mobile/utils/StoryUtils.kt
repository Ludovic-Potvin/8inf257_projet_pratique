package com.project.mobile.utils

class StoryUtils {
    private val storiesList: MutableList<StoryVM> = mutableListOf(
        StoryVM(id = 1,
            "Inscription",
            description = "En tant que utilisateur, \nje veux créer un compte.",
            done = false),
        StoryVM(id = 2,
            "Consulter le solde",
            description = "En tant que client, " +
                    "\nje veux voir mon solde.",
            done = true,
            priority = HighPriority
        ),
        StoryVM(id = 3,
            "Notifications",
            description = "En tant que abonné, \nje veux recevoir des notifications",
            done = true,
            priority = HighPriority
        ),
        StoryVM(id = 4,
            "Recherche d’articles",
            description = "En tant que utilisateur, \nje veux voir des articles",
            done = true,
        )
    )
}