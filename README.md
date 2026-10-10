# شاشة المسجد - APK

## Compiler l'APK avec GitHub (gratuit, sans installer Android Studio)
1. Créez un compte sur github.com, puis **New repository** (nom : mosque-screen, Public ou Private).
2. Décompressez ce zip sur votre ordinateur.
3. Sur la page du dépôt : **Add file > Upload files**, glissez TOUT le contenu du dossier
   (app, build.gradle, settings.gradle, gradle.properties, README.md) puis **Commit changes**.
4. Le dossier caché `.github` n'est souvent pas envoyé : créez-le à la main.
   **Add file > Create new file**, tapez comme nom `.github/workflows/build.yml`
   (les "/" créent les dossiers), collez le contenu de ce fichier du zip, puis **Commit**.
5. Onglet **Actions** : le job "Build APK" démarre seul (~3 min). Pastille verte = terminé.
   (Sinon : Actions > Build APK > Run workflow.)
6. Cliquez sur l'exécution terminée, en bas **Artifacts > mosque-screen-apk** : téléchargez le zip, l'APK est dedans.

Pour modifier la page plus tard : éditez `app/src/main/assets/index.html` sur GitHub, commit, et un nouvel APK est compilé.

## Installation sur le box TV
Copiez app-debug.apk sur une clé USB (ou "Send Files to TV"), installez-le (autoriser les sources inconnues).

## Démarrage automatique
- Ouvrez l'application une première fois (obligatoire, sinon Android bloque le démarrage auto).
- Android 10 et plus : au premier lancement un écran demande "Afficher par-dessus d'autres applis" : activez-le pour **شاشة المسجد**.
- Redémarrez le box pour tester : l'application doit s'ouvrir seule.
- Certains box ont aussi un réglage "Démarrage automatique / Autostart" dans leurs paramètres : activez-le pour l'application.

## Notes
- Orientation horizontale automatique, plein écran, écran toujours allumé.
- Touche Menu de la télécommande = réglages. Le bouton ⏻ ferme l'application.
