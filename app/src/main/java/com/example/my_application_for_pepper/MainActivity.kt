/*package com.example.my_application_for_pepper

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
*/


//texte en fr
package com.example.my_application_for_pepper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.aldebaran.qi.sdk.`object`.locale.Locale
import com.aldebaran.qi.sdk.`object`.locale.Language
import com.aldebaran.qi.sdk.`object`.locale.Region

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        CoroutineScope(Dispatchers.IO).launch {
            interactiveStory(qiContext)
        }
    }

    override fun onRobotFocusLost() {}
    override fun onRobotFocusRefused(reason: String) {}

    private suspend fun interactiveStory(qiContext: QiContext) = withContext(Dispatchers.IO) {
        val storyLines = listOf(
            "Bonjour les enfants ! Bienvenue dans notre incroyable aventure ! Êtes-vous prêts ?",
            "Il était une fois, dans une forêt magique, un petit robot courageux.",
            "Ce robot avait un rêve : devenir un grand explorateur !",
            "Alors, un jour, il a préparé son sac à dos et a commencé son voyage.",
            "Pendant son aventure, il a trouvé une grande grotte mystérieuse. Pensez-vous qu'il devrait entrer ?",
            "À l'intérieur de la grotte, il a découvert un coffre au trésor ! Que pensez-vous qu'il y avait à l'intérieur ?",
            "Soudain, un dragon amical est apparu et a demandé de l'aide !",
            "Le robot a décidé d'aider le dragon et ils sont devenus les meilleurs amis du monde.",
            "Ainsi, ils ont continué leur aventure ensemble, découvrant de nouvelles terres et se faisant de nouveaux amis.",
            "La morale de cette histoire est : Soyez toujours curieux et n'ayez jamais peur d'explorer de nouvelles choses !",
            "Merci d'avoir écouté mon histoire ! L'avez-vous aimée ?"
        )

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
                val randomAnimPath = animationsPaths[Random.nextInt(animationsPaths.size)]

                val say: Say = SayBuilder.with(qiContext)
                    .withText(text)
                    .withLocale(Locale(Language.FRENCH, Region.FRANCE))
                    .build()

                val animation: Animation = AnimationBuilder.with(qiContext)
                    .withAssets(randomAnimPath)
                    .build()

                val animate: Animate = AnimateBuilder.with(qiContext)
                    .withAnimation(animation)
                    .build()

                val sayJob = async { say.run() }
                val animateJob = async { animate.run() }

                sayJob.await()
                animateJob.await()
                delay(2000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


//le capteur tete marhce

/*
package com.example.my_application_for_pepper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
// Imports pour la localisation QiSDK (attention aux backticks pour le package "object")
import com.aldebaran.qi.sdk.`object`.locale.Locale
import com.aldebaran.qi.sdk.`object`.locale.Language
import com.aldebaran.qi.sdk.`object`.locale.Region
// Imports pour le capteur tactile via l'API TouchSensor
import com.aldebaran.qi.sdk.`object`.touch.Touch
import com.aldebaran.qi.sdk.`object`.touch.TouchSensor
import com.aldebaran.qi.sdk.`object`.touch.TouchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.my_application_for_pepper.R

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks {

    private var qiContext: QiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vous pouvez utiliser votre layout habituel (ici activity_main)
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        this.qiContext = qiContext
        Log.d("MainActivity", "Focus acquis par le robot")
        testTouchSensor(qiContext)
    }

    override fun onRobotFocusLost() {
        qiContext = null
    }

    override fun onRobotFocusRefused(reason: String) {
        qiContext = null
    }

    /**
     * Récupère le TouchSensor "Head/Touch" et ajoute un listener.
     * Lorsque le capteur détecte un toucher, Pepper dit :
     * "Vous avez touché ma tête ! Merci beaucoup !"
     */
    private fun testTouchSensor(qiContext: QiContext) {
        try {
            // Récupérer l'instance Touch qui contient tous les capteurs tactiles
            val touch: Touch = qiContext.touch
            // Récupérer le capteur tactile de la tête
            val touchSensor: TouchSensor = touch.getSensor("Head/Touch")
            touchSensor.addOnStateChangedListener { touchState: TouchState ->
                if (touchState.touched) {
                    Log.d("MainActivity", "Head/Touch détecté à ${touchState.time}")
                    CoroutineScope(Dispatchers.IO).launch {
                        val say: Say = SayBuilder.with(qiContext)
                            .withText("Vous avez touché ma tête ! Merci beaucoup !")
                            .withLocale(Locale(Language.FRENCH, Region.FRANCE))
                            .build()
                        say.run()
                    }
                } else {
                    Log.d("MainActivity", "Capteur relâché à ${touchState.time}")
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Erreur lors de la récupération du capteur tactile : ${e.message}")
        }
    }
}

*/


/*

// capteur main fonction super


package com.example.my_application_for_pepper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
// Imports pour la localisation QiSDK (attention aux backticks pour le package "object")
import com.aldebaran.qi.sdk.`object`.locale.Locale
import com.aldebaran.qi.sdk.`object`.locale.Language
import com.aldebaran.qi.sdk.`object`.locale.Region
// Imports pour le service tactile
import com.aldebaran.qi.sdk.`object`.touch.Touch
import com.aldebaran.qi.sdk.`object`.touch.TouchSensor
import com.aldebaran.qi.sdk.`object`.touch.TouchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks {

    companion object {
        private const val TAG = "MainActivity"
    }

    // Stocker les capteurs tactiles que nous souhaitons écouter
    private val touchSensors = mutableListOf<TouchSensor>()
    private var qiContext: QiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Utilisez un layout minimal (par exemple activity_main.xml)
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        this.qiContext = qiContext
        Log.d(TAG, "Focus acquis par le robot")
        setupTouchSensors(qiContext)
    }

    override fun onRobotFocusLost() {
        // Retirer tous les listeners
        touchSensors.forEach { it.removeAllOnStateChangedListeners() }
        touchSensors.clear()
        qiContext = null
    }

    override fun onRobotFocusRefused(reason: String) {
        qiContext = null
    }

    /**
     * Configure les capteurs tactiles pour la tête, la main gauche et la main droite.
     * Lorsqu'un capteur est activé, Pepper répond de façon professionnelle.
     */
    private fun setupTouchSensors(qiContext: QiContext) {
        try {
            val touch: Touch = qiContext.touch
            // Liste des noms de capteurs à surveiller
            val sensorNames = listOf("Head/Touch", "LHand/Touch", "RHand/Touch")
            sensorNames.forEach { sensorName ->
                val sensor: TouchSensor = touch.getSensor(sensorName)
                // Stocker le capteur pour pouvoir retirer le listener plus tard
                touchSensors.add(sensor)
                sensor.addOnStateChangedListener { touchState: TouchState ->
                    val state = if (touchState.touched) "touched" else "released"
                    Log.i(TAG, "Sensor $sensorName $state at ${touchState.time}")
                    if (touchState.touched) {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val say: Say = SayBuilder.with(qiContext)
                                    .withText("Interaction détectée sur le capteur $sensorName. bravo.")
                                    .withLocale(Locale(Language.FRENCH, Region.FRANCE))
                                    .build()
                                say.run()
                            } catch (e: Exception) {
                                Log.e(TAG, "Erreur lors de la réponse vocale : ${e.message}")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erreur lors de la configuration des capteurs tactiles : ${e.message}")
        }
    }
}
*/