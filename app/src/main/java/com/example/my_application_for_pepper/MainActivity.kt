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
//this version work very good 
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
        // Lancer la narration avec plusieurs animations
        CoroutineScope(Dispatchers.IO).launch {
            tellStoryWithAnimations(qiContext)
        }
    }

    override fun onRobotFocusLost() {
        // À implémenter si besoin
    }

    override fun onRobotFocusRefused(reason: String) {
        // Gestion en cas de refus du focus
    }

    /**
     * Fait parler Pepper avec un long texte et plusieurs animations synchronisées.
     */
    private suspend fun tellStoryWithAnimations(qiContext: QiContext) = withContext(Dispatchers.IO) {
        // Texte long découpé en phrases
        val storyLines = listOf(
            "Hello everyone! My name is Pepper, and today I am going to tell you an amazing story!",
            "Once upon a time, in a faraway land, there was a small robot just like me.",
            "This robot had a big dream. It wanted to travel the world and meet many new friends.",
            "One day, it decided to take its first step outside and explore the unknown.",
            "Along the way, it discovered beautiful landscapes, met kind people, and learned new things every day.",
            "But, the journey was not always easy. There were challenges and obstacles to overcome.",
            "However, the little robot never gave up! It always found a way to move forward and achieve its dreams.",
            "And that, my friends, is the secret to success: never stop learning, never stop exploring, and always believe in yourself!",
            "Thank you for listening to my story! I hope you enjoyed it!"
        )

        // Liste des animations associées (doit correspondre au nombre de phrases)
        val animationsPaths = listOf(
            "animations/Attract_L01.qianim",  // Introduction
            "animations/Attract_L02.qianim",  // Début de l'histoire
            "animations/Attract_R01.qianim",  // Robot rêveur
            "animations/Attract_R02.qianim",  // Exploration
            "animations/Attract_R03.qianim",  // Découvertes
            "animations/Attract_R04.qianim",  // Défis
            "animations/Attract_R05.qianim",  // Persévérance
            "animations/Attract_R06.qianim",  // Conclusion inspirante
            "animations/Attract_R07.qianim"   // Remerciement
        )

        // Vérifier que les listes sont de la même taille
        if (storyLines.size != animationsPaths.size) {
            println("Erreur : Le nombre de phrases et d'animations ne correspond pas !")
            return@withContext
        }

        // Lancer chaque phrase avec son animation
        for (i in storyLines.indices) {
            val text = storyLines[i]
            val animPath = animationsPaths[i]

            try {
                // 1) Construire le texte à dire
                val say: Say = SayBuilder.with(qiContext)
                    .withText(text)
                    .build()

                // 2) Construire l'animation correspondante
                val animation: Animation = AnimationBuilder.with(qiContext)
                    .withAssets(animPath)
                    .build()

                val animate: Animate = AnimateBuilder.with(qiContext)
                    .withAnimation(animation)
                    .build()

                // 3) Exécuter les deux en parallèle
                val sayJob = async { say.run() }
                val animateJob = async { animate.run() }

                // Attendre la fin des deux tâches
                sayJob.await()
                animateJob.await()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
