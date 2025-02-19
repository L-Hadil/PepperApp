package com.example.my_application_for_pepper

import android.os.Bundle
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        // Lancer l’histoire interactive avec animations
        CoroutineScope(Dispatchers.IO).launch {
            interactiveStory(qiContext)
        }
    }

    override fun onRobotFocusLost() {
        // À implémenter si besoin
    }

    override fun onRobotFocusRefused(reason: String) {
        // Gestion en cas de refus du focus
    }

    /**
     * Fait parler Pepper, joue des animations aléatoires et pose des questions aux enfants.
     */
    private suspend fun interactiveStory(qiContext: QiContext) = withContext(Dispatchers.IO) {
        val storyLines = listOf(
            "Hello kids! Welcome to our amazing adventure! Are you ready?",
            "Once upon a time, in a magical forest, there was a brave little robot.",
            "This robot had one dream: to become a great explorer!",
            "So one day, he packed his backpack and started his journey.",
            "During his adventure, he found a big mysterious cave. Do you think he should go inside?",
            "Inside the cave, he discovered a treasure chest! What do you think was inside?",
            "Suddenly, a friendly dragon appeared and asked for help!",
            "The robot decided to help the dragon and they became best friends.",
            "And so, they continued their adventure together, discovering new lands and making more friends.",
            "The moral of the story is: Always be curious and never be afraid to explore new things!",
            "Thank you for listening to my story! Did you enjoy it?"
        )

        // Liste des animations disponibles (prend toutes les animations trouvées)
        val animationsPaths = listOf(
            "animations/Attract_L01.qianim",
            "animations/Attract_L02.qianim",
            "animations/Attract_R01.qianim",
            "animations/Attract_R02.qianim",
            "animations/Attract_R03.qianim",
            "animations/Attract_R04.qianim",
            "animations/Attract_R05.qianim",
            "animations/Attract_R06.qianim",
            "animations/Attract_R07.qianim",
            "animations/PlayWithHandLeft_01.qianim",
            "animations/PlayWithHandRight_01.qianim",
            "animations/CheckRight_01.qianim",
            "animations/CheckLeft_01.qianim",
            "animations/Make_Space_01.qianim",
            "animations/SadReaction_01.qianim",
            "animations/Show_Tablet_01.qianim"
        )

        for (text in storyLines) {
            try {
                // Sélection aléatoire d'une animation
                val randomAnimPath = animationsPaths[Random.nextInt(animationsPaths.size)]

                // Création du texte à dire
                val say: Say = SayBuilder.with(qiContext)
                    .withText(text)
                    .build()

                // Création de l’animation aléatoire
                val animation: Animation = AnimationBuilder.with(qiContext)
                    .withAssets(randomAnimPath)
                    .build()

                val animate: Animate = AnimateBuilder.with(qiContext)
                    .withAnimation(animation)
                    .build()

                // Exécution en parallèle
                val sayJob = async { say.run() }
                val animateJob = async { animate.run() }

                sayJob.await()
                animateJob.await()

                // Petite pause après chaque phrase pour plus d’interaction
                delay(2000)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
