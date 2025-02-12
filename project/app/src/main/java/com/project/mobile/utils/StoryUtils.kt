package com.project.mobile.utils

import com.project.mobile.presentation.StoryVM

private val stories: MutableList<StoryVM> = mutableListOf(
    StoryVM(
        id = 1,
        title = "Inscription",
        description = "En tant que utilisateur, \nje veux créer un compte."
    ),
    StoryVM(
        title = "Consulter le solde",
        description = "En tant que client, " +
                "\nje veux voir mon solde."
    ),
    StoryVM(
        title = "Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        title ="Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        title="Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        title="Notifications", description = "En tant que abonné, \nje veux recevoir des notifications"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    ),
    StoryVM(
        title="Recherche d’articles",
        description = "En tant que utilisateur, \nje veux voir des articles"
    )
)
fun getStories() : List<StoryVM> {
    return stories;
}

fun addOrUpdateStory(story: StoryVM) {
    val existingStory = stories.find { it.id == story.id }

    existingStory?.let {
        stories.remove(it)
    }

    stories.add(story)
}

fun deleteStoryFromList(story: StoryVM) {
    stories.remove(story)
}