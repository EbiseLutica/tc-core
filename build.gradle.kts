/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    kotlin("jvm") version "1.8.0"

    `maven-publish`

    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "2.0.4"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://m2.dv8tion.net/releases")
    }

    maven {
        url = uri("https://repo.opencollab.dev/maven-snapshots")
    }

    maven {
        url = uri("https://repo.opencollab.dev/maven-releases")
    }

    maven {
        url = uri("https://repo.phoenix616.dev/")
    }

    maven {
        url = uri("https://nexus.scarsz.me/content/groups/public/")
    }
    maven {
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
    maven {
        name = "citizens"
        url = uri("http://repo.citizensnpcs.co/")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    compileOnly("net.skinsrestorer:skinsrestorer:14.1.4-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.30")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("org.geysermc:connector:1.4.3-SNAPSHOT")
    compileOnly("org.geysermc.floodgate:api:2.0-SNAPSHOT")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.11.1")
    compileOnly("com.discordsrv:discordsrv:1.25.1")
    implementation("net.wesjd:anvilgui:1.6.3-SNAPSHOT")

    library("com.google.code.gson", "gson", "2.8.7")
    bukkitLibrary("com.google.code.gson", "gson", "2.8.7")
}

group = "work.xeltica.craft.core"
version = "3.2.0-tc"
description = "A Core System Plugin for TadoCraft."
java.sourceCompatibility = JavaVersion.VERSION_17

bukkit {
    name = "TC-Core"
    main = "work.xeltica.craft.core.TCCorePlugin"
    version = getVersion().toString()
    apiVersion = "1.19"
    depend = listOf("kotlin-stdlib", "Geyser-Spigot", "floodgate", "DiscordSRV")

    commands {
        register("omikuji") {
            description = "おみくじを引きます。マイクラ内で1日に1回まで引けて、100エビパワーを消費します。"
            usage = "/omikuji"
        }
        register("signedit") {
            description = "看板の指定行を編集します。"
            usage = "/signedit <行番号> <テキスト>"
            permission = "otanoshimi.command.signedit"
        }
        register("givemobball") {
            description = "モブボールを入手します。。"
            usage = "/givemobball <playerName> [amount=1] [type:normal|super|ultra]"
            permission = "otanoshimi.command.givemobball"
        }
        register("cat") {
            description = "CATモードの有効/無効を切り替えるか、現在のモードを取得します。"
            usage = "/cat [on/off]"
            permission = "otanoshimi.command.cat"
        }
        register("countdown") {
            description = "カウントダウンを表示します。"
            usage = "/countdown <秒数> [プレイヤー名...]"
            permission = "otanoshimi.command.countdown"
        }
        register("xreload") {
            description = "X-Coreの設定をリロードします。"
            usage = "/xreload"
            permission = "otanoshimi.command.xreload"
        }
        register("xdebug") {
            description = "X-Core Debug Command"
            usage = "/xdebug"
            permission = "otanoshimi.command.xdebug"
        }
        register("stamp") {
            description = "スタンプラリー用コマンド"
            usage = "/stamp listDonePlayers"
            permission = "otanoshimi.command.stamp"
        }
        register("firework") {
            description = "花火大会用コマンド"
            usage = "/firework <run|center> <scriptName>"
            permission = "otanoshimi.command.firework"
        }
        register("farmfest") {
            description = "秋農業祭り用コマンド"
            usage = "/farmfest <clearFarm|add|init|start|stop>"
            permission = "otanoshimi.command.farmfest"
        }
        register("candystore") {
            description = "アメストアを開きます。"
            usage = "/candystore"
            permission = "otanoshimi.command.candystore"
        }
        register("__core_gui_event__") {
            description = "?"
            usage = "?"
        }
    }

    permissions {
        register("otanoshimi.command.pvp") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.givecustomitem") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.givemobball") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.signedit") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.report") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.localtime") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.boat") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.cart") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.citizen") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.staff") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.cat") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("hub.teleport.sandbox") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("hub.teleport.art") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("hub.teleport.nightmare") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("hub.gatekeeper.citizen") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("hub.gatekeeper.staff") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.debug") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.xtp") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.xtp.other") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.promo") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.promo.other") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.epshop") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.epshop.add") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.epshop.delete") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.hint") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.xphone") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.live") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.counter") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.ranking") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.countdown") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.qchat") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.app.fireworks") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.FALSE
        }
        register("otanoshimi.command.epeffectshop") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.epeffectshop.add") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.epeffectshop.delete") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.xreload") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.xtpreset") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.xdebug") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.stamp") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.firework") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.farmfest") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.candystore") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.TRUE
        }
        register("otanoshimi.command.candystore.add") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.command.candystore.delete") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.stamp.create") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("otanoshimi.stamp.destroy") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("xcore.teleport.event") {
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        incremental = true
    }
}

tasks.jar {
    archiveFileName.set("${project.name}.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
