#!/bin/bash

# Vérifier si le dossier cmdline-tools existe déjà
if [ ! -d "cmdline-tools" ]; then
    echo "Décompression de commandlinetools-linux-10406996_latest.zip..."
    unzip -o commandlinetools-linux-10406996_latest.zip
    echo "Décompression terminée."
else
    echo "Les outils sont déjà extraits."
fi

